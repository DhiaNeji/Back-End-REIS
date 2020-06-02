package Reis.PFE.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;
import javax.websocket.MessageHandler.Whole;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.sun.mail.imap.OlderTerm;

import Reis.PFE.Entities.Cluster;
import Reis.PFE.Entities.ClusterToEdit;
import Reis.PFE.Entities.Customer;
import Reis.PFE.Entities.CustomerDuplicationDTO;
import Reis.PFE.Entities.CustomerSimpleCorrectionDTO;
import Reis.PFE.Entities.CustomerToEdit;
import Reis.PFE.Entities.CustomerWholeNameDuplDTO;
import Reis.PFE.Entities.Notification;
import Reis.PFE.Entities.Reporting;
import Reis.PFE.Entities.Step;
import Reis.PFE.Entities.StepDTO;
import Reis.PFE.Entities.Stepper;
import Reis.PFE.NameMatching.EditDistanceRecursive;
import Reis.PFE.NameMatching.NameMatching;
import Reis.PFE.config.Regex;
import Reis.PFE.dao.CustomerRepository;
import Reis.PFE.dao.NotificationRepository;
import de.uni_jena.cs.fusion.similarity.jarowinkler.JaroWinklerSimilarity;
import info.debatty.java.stringsimilarity.JaroWinkler;
import net.bytebuddy.description.type.TypeDefinition.Sort;
import java.util.function.Function; 

