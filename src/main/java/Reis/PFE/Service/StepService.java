package Reis.PFE.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.ThreeTenBackPortConverters.DateToLocalDateTimeConverter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import Reis.PFE.Entities.CustomerDuplicationDTO;
import Reis.PFE.Entities.CustomerSimpleCorrectionDTO;
import Reis.PFE.Entities.CustomerWholeNameDuplDTO;
import Reis.PFE.Entities.DataCollection;
import Reis.PFE.Entities.Step;
import Reis.PFE.Entities.StepDTO;
import Reis.PFE.Entities.StepId;
import Reis.PFE.dao.StepRepository;

@Service
public class StepService {
@Autowired
private StepRepository stepRepository;
@Autowired
private DataCollectionService dataCollectionService;
@Autowired
private ModelMapper modelMapper;
public Step initializeStep(String step_,int data_length)
{

DataCollection dataCollection=this.dataCollectionService.getLast();
Optional<Step> step=this.stepRepository.findById(new StepId(Integer.valueOf(step_),dataCollection.getId()));
Step s=null;
if(step.isPresent())
{
	s=step.get();
	s.setStarted_on(LocalDateTime.now());
	s.setData_length(data_length);
}
this.stepRepository.save(s);
return s;
}
public void updateSimple(String step_,CustomerSimpleCorrectionDTO customerSimpleCorrectionDTO)
{
	DataCollection dataCollection=this.dataCollectionService.getLast();
	Step step=this.stepRepository.getLastStep(Integer.valueOf(step_),dataCollection.getId());
	switch (step_) {
	case "1":
		if(customerSimpleCorrectionDTO.getStep_1()==null)
		{
			step.setEdited_data_length(step.getEdited_data_length()+1);
		}
		else
		{
			if(customerSimpleCorrectionDTO.getStep_1().equalsIgnoreCase("false"))
			{
				step.setIgnored_data_length(step.getIgnored_data_length()+1);
			}
			else
				step.setEdited_data_length(step.getEdited_data_length()+1);	
		}
		this.stepRepository.save(step);
		break;
	case "2":
		if(customerSimpleCorrectionDTO.getStep_2()==null)
		{
			step.setEdited_data_length(step.getEdited_data_length()+1);
		}
		else
		{
			if(customerSimpleCorrectionDTO.getStep_2().equalsIgnoreCase("false"))
				{
				step.setIgnored_data_length(step.getIgnored_data_length()+1);
				}
			else
				step.setEdited_data_length(step.getEdited_data_length()+1);	
		}
		this.stepRepository.save(step);
		break;

	case "3":
		if(customerSimpleCorrectionDTO.getStep_3()==null)
		{
			step.setEdited_data_length(step.getEdited_data_length()+1);
		}
		else
		{
			if(customerSimpleCorrectionDTO.getStep_3().equalsIgnoreCase("false"))
			{
				step.setIgnored_data_length(step.getIgnored_data_length()+1);
			}
			else
				step.setEdited_data_length(step.getEdited_data_length()+1);	
		}
		this.stepRepository.save(step);
	break;
	case "4":
		if(customerSimpleCorrectionDTO.getStep_4()==null)
		{
			step.setEdited_data_length(step.getEdited_data_length()+1);
		}
		else
		{
			if(customerSimpleCorrectionDTO.getStep_4().equalsIgnoreCase("false"))
			{
				step.setIgnored_data_length(step.getIgnored_data_length()+1);
			}
			else
				step.setEdited_data_length(step.getEdited_data_length()+1);	
		}
		this.stepRepository.save(step);
	break;
	case "5":
		if(customerSimpleCorrectionDTO.getStep_5()==null)
		{
			step.setEdited_data_length(step.getEdited_data_length()+1);
		}
		else
		{
			if(customerSimpleCorrectionDTO.getStep_5().equalsIgnoreCase("false"))
			{
				step.setIgnored_data_length(step.getIgnored_data_length()+1);
			}
			else
				step.setEdited_data_length(step.getEdited_data_length()+1);	
		}
		this.stepRepository.save(step);
	break;

	case "6":
		if(customerSimpleCorrectionDTO.getStep_6()==null)
		{
			step.setEdited_data_length(step.getEdited_data_length()+1);
		}
		else
		{
			if(customerSimpleCorrectionDTO.getStep_6().equalsIgnoreCase("false"))
			{
				step.setIgnored_data_length(step.getIgnored_data_length()+1);
			}
			else
				step.setEdited_data_length(step.getEdited_data_length()+1);	
		}
		this.stepRepository.save(step);
	break;

	case "7":
		if(customerSimpleCorrectionDTO.getStep_7()==null)
		{
			step.setEdited_data_length(step.getEdited_data_length()+1);
		}
		else
		{
			if(customerSimpleCorrectionDTO.getStep_7().equalsIgnoreCase("false"))
			{
				step.setIgnored_data_length(step.getIgnored_data_length()+1);
			}
			else
				step.setEdited_data_length(step.getEdited_data_length()+1);	
		}
		this.stepRepository.save(step);
	break;

	case "8":
		if(customerSimpleCorrectionDTO.getStep_8()==null)
		{
			step.setEdited_data_length(step.getEdited_data_length()+1);
		}
		else
		{
			if(customerSimpleCorrectionDTO.getStep_8().equalsIgnoreCase("false"))
			{
				step.setIgnored_data_length(step.getIgnored_data_length()+1);
			}
			else
				step.setEdited_data_length(step.getEdited_data_length()+1);	
		}
		this.stepRepository.save(step);
	break;
	case "9":
		if(customerSimpleCorrectionDTO.getStep_9()==null)
		{
			step.setEdited_data_length(step.getEdited_data_length()+1);
		}
		else
		{
			if(customerSimpleCorrectionDTO.getStep_9().equalsIgnoreCase("false"))
			{
				step.setIgnored_data_length(step.getIgnored_data_length()+1);
			}
			else
				step.setEdited_data_length(step.getEdited_data_length()+1);	
		}
		this.stepRepository.save(step);
	break;
	case "10":
		if(customerSimpleCorrectionDTO.getStep_10()==null)
		{
			step.setEdited_data_length(step.getEdited_data_length()+1);
		}
		else
		{
			if(customerSimpleCorrectionDTO.getStep_10().equalsIgnoreCase("false"))
			{
				step.setIgnored_data_length(step.getIgnored_data_length()+1);
			}
			else
				step.setEdited_data_length(step.getEdited_data_length()+1);	
		}
		this.stepRepository.save(step);
	break;
	case "11":
		if(customerSimpleCorrectionDTO.getStep_11()==null)
		{
			step.setEdited_data_length(step.getEdited_data_length()+1);
		}
		else
		{
			if(customerSimpleCorrectionDTO.getStep_11().equalsIgnoreCase("false"))
			{
				step.setIgnored_data_length(step.getIgnored_data_length()+1);
			}
			else
				step.setEdited_data_length(step.getEdited_data_length()+1);	
		}
		this.stepRepository.save(step);
	break;

	case "12":
		if(customerSimpleCorrectionDTO.getStep_12()==null)
		{
			step.setEdited_data_length(step.getEdited_data_length()+1);
		}
		else
		{
			if(customerSimpleCorrectionDTO.getStep_12().equalsIgnoreCase("false"))
			{
				step.setIgnored_data_length(step.getIgnored_data_length()+1);
			}
			else
				step.setEdited_data_length(step.getEdited_data_length()+1);	
		}
		this.stepRepository.save(step);
	break;

	case "13":
		if(customerSimpleCorrectionDTO.getStep_13()==null)
		{
			step.setEdited_data_length(step.getEdited_data_length()+1);
		}
		else
		{
			if(customerSimpleCorrectionDTO.getStep_13().equalsIgnoreCase("false"))
			{
				step.setIgnored_data_length(step.getIgnored_data_length()+1);
			}
			else
				step.setEdited_data_length(step.getEdited_data_length()+1);	
		}
		this.stepRepository.save(step);
	break;
	default:
		
	}
	
}
public Step getStep(String step_)
{
	DataCollection dataCollection=this.dataCollectionService.getLast();
	Step step=this.stepRepository.getLastStep(Integer.valueOf(step_),dataCollection.getId());
return step;	
}
public Step initilizeSimpleDuplicationStep(String step_,int data_length,int duplicates_data_length)
{
	DataCollection dataCollection=this.dataCollectionService.getLast();
	Optional<Step> step=this.stepRepository.findById(new StepId(Integer.valueOf(step_),dataCollection.getId()));
	Step s=null;
	if(step.isPresent())
	{
		s=step.get();
		s.setData_length(data_length);
		s.setStarted_on(LocalDateTime.now());
		s.setDuplicates_data_length(duplicates_data_length);
	}
	this.stepRepository.save(s);
	return s;
}
public void updateSimpleDuplicationStep(String step_,CustomerDuplicationDTO customerDuplicationDTO,boolean isFinished)
{
	DataCollection dataCollection=this.dataCollectionService.getLast();
	Step step=this.stepRepository.getLastStep(Integer.valueOf(step_),dataCollection.getId());
	switch (step_) {
	case "14":
		if(customerDuplicationDTO.isTo_delete())
		{
			step.setDeleted_data_length(step.getDeleted_data_length()+1);
		}
		if(customerDuplicationDTO.getStep_14()!=null)
		{
		if(customerDuplicationDTO.getStep_14().equalsIgnoreCase("false"))
		{
			step.setIgnored_data_length(step.getIgnored_data_length()+1);
		}
		else
			step.setEdited_data_length(step.getEdited_data_length()+1);
		}
		if(isFinished==true)
		{
			step.setEdited_duplicates_length(step.getEdited_duplicates_length()+1);
		}
		this.stepRepository.save(step);
		break;
	case "15":
		if(customerDuplicationDTO.isTo_delete())
		{
			step.setDeleted_data_length(step.getDeleted_data_length()+1);
		}
		if(customerDuplicationDTO.getStep_15()!=null)
		{
		if(customerDuplicationDTO.getStep_15().equalsIgnoreCase("false"))
		{
			step.setIgnored_data_length(step.getIgnored_data_length()+1);
		}
		else
			step.setEdited_data_length(step.getEdited_data_length()+1);
		}
		if(isFinished==true)
		{
			step.setEdited_duplicates_length(step.getEdited_duplicates_length()+1);
		}
		this.stepRepository.save(step);
		break;

	default:
		break;
	}	
}
public void updateWholeNameDuplicationStep(CustomerWholeNameDuplDTO customerWholeNameDuplDTO,boolean isFinished)
{
	DataCollection dataCollection=this.dataCollectionService.getLast();
	Step step=this.stepRepository.getLastStep(Integer.valueOf("16"),dataCollection.getId());
	if(customerWholeNameDuplDTO.isTo_delete())
	{

		step.setDeleted_data_length(step.getDeleted_data_length()+1);
	}
	if(customerWholeNameDuplDTO.getStep_16()!=null)
	{
	if(customerWholeNameDuplDTO.getStep_16().equalsIgnoreCase("false"))
	{
		step.setIgnored_data_length(step.getIgnored_data_length()+1);
	}
	else
		step.setEdited_data_length(step.getEdited_data_length()+1);
	}
	if(isFinished==true)
	{
		step.setEdited_duplicates_length(step.getEdited_duplicates_length()+1);
	}
	this.stepRepository.save(step);
}
public List<Object>getSum()
{
	return this.stepRepository.getsum();
}
public List<Step> findAll() {
	return this.stepRepository.findAll();
}
public List<StepDTO> test()
{
	List<Step> l=this.stepRepository.getOrderedStepsById();
	List<StepDTO>ll=new ArrayList<StepDTO>();
	int j=0;
	int k=1;
	for(int i=0;i<16;i++)
	{
		Step s=l.get(j);
		Step ss=l.get(k);
		StepDTO stepDTO=new StepDTO();
		stepDTO.setId(String.valueOf(s.getStepId().getId()));
		stepDTO.setData_length(s.getData_length()+ss.getData_length());
		stepDTO.setEdited_data_length(s.getEdited_data_length()+s.getIgnored_data_length()+ss.getEdited_data_length()+ss.getIgnored_data_length());
		ll.add(stepDTO);
		j=j+2;
		k=k+2;
	}
	return ll;

}
public long getErrDataCount()
{
	return this.stepRepository.err_data_count();
}
public long getEditedDataCount()
{
	return this.stepRepository.editeddata_count();
}
public long getIgnoredDataCount()
{
	return this.stepRepository.ignored_data_count();
}
public List<StepDTO> getStepsByCollection()
{
	DataCollection dataCollection=this.dataCollectionService.getLast();
	return this.stepRepository.getStepsByCollection(dataCollection.getId()).stream().map(c->this.convertFromStepToStepDTO(c)).collect(Collectors.toList());
}
public StepDTO convertFromStepToStepDTO(Step step)
{
	StepDTO s=this.modelMapper.map(step, StepDTO.class);
	s.setEdited_data_length(step.getEdited_data_length()+step.getIgnored_data_length());
	s.setRested_data_length(s.getData_length()-s.getEdited_data_length());
	s.setId(String.valueOf(step.getStepId().getId()));
	s.setData_collection_id(step.getStepId().getData_collection_id());
	return s;
}
public List<StepDTO> getEditedDataLength()
{
	return this.stepRepository.getOrderedSteps().stream().map(c->this.convertFromStepToStepDTO(c)).collect(Collectors.toList());
}
public List<StepDTO> getGroupedSteps()
{
	List<Step> l=this.stepRepository.getOrderedStepsById();
	List<StepDTO>ll=new ArrayList<StepDTO>();
	int j=0;
	int k=1;
	for(int i=0;i<16;i++)
	{
		Step s=l.get(j);
		Step ss=l.get(k);
		StepDTO stepDTO=new StepDTO();
		stepDTO.setId(String.valueOf(s.getStepId().getId()));
		stepDTO.setData_length(s.getData_length()+ss.getData_length());
		stepDTO.setEdited_data_length(s.getEdited_data_length()+s.getIgnored_data_length()+ss.getEdited_data_length()+ss.getIgnored_data_length());
		stepDTO.setIgnored_data_length(s.getIgnored_data_length()+ss.getIgnored_data_length());
		ll.add(i,stepDTO);
		j=j+2;
		k=k+2;
	}
	return ll;
}
public List<StepDTO> getDuplicationSteps()
{
	List<Step> l=this.stepRepository.getOrderedDuplicationStepsById();
	List<StepDTO>ll=new ArrayList<StepDTO>();
	System.out.println("sssssssssssssssssssssssssssssss"+l.get(2).getEdited_duplicates_length());
	int j=0; 
	int k=1;
	for(int i=0;i<3;i++)
	{
		Step s=l.get(j);
		Step ss=l.get(k);
		StepDTO stepDTO=new StepDTO();
		stepDTO.setId(String.valueOf(s.getStepId().getId()));
		stepDTO.setDuplicates_data_length(s.getDuplicates_data_length()+ss.getDuplicates_data_length());
		stepDTO.setEdited_duplicates_length(s.getEdited_duplicates_length()+s.getEdited_duplicates_length());
		stepDTO.setDeleted_data_length(s.getDeleted_data_length()+ss.getDeleted_data_length());
		ll.add(i,stepDTO);
		j=j+2;
		k=k+2;
	}
	return ll;
}
}