@Service
public class CustomerService {
@Autowired
private CustomerRepository customerRepository;
@Autowired
private NotificationService notificationService;
@Autowired
private NotificationRepository notificationRepository;
@Autowired
private ClusterService clusterService;
@Autowired
private ModelMapper modelMapper;
@Autowired
private Regex Regex;
@Autowired
private CleaningHistoryService cleaningHistoryService;
private SimpMessagingTemplate template;
private List<CustomerSimpleCorrectionDTO> cachedData=new ArrayList<CustomerSimpleCorrectionDTO>();
private Map<String,List<CustomerDuplicationDTO>> duplications;
private Map<String,List<CustomerWholeNameDuplDTO>> duplicationsBYWholeName;
private Map<String,List<Customer>> errNID=new HashMap<String,List<Customer>>();
private Map<String,List<Integer>> dataLength=new HashMap<String, List<Integer>>(){{
	put("1",new ArrayList<Integer>() {{add(0);add(0);}});
	put("2",new ArrayList<Integer>() {{add(0);add(0);}});
	put("3",new ArrayList<Integer>() {{add(0);add(0);}});
	put("4",new ArrayList<Integer>() {{add(0);add(0);}});
	put("5",new ArrayList<Integer>() {{add(0);add(0);}});
	put("6",new ArrayList<Integer>() {{add(0);add(0);}});
	put("7",new ArrayList<Integer>() {{add(0);add(0);}});
	put("8",new ArrayList<Integer>() {{add(0);add(0);}});
	put("9",new ArrayList<Integer>() {{add(0);add(0);}});
	put("10",new ArrayList<Integer>() {{add(0);add(0);}});
}};
//0 for datalength //1 for editedDataLength
private List<String> keys;
private int cachedDataLength;
private int editedDataLength;
@Autowired
private RestTemplate restTemplate;
private int lastEditedStep;
@Autowired
private StepService stepService;
@Autowired
private ReportingService reportingService;
private List<Stepper> list=new ArrayList<Stepper>() {{
add(new Stepper(false));
add(new Stepper(false));
add(new Stepper(false));
add(new Stepper(false));
add(new Stepper(false));
add(new Stepper(false));
add(new Stepper(false));
add(new Stepper(false));
add(new Stepper(false));
add(new Stepper(false));
add(new Stepper(false));
add(new Stepper(false));
add(new Stepper(false));
add(new Stepper(false));
add(new Stepper(false));
add(new Stepper(false));
add(new Stepper(false));
add(new Stepper(false));
add(new Stepper(false));
add(new Stepper(false));
add(new Stepper(false));
}}
;
private JavaMailSender javaMailSender;
@Autowired
public CustomerService(JavaMailSender javaMailSender)
{
	this.javaMailSender=javaMailSender;
}
public void sendEmail(String sendTo,String text) throws MailException
{
	SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
	simpleMailMessage.setTo(sendTo);
	simpleMailMessage.setFrom("dhia666@gmail.com");
	simpleMailMessage.setSubject("Nettoyage des données");
	simpleMailMessage.setText(text);
	this.javaMailSender.send(simpleMailMessage);
}
public List<String> getKeys(String step,String startRow,String endRow)
{
	List<String> keys=null;
	Set<String> l;
	switch (step) {
	case "14":
		
		l=this.list.get(14).getDuplicatedData().keySet();
		if(Integer.valueOf(endRow)>l.size())
			endRow=String.valueOf(l.size());
			keys=l.stream().collect(Collectors.toList()).subList(Integer.valueOf(startRow),Integer.valueOf(endRow));
		break;
	case "15":

		l=this.list.get(15).getDuplicatedData().keySet();
		if(Integer.valueOf(endRow)>l.size())
			endRow=String.valueOf(l.size());
			keys=l.stream().collect(Collectors.toList()).subList(Integer.valueOf(startRow),Integer.valueOf(endRow));
		break;
	case "16":
		l=this.list.get(16).getWholeNameDup().keySet();
		if(Integer.valueOf(endRow)>l.size())
			endRow=String.valueOf(l.size());
			keys=l.stream().collect(Collectors.toList()).subList(Integer.valueOf(startRow),Integer.valueOf(endRow));
	
		break;
	default:
		break;
	}

	return keys;
}
@Transactional
@Modifying
public void delete(int id)
{
	this.customerRepository.deleteById(id);
}
public List<Customer> findByNid(String nid)
{
	return this.customerRepository.findByNid(nid);
}
public ArrayList<Customer>findByNidAndBirth_date(String Birth_date,String Nid)
{
	return this.customerRepository.findByBirthdateAndNid(Nid, Birth_date);
}
public List<List<Customer>> getDuplicationsByNidAndBirth()
{
	List<Customer> liste=this.customerRepository.get_ducplicationByNidAndBirth();
	List<List<Customer>> resultat=new ArrayList<List<Customer>>();
	int i=0;
	int j=0;
	while(i<liste.size())
	{
		List<Customer> l=new ArrayList<Customer>();
		String birthdate=liste.get(i).getBirthdate();
		String Nid=liste.get(i).getNid();
		while(j<liste.size() &&liste.get(j).getBirthdate().equals(birthdate) && liste.get(j).getNid().equals(Nid))
		{
			l.add(liste.get(j));
			j++;
		}
		resultat.add(l);
		i=j;
	}
	return resultat;
}
public List<Customer>getDuplication(int nb)
{
	List<Customer> liste=new ArrayList<Customer>();
	List<List<Customer>> l=this.getDuplicationsByNidAndBirth();
	int k=(nb-1)*2;
	for(int i=k;i<=k+1;i++)
	{
		System.out.println(i);
		for(int j=0;j<l.get(i).size();j++)
		{
			liste.add(l.get(i).get(j));
		}
	}
	return liste;
}
public List<CustomerSimpleCorrectionDTO> getCachedData(String startRow,String endRow) {
return	this.cachedData.subList(Integer.valueOf(startRow),Integer.valueOf(endRow));
	
}
public int getDataLength()
{
return this.cachedData.size() ;
}
public boolean updateCachedData(Customer customer,int index)
{

	boolean status=false;
	try {
		this.customerRepository.save(customer);
		this.cachedData.get(index).setBirthdate(customer.getBirthdate());
//		this.cachedData.get(index).setEdited_in_corr_birth_dates(customer.getEdited_in_corr_birth_dates());
	} catch (Exception e) {
		// TODO: handle exception
	}
	return status;
}
public boolean clear() {
return true;}

public List<CustomerSimpleCorrectionDTO> getSimpleData(String step)
{
	System.out.println(this.list);
	return this.list.get(Integer.valueOf(step)).getData();
}
public Notification prepareData(String step)
{
	Notification notification=null;
	switch (step) {
	case "1":
		System.out.println("yeees");
		this.notificationService.createAndSendNotification("Préparation des cartes d'identité erronées en cours");
		if(this.list.get(1).isInitialized())
		{
			this.notificationService.createAndSendNotification("Préparation des cartes d'identité erronées terminée");
			//this.sendEmail("dhia.neji@vneuron.com","Préparation des dates de naissance erronées terminée avec succès !");
		}
		else
		{
			List<CustomerSimpleCorrectionDTO> l=this.customerRepository.getErrNID(this.Regex.getCinTNRegex()).stream().map(this::convertToCustomerSimpleCorrectionDTO).collect(Collectors.toList());;
			this.list.get(1).setData(l);
			Step step_=this.stepService.initializeStep("1",l.size());
			this.list.get(1).setStep(step_);
			//this.sendEmail("dhia.neji@vneuron.com","Préparation des dates de naissance erronées terminée avec succès !");
			this.notificationService.createAndSendNotification("Préparation des cartes d'identité terminée");
		}
		break;
	case "2":
		System.out.println("noooo");
		this.notificationService.createAndSendNotification("Préparation des nationalités erronées en cours");
		if(this.lastEditedStep==Integer.valueOf(step))
		{
			this.notificationService.createAndSendNotification("Préparation des nationalités erronées terminée");
		}
		else
		{
			try
			{
			this.cachedData=this.customerRepository.find_err_Nationalities().stream().map(this::convertToCustomerSimpleCorrectionDTO).collect(Collectors.toList());;
			this.notificationService.createAndSendNotification("Préparation des nationalités erronées terminée");
			this.lastEditedStep=2;
			this.initializeSimpleDataLength(step);
			this.stepService.initializeStep(step, this.cachedData.size());
			}
			catch(Exception ex)
			{
				this.notificationService.createAndSendNotification("Préparation des nationalités erronées echouée");
				
			}
		}
		break;
	case "3":
		this.notificationService.createAndSendNotification("Préparation des cartes d'identités tunisiennes erronées en cours");
		if(this.lastEditedStep==Integer.valueOf(step))
		{
			this.notificationService.createAndSendNotification("Préparation des cartes d'identités tunisiennes terminée");			
		}
		else
		{
			try {
				this.cachedData=this.customerRepository.getErrNID(this.Regex.getCinTNRegex()).stream().map(this::convertToCustomerSimpleCorrectionDTO).collect(Collectors.toList());;
				this.notificationService.createAndSendNotification("Préparation des cartes d'identités tunisiennes terminée");
				this.lastEditedStep=3;
				this.initializeSimpleDataLength(step);
				this.stepService.initializeStep(step, this.cachedData.size());
			}
			 catch (Exception e) {
				 this.notificationService.createAndSendNotification("Préparation des cartes d'identités tunisiennes echouée");
			 }
		}
		break;
	case "4":
		this.notificationService.createAndSendNotification("Préparation des passeports en cours");
		if(this.lastEditedStep==Integer.valueOf(step))
		{
			this.notificationService.createAndSendNotification("Préparation des passeports terminée");
		}
		else
		{
		try
		{
			this.cachedData=this.customerRepository.getERRPass(this.Regex.getPasseportRegex()).stream().map(this::convertToCustomerSimpleCorrectionDTO).collect(Collectors.toList());;
			this.notificationService.createAndSendNotification("Préparation des passeports erronés terminée");
			this.lastEditedStep=4;
			this.initializeSimpleDataLength(step);
			this.stepService.initializeStep(step, this.cachedData.size());
		}
		catch(Exception ex)
		{
			this.notificationService.createAndSendNotification("Préparation des passeports echouée");
		}}
		break;
	case "5" :
		this.notificationService.createAndSendNotification("Préparation des cartes des séjours en cours");
		if(this.lastEditedStep==Integer.valueOf(step))
		{
			notification=new Notification(null,"Préparation des cartes de séjours erronés terminée",LocalDateTime.now());
			this.notificationRepository.save(notification);
		}
		else
		{
		try
		{
			this.cachedData=this.customerRepository.getERRCSejour(this.Regex.getCarteSejourRegex()).stream().map(this::convertToCustomerSimpleCorrectionDTO).collect(Collectors.toList());;
			this.notificationService.createAndSendNotification("Préparation des cartes de séjours terminée");
			this.lastEditedStep=5;
			this.initializeSimpleDataLength(step);
			this.stepService.initializeStep(step, this.cachedData.size());
		}
		catch(Exception ex)
		{
			this.notificationService.createAndSendNotification("Préparation des cartes de séjours echouée");
		}}
		break;
	case "6":
		this.notificationService.createAndSendNotification("Préparation des matricules fiscaux en cours");
		if(this.lastEditedStep==Integer.valueOf(step))
		{
			this.notificationService.createAndSendNotification("Préparation des matricules fiscaux terminée");
		}
		else
		{
		try
		{
			this.cachedData=this.customerRepository.getERRMatFisc(this.Regex.getMatriculefiscaleRegex()).stream().map(this::convertToCustomerSimpleCorrectionDTO).collect(Collectors.toList());;
			this.notificationService.createAndSendNotification("Préparation des matricules fiscaux terminée");
			this.lastEditedStep=5;
			this.initializeSimpleDataLength(step);
			this.stepService.initializeStep(step, this.cachedData.size());
		}
		catch(Exception ex)
		{
			this.notificationService.createAndSendNotification("Préparation des matricules fiscaux echouée");
		}}
		break;
	case "7":
		this.notificationService.createAndSendNotification("Préparation des duplications des nid et dates de naissance en cours");
		if(this.lastEditedStep==Integer.valueOf(step))
		{
			this.notificationService.createAndSendNotification("Préparation des duplications des nid et dates de naissance terminée");
		}
		else
		{
			this.duplications=this.customerRepository.get_ducplicationByNidAndBirth().stream().map(c->this.convertToCustomerDuplicationDTO(c, step)).collect(Collectors.toList()).stream().collect(Collectors.groupingBy(C->C.getNid()));
			this.keys=this.duplications.keySet().stream().collect(Collectors.toList());
			this.dataLength.put(step,new ArrayList<Integer>());
			this.initializeDuplicatedDataLength(step);
			this.lastEditedStep=Integer.valueOf(step);
			//this.stepService.initilizeSimpleDuplicationStep(step,this.simpleDuplicationsLength(),this.keys.size());
			this.notificationService.createAndSendNotification("Préparation des duplications des nid et dates de naissance terminée");
		}
		break;
	case "8":
		this.notificationService.createAndSendNotification("Préparation des duplications des matricules fiscaux en cours");
		if(this.lastEditedStep==Integer.valueOf(step))
		{
			this.notificationService.createAndSendNotification("Préparation des duplications des matricules fiscaux terminée");
		}
		else
		{
			this.duplications=this.customerRepository.get_ducplicationByNidPM().stream().map(c->this.convertToCustomerDuplicationDTO(c, step)).collect(Collectors.toList()).stream().collect(Collectors.groupingBy(C->C.getNid()));
			System.out.println(this.customerRepository.get_ducplicationByNidPM().size());
			this.keys=this.duplications.keySet().stream().collect(Collectors.toList());
			this.initializeDuplicatedDataLength(step);
			this.lastEditedStep=Integer.valueOf(step);
			//this.stepService.initilizeSimpleDuplicationStep(step,this.simpleDuplicationsLength(),this.keys.size());
			this.notificationService.createAndSendNotification("Préparation des duplications des matricules fiscaux terminée");
		}
		break;
	case "9":
		this.notificationService.createAndSendNotification("Préparation des duplications des noms et des prenoms en cours");
		if(this.lastEditedStep==Integer.valueOf(step))
		{
			this.notificationService.createAndSendNotification("Préparation des duplications des noms et des prenoms terminée");
		}
		else
		{
		this.prepareDuplicationsFromApi();
		List<Cluster> list=this.clusterService.getClustersByIdCluster();
		this.duplicationsBYWholeName=this.clusterService.getClustersByIdCluster().stream().map(c->this.convertToCustomerWholeNameDuplDTO(c)).collect(Collectors.groupingBy(C->String.valueOf(C.getIdcluster())));
		this.keys=this.duplicationsBYWholeName.keySet().stream().collect(Collectors.toList());
		this.lastEditedStep=Integer.valueOf(step);
		this.stepService.initilizeSimpleDuplicationStep(step,this.wholeNameDuplicationLength(),this.keys.size());
		this.notificationService.createAndSendNotification("Préparation des duplications des noms et des prenoms terminée");
		}
	case "10":
		this.notificationService.createAndSendNotification("Préparation des registres de commerce en cours");
		if(this.lastEditedStep==Integer.valueOf(step))
		{
			this.notificationService.createAndSendNotification("Préparation des registres de commerce terminée");
		}
		else
		{
		try
		{
			this.cachedData=this.customerRepository.getERRRegComm(this.Regex.getRegistrecommercial()).stream().map(this::convertToCustomerSimpleCorrectionDTO).collect(Collectors.toList());;
			this.notificationService.createAndSendNotification("Préparation des registres de commerce terminée");
			this.lastEditedStep=10;
			this.initializeSimpleDataLength(step);
			this.stepService.initializeStep(step, this.cachedData.size());
		}
		catch(Exception ex)
		{
			this.notificationService.createAndSendNotification("Préparation des registres de commerce echouée");
		}}
		break;

	default:
		break;
	}
	return notification;
}
public int simpleDuplicationsLength(String step)
{

	int s=0;
	switch (step) {
	case "14":
		for(Map.Entry<String,List<CustomerDuplicationDTO>> entry:this.list.get(14).getDuplicatedData().entrySet())
		{
			s=s+entry.getValue().size();
		}	
		break;
	case "15":
		for(Map.Entry<String,List<CustomerDuplicationDTO>> entry:this.list.get(15).getDuplicatedData().entrySet())
		{
			s=s+entry.getValue().size();
		}
		break;

	default:
		break;
	}
	
	return s;
}
public int wholeNameDuplicationLength()
{
	int s=0;
	for(Map.Entry<String,List<CustomerWholeNameDuplDTO>> entry:this.list.get(16).getWholeNameDup().entrySet())
	{
		s=s+entry.getValue().size();
	}
	return s;
}
public void initializeSimpleDataLength(String step)
{
	this.dataLength.put(step,new ArrayList<Integer>());
	System.out.println("size :"+this.cachedData.size());
	this.dataLength.get(step).add(this.cachedData.size());
	this.dataLength.get(step).add(0);
}
public void initializeDuplicatedDataLength(String step)
{
	int s=0;
	for(Map.Entry<String,List<CustomerDuplicationDTO>> entry:this.duplications.entrySet())
	{
		s=s+entry.getValue().size();
	}
	this.dataLength.put(step,new ArrayList<Integer>());
	this.dataLength.get(step).add(s);
	this.dataLength.get(step).add(0);
	
}
public void initialiezDuplicationsByWholeNameDataLength(String step)
{
}
public List<CustomerSimpleCorrectionDTO> getData(String step,String startRow,String endRow)
{
	List<CustomerSimpleCorrectionDTO> result=new ArrayList<CustomerSimpleCorrectionDTO>();
	if(Integer.valueOf(endRow)>this.list.get(Integer.valueOf(step)).getData().size())
		endRow=String.valueOf(this.list.get(Integer.valueOf(step)).getData().size());
	switch (step) {
	case "1"://a changer
		result=this.list.get(1).getData().subList(Integer.valueOf(startRow),Integer.valueOf(endRow));
		break;
	case "2":
		result=this.list.get(2).getData().subList(Integer.valueOf(startRow),Integer.valueOf(endRow));
		break;
	case "3":
		result=this.list.get(3).getData().subList(Integer.valueOf(startRow),Integer.valueOf(endRow));
		break;
	case "4":
		result=this.list.get(4).getData().subList(Integer.valueOf(startRow),Integer.valueOf(endRow));
		break;
	case "5":
		result=this.list.get(5).getData().subList(Integer.valueOf(startRow),Integer.valueOf(endRow));
		break;
	case "6":
		result=this.list.get(6).getData().subList(Integer.valueOf(startRow),Integer.valueOf(endRow));
		break;
	case "7":
		result=this.list.get(7).getData().subList(Integer.valueOf(startRow),Integer.valueOf(endRow));
		break;
	case "8":
		result=this.list.get(8).getData().subList(Integer.valueOf(startRow),Integer.valueOf(endRow));
		break;
	case "9":
		result=this.list.get(9).getData().subList(Integer.valueOf(startRow),Integer.valueOf(endRow));
		break;
	case "10":
		result=this.list.get(10).getData().subList(Integer.valueOf(startRow),Integer.valueOf(endRow));
		break;
	case "11":
		result=this.list.get(11).getData().subList(Integer.valueOf(startRow),Integer.valueOf(endRow));
		break;
	case "12":
		result=this.list.get(12).getData().subList(Integer.valueOf(startRow),Integer.valueOf(endRow));
		break;
	case "13":
		result=this.list.get(13).getData().subList(Integer.valueOf(startRow),Integer.valueOf(endRow));
		break;
	default:
	}
	return result;
}
public int getDataLength(String step)
{
	return this.dataLength.get(step).get(0);
}
public ResponseEntity<Integer> update_data(String step,CustomerSimpleCorrectionDTO customerSimpleCorrectionDTO,int index) 
{
	this.list.get(Integer.valueOf(step)).getData().set(index, customerSimpleCorrectionDTO);
	//this.customerRepository.save(this.convertToEntityFromCustomerSimpleCorrectionDTO(customerSimpleCorrectionDTO));
	this.stepService.updateSimple(step, customerSimpleCorrectionDTO);
	return  ResponseEntity.ok().build();
}
public int get_edited_data_length(String step)
{
return this.dataLength.get(step).get(1);
}
public List<CustomerDuplicationDTO> getduplications(String step,String nid)
{
	return this.list.get(Integer.valueOf(step)).getDuplicatedData().get(nid);
}
public ResponseEntity<Integer> updateDuplications(String step,String oldNid,CustomerDuplicationDTO customerDuplicationDTO)
{
	List<CustomerDuplicationDTO> liste=null;
	switch (step) {
	case "14":
		liste=this.list.get(14).getDuplicatedData().get(oldNid);
		break;
	case "15":
		liste=this.list.get(15).getDuplicatedData().get(oldNid);
	default:
		break;
	}
	   int index=-1;
	   for(int i=0;i<liste.size();i++)
	   {
	     if(liste.get(i).getId()==customerDuplicationDTO.getId())
	     {
	       index=i;
	     }
	     }
	   	liste.set(index, customerDuplicationDTO);
	   	boolean stat=this.isDuplicationEditFinished(oldNid,step,"22");
	   	this.stepService.updateSimpleDuplicationStep(step, customerDuplicationDTO, stat);
		if(customerDuplicationDTO.isTo_delete())
		{	//this.customerRepository.deleteById(customerDuplicationDTO.getId());
		}else
		{
			Customer c=this.convertToEntityFromCustomerDuplicationDTO(customerDuplicationDTO);
			//this.customerRepository.save(c);
		}
	return ResponseEntity.ok(1);
}
public ResponseEntity<Integer> updateWholeNameDuplications(String idCluster,String oldNid,CustomerWholeNameDuplDTO customerWholeNameDuplDTO)
{
	List<CustomerWholeNameDuplDTO> liste=this.list.get(16).getWholeNameDup().get(idCluster);
	int index=-1;
	for(int i=0;i<liste.size();i++)
	{
		if(liste.get(i).getId()==customerWholeNameDuplDTO.getId())
			index=i;
	}
	liste.set(index,customerWholeNameDuplDTO);
	boolean stat=this.isDuplicationEditFinished(oldNid,"16",idCluster);
	this.stepService.updateWholeNameDuplicationStep(customerWholeNameDuplDTO, stat);
	if(customerWholeNameDuplDTO.isTo_delete())
	{
		//this.customerRepository.deleteById(customerWholeNameDuplDTO.getId());
	}
	else
	{
		//this.customerRepository.save(this.convertToEntityFromCustomerWholeDupDTO(customerWholeNameDuplDTO));
	}
	/*Customer customer=this.customerRepository.getCustomerById(clusterToEdit.getId());
	for(int i=0;i<liste.size();i++)
	{
		ClusterToEdit oldCustomerCluster=liste.get(i);
		if(oldCustomerCluster.getId()==clusterToEdit.getId())
		{
			liste.get(i).setBirth_date(clusterToEdit.getBirth_date());
			customer.setBirthdate(clusterToEdit.getBirth_date());
			liste.get(i).setNid(clusterToEdit.getNid());
			customer.setNid(clusterToEdit.getNid());
			liste.get(i).setStep_9(clusterToEdit.getStep_9());
			customer.setStep_6(clusterToEdit.getStep_9());
			liste.get(i).setTo_delete(clusterToEdit.isTo_delete());
			if(liste.get(i).getWhole_name().equals(clusterToEdit.getWhole_name())==false)
			{
				liste.get(i).setWhole_name(clusterToEdit.getWhole_name());
				String[] whole_name=clusterToEdit.getWhole_name().split(" ");
				customer.setFirst_name(whole_name[0]);
				customer.setLast_name(whole_name[1]);
			}
			if(clusterToEdit.isTo_delete())
			{
				//this.customerRepository.deleteById(clusterToEdit.getId());
			}
			else
			{	
				//this.customerRepository.save(customer);
			}
			}
	return true;
	}
	status=true;
	this.cleaningHistoryService.Increment("9");
	return status;*/
	return ResponseEntity.ok(1);
}

public Integer getEditedDataLength(String step) {
	return this.editedDataLength;
}
public boolean isDuplicationEditFinished(String nid,String step,String idCluster)
{
	int s=0;
	switch (step) {
	case "14":

		List<CustomerDuplicationDTO> liste=this.list.get(14).getDuplicatedData().get(nid);
		
		for(int i=0;i<liste.size();i++)
		{
			if(liste.get(i).isTo_delete()==false && liste.get(i).getStep_14()==null)
				s++;
			System.out.println("s="+s);
		}
		break;
	case "15":

		List<CustomerDuplicationDTO> liste2=this.list.get(15).getDuplicatedData().get(nid);
		for(int i=0;i<liste2.size();i++)
		{
			if(liste2.get(i).isTo_delete()==false && liste2.get(i).getStep_15()==null)
				s++;
			System.out.println("s="+s);
		}
		break;
	case "16":
		List<CustomerWholeNameDuplDTO> l=this.list.get(16).getWholeNameDup().get(idCluster);
		for (int i=0;i<l.size();i++)
		{
			if(l.get(i).isTo_delete()==false && l.get(i).getStep_16()==null)
				s++;
		}
	default:
		break;
	}
	
	return s==0;
}
public List<Notification> get_notifications() {
	Pageable pageable=PageRequest.of(0,5,org.springframework.data.domain.Sort.by("id").descending());
	return this.notificationRepository.findAll(pageable).getContent();
}
public List<List<Integer>> getStepsProgress()
{
	System.out.println(this.dataLength.values());
	List<List<Integer>> l=new ArrayList<List<Integer>>(this.dataLength.values());
	return l;
}

public void prepareDuplicationsFromApi()
{
	try {

		URL url = new URL("http://127.0.0.1:5000/duplication");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		if (conn.getResponseCode() != 200) {
		throw new RuntimeException("Failed : HTTP Error code : " + conn.getResponseCode());
		}
		conn.disconnect();
		} catch (Exception e) {
		System.out.println("Exception in NetClientGet:- " + e);
		}
}
public List<CustomerWholeNameDuplDTO> getDuplicationsByWholeName(String idCluster) 
{
return this.list.get(16).getWholeNameDup().get(idCluster);
}
public Customer testDto() {
	Customer c =this.customerRepository.getCustomerById(1);
	return c;
}
public CustomerDuplicationDTO convertToCustomerDuplicationDTO(Customer c,String step)
{
	CustomerDuplicationDTO customerDuplicationDTO= this.modelMapper.map(c,CustomerDuplicationDTO.class);
	customerDuplicationDTO.setCurrentStep(step);
	if(step.equals("14"))
		customerDuplicationDTO.setCurrentStep("14");
	else
		customerDuplicationDTO.setCurrentStep("15");
		return customerDuplicationDTO;
}
public CustomerSimpleCorrectionDTO convertToCustomerSimpleCorrectionDTO(Customer c)
{
return this.modelMapper.map(c, CustomerSimpleCorrectionDTO.class);
}
public CustomerWholeNameDuplDTO convertToCustomerWholeNameDuplDTO(Cluster c)
{
	CustomerWholeNameDuplDTO customerWholeNameDuplDTO=this.modelMapper.map(c, CustomerWholeNameDuplDTO.class);
	customerWholeNameDuplDTO.setCurrent_step("6");
	String[] whole_name=c.getWhole_name().split(" ");
	customerWholeNameDuplDTO.setFirst_name(whole_name[0]);
	customerWholeNameDuplDTO.setLast_name(whole_name[1]);
	return customerWholeNameDuplDTO;
}
public Customer convertToEntityFromCustomerSimpleCorrectionDTO(CustomerSimpleCorrectionDTO customerSimpleCorrectionDTO)
{
	Optional<Customer> customer=this.customerRepository.findById((int)customerSimpleCorrectionDTO.getId());
	Customer c=customer.get();
	c.setBirthdate(customerSimpleCorrectionDTO.getBirthdate());
	c.setNid(customerSimpleCorrectionDTO.getNid());
	c.setNationality(customerSimpleCorrectionDTO.getNationality());
	c.setStep_1(customerSimpleCorrectionDTO.getStep_1());
	c.setStep_2(customerSimpleCorrectionDTO.getStep_2());
	c.setStep_3(customerSimpleCorrectionDTO.getStep_3());
	return c;
}
public Customer convertToEntityFromCustomerDuplicationDTO(CustomerDuplicationDTO customerDuplicationDTO) 
{
	Optional<Customer> customer=this.customerRepository.findById((int)customerDuplicationDTO.getId());
	Customer c=customer.get();
	c.setNid(customerDuplicationDTO.getNid());
	c.setBirthdate(customerDuplicationDTO.getBirthdate());
	//c.setStep_14(customerDuplicationDTO.getStep_14());
	//c.setStep_15(customerDuplicationDTO.getStep_15());
	return c;
}
public Customer convertToEntityFromCustomerWholeDupDTO(CustomerWholeNameDuplDTO customerWholeNameDuplDTO)
{
	Optional<Customer> customer=this.customerRepository.findById((int)customerWholeNameDuplDTO.getId());
	Customer c=customer.get();
	c.setNid(customerWholeNameDuplDTO.getNid());
	c.setBirthdate(customerWholeNameDuplDTO.getNid());
	c.setFirst_name(customerWholeNameDuplDTO.getFirst_name());
	c.setLast_name(customerWholeNameDuplDTO.getLast_name());
	c.setStep_6(customerWholeNameDuplDTO.getStep_16());
	return c;
}
public Step getProgcess(String step) {
	return this.stepService.getStep(step);
}
public List<Object> getSumProgress()
{
	return this.stepService.getSum();
}
public List<Step> getSteps()
{
	return this.stepService.findAll();
}
public ResponseEntity<String> prepareErrIdentityCard()
{
	this.notificationService.createAndSendNotification("Préparation des cartes d'identité erronées en cours");
	if(this.list.get(1).isInitialized())
	{
		this.notificationService.createAndSendNotification("Préparation des cartes d'identité erronées terminée");
		//this.sendEmail("dhia.neji@vneuron.com","Préparation des dates de naissance erronées terminée avec succès !");
	}
	else
	{

		try
		{
			Thread.sleep(10000L);
		}catch(Exception ex)
		{
			
		}
		List<CustomerSimpleCorrectionDTO> l=this.customerRepository.getErrNID(this.Regex.getCinTNRegex()).stream().map(this::convertToCustomerSimpleCorrectionDTO).collect(Collectors.toList());;
		this.list.get(1).setData(l);
		Step step_=this.stepService.initializeStep("1",l.size());
		this.list.get(1).setStep(step_);
		this.list.get(1).setInitialized(true);
		//this.sendEmail("dhia.neji@vneuron.com","Préparation des dates de naissance erronées terminée avec succès !");
		this.notificationService.createAndSendNotification("Préparation des cartes d'identité terminée");
	}
	return new ResponseEntity<>("Préparation terminée",HttpStatus.OK);
}
public ResponseEntity<String> prepareErrPasseport()
{
	this.notificationService.createAndSendNotification("Préparation des passeports erronées en cours");
	if(this.list.get(2).isInitialized())
	{
		this.notificationService.createAndSendNotification("Préparation des passeports erronées terminée");
		//this.sendEmail("dhia.neji@vneuron.com","Préparation des dates de naissance erronées terminée avec succès !");
	}
	else
	{
		List<CustomerSimpleCorrectionDTO> l=this.cachedData=this.customerRepository.getERRPass(this.Regex.getPasseportRegex()).stream().map(this::convertToCustomerSimpleCorrectionDTO).collect(Collectors.toList());;
		this.list.get(2).setData(l);
		Step step_=this.stepService.initializeStep("2",l.size());
		this.list.get(2).setStep(step_);
		this.list.get(2).setInitialized(true);
		//this.sendEmail("dhia.neji@vneuron.com","Préparation des dates de naissance erronées terminée avec succès !");
		this.notificationService.createAndSendNotification("Préparation des passeports terminée");
	}
	return new ResponseEntity<>("Préparation terminée",HttpStatus.OK);
}
public ResponseEntity<?> prepareERRCarteSejour()
{
	this.notificationService.createAndSendNotification("Préparation des cartes de séjour erronées en cours");
	if(this.list.get(3).isInitialized())
	{
		this.notificationService.createAndSendNotification("Préparation des cartes de séjour erronées terminée");
		//this.sendEmail("dhia.neji@vneuron.com","Préparation des dates de naissance erronées terminée avec succès !");
	}
	else
	{
		List<CustomerSimpleCorrectionDTO> l=this.customerRepository.getERRCSejour(this.Regex.getCarteSejourRegex()).stream().map(this::convertToCustomerSimpleCorrectionDTO).collect(Collectors.toList());;
		this.list.get(3).setData(l);
		Step step_=this.stepService.initializeStep("3",l.size());
		this.list.get(3).setStep(step_);
		this.list.get(3).setInitialized(true);
		//this.sendEmail("dhia.neji@vneuron.com","Préparation des dates de naissance erronées terminée avec succès !");
		this.notificationService.createAndSendNotification("Préparation des cartes de séjour terminée");
	}
	return ResponseEntity.ok().build();
}
public ResponseEntity<?> prepareERRMatriculeFiscal()
{
	this.notificationService.createAndSendNotification("Préparation des cartes de séjour erronées en cours");
	if(this.list.get(4).isInitialized())
	{
		this.notificationService.createAndSendNotification("Préparation des cartes de séjour erronées terminée");
		//this.sendEmail("dhia.neji@vneuron.com","Préparation des dates de naissance erronées terminée avec succès !");
	}
	else
	{
		List<CustomerSimpleCorrectionDTO> l=this.customerRepository.getERRMatFisc(this.Regex.getMatriculefiscaleRegex()).stream().map(this::convertToCustomerSimpleCorrectionDTO).collect(Collectors.toList());;
		this.list.get(4).setData(l);
		Step step_=this.stepService.initializeStep("4",l.size());
		this.list.get(4).setStep(step_);
		this.list.get(4).setInitialized(true);
		//this.sendEmail("dhia.neji@vneuron.com","Préparation des dates de naissance erronées terminée avec succès !");
		this.notificationService.createAndSendNotification("Préparation des cartes de séjour terminée");
	}
	return ResponseEntity.ok().build();
}
public ResponseEntity<?> prepareERRRegistreDeCommerce()
{
	this.notificationService.createAndSendNotification("Préparation des registres de commerce erronées en cours");
	if(this.list.get(5).isInitialized())
	{
		this.notificationService.createAndSendNotification("Préparation des registres de commerce erronées terminée");
		//this.sendEmail("dhia.neji@vneuron.com","Préparation des dates de naissance erronées terminée avec succès !");
	}
	else
	{
		List<CustomerSimpleCorrectionDTO> l=this.customerRepository.getERRRegComm(this.Regex.getRegistrecommercial()).stream().map(this::convertToCustomerSimpleCorrectionDTO).collect(Collectors.toList());
		this.list.get(5).setData(l);
		Step step_=this.stepService.initializeStep("5",l.size());
		this.list.get(5).setStep(step_);
		this.list.get(5).setInitialized(true);
		//this.sendEmail("dhia.neji@vneuron.com","Préparation des dates de naissance erronées terminée avec succès !");
		this.notificationService.createAndSendNotification("Préparation des registres de commerce terminée");
	}
	return ResponseEntity.ok().build();
}
public ResponseEntity<?> prepareERRTin()
{
	this.notificationService.createAndSendNotification("Préparation des identifiants manquants en cours");
	if(this.list.get(6).isInitialized())
	{
		this.notificationService.createAndSendNotification("Préparation des identifiants manquants terminée");
		//this.sendEmail("dhia.neji@vneuron.com","Préparation des dates de naissance erronées terminée avec succès !");
	}
	else
	{
		List<CustomerSimpleCorrectionDTO> l=this.customerRepository.getERRTin().stream().map(this::convertToCustomerSimpleCorrectionDTO).collect(Collectors.toList());
		this.list.get(6).setData(l);
		Step step_=this.stepService.initializeStep("6",l.size());
		this.list.get(6).setStep(step_);
		this.list.get(6).setInitialized(true);
		//this.sendEmail("dhia.neji@vneuron.com","Préparation des dates de naissance erronées terminée avec succès !");
		this.notificationService.createAndSendNotification("Préparation des identifiants manquants terminée");
	}
	return ResponseEntity.ok().build();
}

public ResponseEntity<?> prepareERRDateDeNaissance()
{
	this.notificationService.createAndSendNotification("Préparation des dates de naissance erronées en cours");
	if(this.list.get(7).isInitialized())
	{
		this.notificationService.createAndSendNotification("Préparation des dates de naissance erronées terminée");
		//this.sendEmail("dhia.neji@vneuron.com","Préparation des dates de naissance erronées terminée avec succès !");
	}
	else
	{
		List<CustomerSimpleCorrectionDTO> l=this.customerRepository.findErrBD(this.Regex.getBirthDateRegex()).stream().map(this::convertToCustomerSimpleCorrectionDTO).collect(Collectors.toList());;
		this.list.get(7).setData(l);
		Step step_=this.stepService.initializeStep("7",l.size());
		this.list.get(7).setStep(step_);
		this.list.get(7).setInitialized(true);
		//this.sendEmail("dhia.neji@vneuron.com","Préparation des dates de naissance erronées terminée avec succès !");
		this.notificationService.createAndSendNotification("Préparation des dates de naissance terminée");
	}
	return ResponseEntity.ok().build();
}
public ResponseEntity<?> prepareERRNationalites()
{
	this.notificationService.createAndSendNotification("Préparation des nationalités erronées en cours");
	if(this.list.get(8).isInitialized())
	{
		this.notificationService.createAndSendNotification("Préparation des nationalités erronées terminée");
		//this.sendEmail("dhia.neji@vneuron.com","Préparation des dates de naissance erronées terminée avec succès !");
	}
	else
	{
		List<CustomerSimpleCorrectionDTO> l=this.customerRepository.find_err_Nationalities().stream().map(this::convertToCustomerSimpleCorrectionDTO).collect(Collectors.toList());;
		this.list.get(8).setData(l);
		Step step_=this.stepService.initializeStep("8",l.size());
		this.list.get(8).setStep(step_);
		this.list.get(8).setInitialized(true);
		//this.sendEmail("dhia.neji@vneuron.com","Préparation des dates de naissance erronées terminée avec succès !");
		this.notificationService.createAndSendNotification("Préparation des nationalités terminée");
	}
	return ResponseEntity.ok().build();
}
public ResponseEntity<?> prepareDuplicationNidDateNaissance()
{

	this.notificationService.createAndSendNotification("Préparation des duplication par Nid et date de naissance en cours");
	if(this.list.get(14).isInitialized())
	{
		this.notificationService.createAndSendNotification("Préparation des duplication par Nid et date de naissance terminée");
		//this.sendEmail("dhia.neji@vneuron.com","Préparation des dates de naissance erronées terminée avec succès !");
	}
	else
	{
		Map<String,List<CustomerDuplicationDTO>> m=this.customerRepository.get_ducplicationByNidAndBirth().stream().map(c->this.convertToCustomerDuplicationDTO(c,"14")).collect(Collectors.toList()).stream().collect(Collectors.groupingBy(C->C.getNid()));
		this.list.get(14).setDuplicatedData(m);
		Step step=this.stepService.initilizeSimpleDuplicationStep("14",this.simpleDuplicationsLength("14"),m.keySet().size());
		this.list.get(14).setStep(step);
		this.list.get(14).setInitialized(true);
		//this.sendEmail("dhia.neji@vneuron.com","Préparation des dates de naissance erronées terminée avec succès !");
		this.notificationService.createAndSendNotification("Préparation des duplication par Nid et date de naissance terminée");
	}
	return ResponseEntity.ok().build();	
}
public ResponseEntity<?> prepareDuplicationNidDPP()
{

	this.notificationService.createAndSendNotification("Préparation des duplication par Nid pour les personnes morales en cours");
	if(this.list.get(15).isInitialized())
	{
		this.notificationService.createAndSendNotification("Préparation des duplication par Nid pour les personnes morales terminée");
		//this.sendEmail("dhia.neji@vneuron.com","Préparation des dates de naissance erronées terminée avec succès !");
	}
	else
	{
		Map<String,List<CustomerDuplicationDTO>> m=this.customerRepository.get_ducplicationByNidPM().stream().map(c->this.convertToCustomerDuplicationDTO(c,"14")).collect(Collectors.toList()).stream().collect(Collectors.groupingBy(C->C.getNid()));
		this.list.get(15).setDuplicatedData(m);
		Step step=this.stepService.initilizeSimpleDuplicationStep("15",this.simpleDuplicationsLength("15"),m.keySet().size());
		this.list.get(15).setStep(step);
		this.list.get(15).setInitialized(true);
		//this.sendEmail("dhia.neji@vneuron.com","Préparation des dates de naissance erronées terminée avec succès !");
		this.notificationService.createAndSendNotification("Préparation des duplication par Nid pour les personnes morales terminée");
	}
	return ResponseEntity.ok().build();	
}

public ResponseEntity<?> prepareDuplicationWholeName()
{

	this.notificationService.createAndSendNotification("Préparation des duplications par nom et prenom en cours");
	if(this.list.get(16).isInitialized())
	{
		this.notificationService.createAndSendNotification("Préparation des duplications par nom et prenom terminée");
		//this.sendEmail("dhia.neji@vneuron.com","Préparation des dates de naissance erronées terminée avec succès !");
	}
	else
	{

		this.prepareDuplicationsFromApi();
		List<Cluster> list=this.clusterService.getClustersByIdCluster();
		Map<String,List<CustomerWholeNameDuplDTO>> m=this.clusterService.getClustersByIdCluster().stream().map(c->this.convertToCustomerWholeNameDuplDTO(c)).collect(Collectors.groupingBy(C->String.valueOf(C.getIdcluster())));
		this.list.get(16).setWholeNameDup(m);
		Step step=this.stepService.initilizeSimpleDuplicationStep("16",this.wholeNameDuplicationLength(),m.keySet().size());
		this.list.get(16).setStep(step);
		this.list.get(16).setInitialized(true);
		//this.sendEmail("dhia.neji@vneuron.com","Préparation des dates de naissance erronées terminée avec succès !");
		this.notificationService.createAndSendNotification("Préparation des duplication par Nid pour les personnes morales terminée");
	}
	return ResponseEntity.ok().build();	
}
public ResponseEntity<?> preparerErrEmail()
{

	this.notificationService.createAndSendNotification("Préparation des emails erronés en cours");
	if(this.list.get(9).isInitialized())
	{
		this.notificationService.createAndSendNotification("Préparation des emails erronés terminée");
		//this.sendEmail("dhia.neji@vneuron.com","Préparation des dates de naissance erronées terminée avec succès !");
	}
	else
	{
		List<CustomerSimpleCorrectionDTO> l=this.customerRepository.getERREmail(this.Regex.getEmailRegex()).stream().map(this::convertToCustomerSimpleCorrectionDTO).collect(Collectors.toList());;
		this.list.get(9).setData(l);
		Step step_=this.stepService.initializeStep("9",l.size());
		this.list.get(9).setStep(step_);
		this.list.get(9).setInitialized(true);
		//this.sendEmail("dhia.neji@vneuron.com","Préparation des dates de naissance erronées terminée avec succès !");
		this.notificationService.createAndSendNotification("Préparation des emails erronés terminée");
	}
	return ResponseEntity.ok().build();	
}
public List<Stepper> getStepper() {
return this.list;
}
public ResponseEntity<?> prepapreErrNoms()
{
	this.notificationService.createAndSendNotification("Préparation des noms erronés en cours");
	if(this.list.get(10).isInitialized())
	{
		this.notificationService.createAndSendNotification("Préparation des noms erronés terminée");
		//this.sendEmail("dhia.neji@vneuron.com","Préparation des dates de naissance erronées terminée avec succès !");
	}
	else
	{
		ResponseEntity<List<Customer>> re=restTemplate.exchange("http://127.0.0.1:5000/wrongfirstname/",HttpMethod.GET,null,new ParameterizedTypeReference<List<Customer>>() {
		});
		List<Customer> l=re.getBody();
		List<CustomerSimpleCorrectionDTO> ll=l.stream().map(this::convertToCustomerSimpleCorrectionDTO).collect(Collectors.toList());
		this.list.get(10).setData(ll);
		this.list.get(10).setInitialized(true);
		Step step_=this.stepService.initializeStep("10",l.size());
		this.list.get(10).setStep(step_);
		//this.sendEmail("dhia.neji@vneuron.com","Préparation des noms erronées terminée avec succès !");
		this.notificationService.createAndSendNotification("Préparation des noms erronés terminée");
	}
	return ResponseEntity.ok().build();	
}
public ResponseEntity<Boolean> validateFirstName(String firstName)
{
 	ResponseEntity<Boolean> re=restTemplate.exchange("http://127.0.0.1:5000/Testfirstname/"+firstName,HttpMethod.GET,null,new ParameterizedTypeReference<Boolean>() {
});
if(re.getBody()==true)
	return ResponseEntity.accepted().build();
else
	return ResponseEntity.badRequest().build();
}
public ResponseEntity<?> prepapreErrPrenoms()
{
	this.notificationService.createAndSendNotification("Préparation des prenom erronés en cours");
	if(this.list.get(11).isInitialized())
	{
		this.notificationService.createAndSendNotification("Préparation des prenom erronés terminée");
		//this.sendEmail("dhia.neji@vneuron.com","Préparation des dates de naissance erronées terminée avec succès !");
	}
	else
	{
		ResponseEntity<List<Customer>> re=restTemplate.exchange("http://127.0.0.1:5000/wronglastname/",HttpMethod.GET,null,new ParameterizedTypeReference<List<Customer>>() {
		});
		List<Customer> l=re.getBody();
		List<CustomerSimpleCorrectionDTO> ll=l.stream().map(this::convertToCustomerSimpleCorrectionDTO).collect(Collectors.toList());
		this.list.get(11).setData(ll);
		this.list.get(11).setInitialized(true);
		Step step_=this.stepService.initializeStep("11",l.size());
		this.list.get(11).setStep(step_);
		//this.sendEmail("dhia.neji@vneuron.com","Préparation des noms erronées terminée avec succès !");
		this.notificationService.createAndSendNotification("Préparation des prenoms erronés terminée");
	}
	return ResponseEntity.ok().build();	
}
public ResponseEntity<?> prepapreErrTel()
{
	this.notificationService.createAndSendNotification("Préparation des numéros de téléphone erronés en cours");
	if(this.list.get(12).isInitialized())
	{
		this.notificationService.createAndSendNotification("Préparation des numéros de téléphone erronés terminée");
		//this.sendEmail("dhia.neji@vneuron.com","Préparation des dates de naissance erronées terminée avec succès !");
	}
	else
	{
		List<CustomerSimpleCorrectionDTO> l=this.customerRepository.getERRTel().stream().map(this::convertToCustomerSimpleCorrectionDTO).collect(Collectors.toList());;
		this.list.get(12).setData(l);
		Step step_=this.stepService.initializeStep("12",l.size());
		this.list.get(12).setStep(step_);
		this.list.get(12).setInitialized(true);
		//this.sendEmail("dhia.neji@vneuron.com","Préparation des dates de naissance erronées terminée avec succès !");
		this.notificationService.createAndSendNotification("Préparation des numéros de téléphone erronés terminée");
	
	}
	return ResponseEntity.ok().build();	
}
public ResponseEntity<?> prepapreErrFax()
{
	this.notificationService.createAndSendNotification("Préparation des numéros de fax erronés en cours");
	if(this.list.get(13).isInitialized())
	{
		this.notificationService.createAndSendNotification("Préparation des numéros de fax erronés terminée");
		//this.sendEmail("dhia.neji@vneuron.com","Préparation des dates de naissance erronées terminée avec succès !");
	}
	else
	{
		List<CustomerSimpleCorrectionDTO> l=this.customerRepository.getERRFax().stream().map(this::convertToCustomerSimpleCorrectionDTO).collect(Collectors.toList());;
		this.list.get(13).setData(l);
		Step step_=this.stepService.initializeStep("13",l.size());
		this.list.get(13).setStep(step_);
		this.list.get(13).setInitialized(true);
		//this.sendEmail("dhia.neji@vneuron.com","Préparation des dates de naissance erronées terminée avec succès !");
		this.notificationService.createAndSendNotification("Préparation des numéros de fax erronés terminée");
	
	}
	return ResponseEntity.ok().build();	
}
public ResponseEntity<Boolean> validateLastName(String lastName)
{
 	ResponseEntity<Boolean> re=restTemplate.exchange("http://127.0.0.1:5000/Testlastname/"+lastName,HttpMethod.GET,null,new ParameterizedTypeReference<Boolean>() {
});
if(re.getBody()==true)
	return ResponseEntity.accepted().build();
else
	return ResponseEntity.badRequest().build();
}
public List<StepDTO> test()
{
return this.stepService.test();
}
public Long getcustomerCount()
{
	return this.customerRepository.count();
}
public Reporting getReporting()
{
	return this.reportingService.get();
}
}
