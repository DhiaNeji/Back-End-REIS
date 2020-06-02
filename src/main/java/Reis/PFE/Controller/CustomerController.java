package Reis.PFE.Controller;

import org.hibernate.annotations.Parameter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.util.HtmlUtils;

import Reis.PFE.Entities.Cluster;
import Reis.PFE.Entities.ClusterToEdit;
import Reis.PFE.Entities.Customer;
import Reis.PFE.Entities.CustomerDuplicationDTO;
import Reis.PFE.Entities.CustomerSimpleCorrectionDTO;
import Reis.PFE.Entities.CustomerToEdit;
import Reis.PFE.Entities.CustomerWholeNameDuplDTO;
import Reis.PFE.Entities.Greeting;
import Reis.PFE.Entities.HelloMessage;
import Reis.PFE.Entities.Notification;
import Reis.PFE.Entities.Notifications;
import Reis.PFE.Entities.Reporting;
import Reis.PFE.Entities.Step;
import Reis.PFE.Entities.StepDTO;
import Reis.PFE.Entities.Stepper;
import Reis.PFE.Entities.tst;
import Reis.PFE.Service.CustomerService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

import javax.transaction.Transactional;
@CrossOrigin("*")
@RestController
public class CustomerController {
@Autowired
private CustomerService customerService;
@Autowired
private ModelMapper modelMapper;
@MessageMapping("/hello")
@SendTo("/topic/greetings")
public Greeting greeting(HelloMessage message) throws Exception {
    Thread.sleep(1000); // simulated delay
    return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
}

@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
public void delete(@PathVariable int id)
{
	  this.customerService.delete(id);
}
@RequestMapping(value="/listerParNidAndBirthDate/{nid}/{birthDate}", method=RequestMethod.GET)
public ArrayList<Customer>listerParNidAndBirthDat(@PathVariable String birthDate,@PathVariable String nid)
{
	return this.customerService.findByNidAndBirth_date(birthDate,nid);
}
@RequestMapping(value="updateCashedData",method=RequestMethod.PUT)
public boolean prepareErrBD(@RequestBody Customer customer,@RequestParam int index)
{
	return this.customerService.updateCachedData(customer, index);
}
@RequestMapping(value="clear",method=RequestMethod.GET)
public boolean clear()
{
	return this.customerService.clear();
}
@RequestMapping(value="prepare_data",method=RequestMethod.GET)
public Notification prepare_data(@RequestParam String step)
{
	return this.customerService.prepareData(step);
}
@RequestMapping(value="getSteppers",method=RequestMethod.GET)
public List<Stepper> getsteppers()
{
	return this.customerService.getStepper();
}
@RequestMapping(value="get_data",method=RequestMethod.GET)
public List<CustomerSimpleCorrectionDTO> get_data(@RequestParam String step,@RequestParam String startRow,@RequestParam String endRow)
{
	return this.customerService.getData(step,startRow,endRow);
}
@RequestMapping(value="get_data_length",method=RequestMethod.GET)
public int get_data_length(@RequestParam String step)
{
	return this.customerService.getDataLength(step);
}
@RequestMapping(value="update_data",method=RequestMethod.PUT)
public ResponseEntity<Integer> get_data_length(@RequestParam String step,@RequestParam int index,@RequestBody CustomerSimpleCorrectionDTO customerSimpleCorrectionDTO)
{
	return this.customerService.update_data(step,customerSimpleCorrectionDTO,index);
}
@RequestMapping(value="get_edited_data_length",method=RequestMethod.GET)
public int get_edited_data_length(@RequestParam String step)
{
	return this.customerService.get_edited_data_length(step);
}
@RequestMapping(value="get_keys",method=RequestMethod.GET)
public List<String> get_keys(@RequestParam String step,@RequestParam String startRow,@RequestParam String endRow)
{
	return this.customerService.getKeys(step,startRow,endRow);
}
@RequestMapping(value="get_duplications",method=RequestMethod.GET)
public List<CustomerDuplicationDTO> getDuplications(@RequestParam String step,@RequestParam String nid)
{
	return this.customerService.getduplications(step,nid);
}
@RequestMapping(value="update_duplicationsNidBD",method=RequestMethod.PUT)
public ResponseEntity<Integer> update_duplicationsNidBD(@RequestParam String step,@RequestParam String oldNid,@RequestBody CustomerDuplicationDTO customer)
{
	return this.customerService.updateDuplications(step,oldNid, customer);
}
@RequestMapping(value="edited_data_length",method=RequestMethod.GET)
public Integer edited_data_length(@RequestParam String step)
{
	return this.customerService.getEditedDataLength(step);
}
@RequestMapping(value="get_notifications",method=RequestMethod.GET)
public List<Notification> get_notifications()
{
	List<Notification> liste=this.customerService.get_notifications();
	return liste;
}
@RequestMapping(value="get_progress",method=RequestMethod.GET)
public List<List<Integer>> get_progress()
{
	return this.customerService.getStepsProgress();
}
@RequestMapping(value="getByNid",method=RequestMethod.GET)
public List<Customer> getByNid(@RequestParam String nid)
{
	return this.customerService.findByNid(nid);
}
@RequestMapping(value="getDuplicationsByWholeName",method=RequestMethod.GET)
public List<CustomerWholeNameDuplDTO> getDuplicationsByWholeName(@RequestParam String idCluster)
{
	return this.customerService.getDuplicationsByWholeName(idCluster);
}
@RequestMapping(value="updateDuplicationsByWholeName",method=RequestMethod.PUT)
public ResponseEntity<Integer> updateDuplicationsByWholeName(@RequestParam String idCluster,@RequestParam String oldNid,@RequestBody CustomerWholeNameDuplDTO customerWholeNameDuplDTO)
{
	return this.customerService.updateWholeNameDuplications(idCluster, oldNid,customerWholeNameDuplDTO);
}
@RequestMapping(value="testdto",method=RequestMethod.GET)
public CustomerDuplicationDTO getc()
{
	Customer c=this.customerService.testDto();
	return  this.modelMapper.map(c, CustomerDuplicationDTO.class);
}
@RequestMapping(value="getProgess",method = RequestMethod.GET)
public Step getProgcess(@RequestParam String step)
{
return this.customerService.getProgcess(step);
}
@RequestMapping(value="getSumProgess",method = RequestMethod.GET)
public List<Object> getProgcess()
{
return this.customerService.getSumProgress();
}
@RequestMapping(value="getSimpleData",method = RequestMethod.GET)
public List<CustomerSimpleCorrectionDTO> getSimpleData(@RequestParam String step)
{
	return this.customerService.getSimpleData(step);
}
@RequestMapping(value="getSteps",method = RequestMethod.GET)
public List<Step> getSteps()
{
	return this.customerService.getSteps();
}
@RequestMapping(value="test2",method=RequestMethod.GET)
public ResponseEntity<String> test2()
{
	return this.customerService.prepareErrIdentityCard();
}
@RequestMapping(value="preparerErrCarteIdentite",method=RequestMethod.GET)
public DeferredResult<ResponseEntity<String>> doo()
{
	    DeferredResult<ResponseEntity<String>> output = new DeferredResult<>(20000L);

	    output.onError((Throwable t) -> {
	        output.setErrorResult(
	          ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body("An error occurred."));
	    });
	    output.onTimeout(()->{
	    	  output.setErrorResult(
	    			   ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
	    			   .body("Request timeoeeeeeeeut."));
	    });
	    ForkJoinPool.commonPool().submit(() -> {
	    	this.customerService.prepareErrIdentityCard();
	        output.setResult(ResponseEntity.ok().build());
	    });
	    return output;
}
@RequestMapping(value="preparerErrPasseport",method = RequestMethod.GET)
public DeferredResult<ResponseEntity<String>> prepareErrPassport()
{

    DeferredResult<ResponseEntity<String>> output = new DeferredResult<>(20000L);

    output.onError((Throwable t) -> {
        output.setErrorResult(
          ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("An error occurred."));
    });
    output.onTimeout(()->{
    	  output.setErrorResult(
    			   ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
    			   .body("Request timeoeeeeeeeut."));
    });
    ForkJoinPool.commonPool().submit(() -> {
    	this.customerService.prepareErrPasseport();
        output.setResult(ResponseEntity.ok().build());
    });
    return output;
	
}
@RequestMapping(value="preparerErrCarteSejour",method = RequestMethod.GET)
public DeferredResult<ResponseEntity<String>> preparerErrCarteSejour()
{

    DeferredResult<ResponseEntity<String>> output = new DeferredResult<>(20000L);

    output.onError((Throwable t) -> {
        output.setErrorResult(
          ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("An error occurred."));
    });
    output.onTimeout(()->{
    	  output.setErrorResult(
    			   ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
    			   .body("Request timeoeeeeeeeut."));
    });
    ForkJoinPool.commonPool().submit(() -> {
    	this.customerService.prepareERRCarteSejour();
        output.setResult(ResponseEntity.ok().build());
    });
    return output;
	
}
@RequestMapping(value="preparerErrMatriculeFiscale",method = RequestMethod.GET)
public DeferredResult<ResponseEntity<String>> preparerErrMatriculeFiscale()
{

    DeferredResult<ResponseEntity<String>> output = new DeferredResult<>(20000L);

    output.onError((Throwable t) -> {
        output.setErrorResult(
          ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("An error occurred."));
    });
    output.onTimeout(()->{
    	  output.setErrorResult(
    			   ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
    			   .body("Request timeoeeeeeeeut."));
    });
    ForkJoinPool.commonPool().submit(() -> {
    	this.customerService.prepareERRMatriculeFiscal();
        output.setResult(ResponseEntity.ok().build());
    });
    return output;
}
@RequestMapping(value="preparerErrRegistreDeCommerce",method = RequestMethod.GET)
public DeferredResult<ResponseEntity<String>> preparerErrRegistreDeCommerce()
{

    DeferredResult<ResponseEntity<String>> output = new DeferredResult<>(20000L);

    output.onError((Throwable t) -> {
        output.setErrorResult(
          ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("An error occurred."));
    });
    output.onTimeout(()->{
    	  output.setErrorResult(
    			   ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
    			   .body("Request timeoeeeeeeeut."));
    });
    ForkJoinPool.commonPool().submit(() -> {
    	this.customerService.prepareERRRegistreDeCommerce();
        output.setResult(ResponseEntity.ok().build());
    });
    return output;
}
@RequestMapping(value="preparerErrTin",method = RequestMethod.GET)
public DeferredResult<ResponseEntity<String>> prepaprerErrTin()
{

    DeferredResult<ResponseEntity<String>> output = new DeferredResult<>(20000L);

    output.onError((Throwable t) -> {
        output.setErrorResult(
          ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("An error occurred."));
    });
    output.onTimeout(()->{
    	  output.setErrorResult(
    			   ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
    			   .body("Request timeoeeeeeeeut."));
    });
    ForkJoinPool.commonPool().submit(() -> {
    	this.customerService.prepareERRTin();
        output.setResult(ResponseEntity.ok().build());
    });
    return output;
}
@RequestMapping(value="preparerErrDateDeNaissance",method = RequestMethod.GET)
public DeferredResult<ResponseEntity<String>> preparerErrDateDeNaissance()
{

    DeferredResult<ResponseEntity<String>> output = new DeferredResult<>(20000L);

    output.onError((Throwable t) -> {
        output.setErrorResult(
          ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("An error occurred."));
    });
    output.onTimeout(()->{
    	  output.setErrorResult(
    			   ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
    			   .body("Request timeoeeeeeeeut."));
    });
    ForkJoinPool.commonPool().submit(() -> {
    	this.customerService.prepareERRDateDeNaissance();
        output.setResult(ResponseEntity.ok().build());
    });
    return output;
}
@RequestMapping(value="preparerErrNationalites",method = RequestMethod.GET)
public DeferredResult<ResponseEntity<String>> preparerErrNationalites()
{

    DeferredResult<ResponseEntity<String>> output = new DeferredResult<>(20000L);

    output.onError((Throwable t) -> {
        output.setErrorResult(
          ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("An error occurred."));
    });
    output.onTimeout(()->{
    	  output.setErrorResult(
    			   ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
    			   .body("Request timeoeeeeeeeut."));
    });
    ForkJoinPool.commonPool().submit(() -> {
    	this.customerService.prepareERRNationalites();
        output.setResult(ResponseEntity.ok().build());
    });
    return output;
}
@RequestMapping(value="preparerErrEmail",method = RequestMethod.GET)
public DeferredResult<ResponseEntity<String>> preparerErrEmail()
{

    DeferredResult<ResponseEntity<String>> output = new DeferredResult<>(20000L);

    output.onError((Throwable t) -> {
        output.setErrorResult(
          ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("An error occurred."));
    });
    output.onTimeout(()->{
    	  output.setErrorResult(
    			   ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
    			   .body("Request timeoeeeeeeeut."));
    });
    ForkJoinPool.commonPool().submit(() -> {
    	this.customerService.preparerErrEmail();
        output.setResult(ResponseEntity.ok().build());
    });
    return output;
}
@RequestMapping(value="preparerErrTel",method = RequestMethod.GET)
public DeferredResult<ResponseEntity<String>> preparerErrTel()
{

    DeferredResult<ResponseEntity<String>> output = new DeferredResult<>(20000L);

    output.onError((Throwable t) -> {
        output.setErrorResult(
          ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("An error occurred."));
    });
    output.onTimeout(()->{
    	  output.setErrorResult(
    			   ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
    			   .body("Request timeoeeeeeeeut."));
    });
    ForkJoinPool.commonPool().submit(() -> {
    	this.customerService.prepapreErrTel();
        output.setResult(ResponseEntity.ok().build());
    });
    return output;
}
@RequestMapping(value="preparerErrFax",method = RequestMethod.GET)
public DeferredResult<ResponseEntity<String>> preparerErrFax()
{

    DeferredResult<ResponseEntity<String>> output = new DeferredResult<>(20000L);

    output.onError((Throwable t) -> {
        output.setErrorResult(
          ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("An error occurred."));
    });
    output.onTimeout(()->{
    	  output.setErrorResult(
    			   ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
    			   .body("Request timeoeeeeeeeut."));
    });
    ForkJoinPool.commonPool().submit(() -> {
    	this.customerService.prepapreErrFax();
        output.setResult(ResponseEntity.ok().build());
    });
    return output;
}
@RequestMapping(value="preparerDuplicationsNidDateNaissance",method = RequestMethod.GET)
public DeferredResult<ResponseEntity<String>> preparerDuplicationsNidDateNaissance()
{

    DeferredResult<ResponseEntity<String>> output = new DeferredResult<>(20000L);

    output.onError((Throwable t) -> {
        output.setErrorResult(
          ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("An error occurred."));
    });
    output.onTimeout(()->{
    	  output.setErrorResult(
    			   ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
    			   .body("Request timeoeeeeeeeut."));
    });
    ForkJoinPool.commonPool().submit(() -> {
    	this.customerService.prepareDuplicationNidDateNaissance();
        output.setResult(ResponseEntity.ok().build());
    });
    return output;
}
@RequestMapping(value="preparerDuplicationsNidPP",method = RequestMethod.GET)
public DeferredResult<ResponseEntity<String>> preparerDuplicationsNidPP()
{

    DeferredResult<ResponseEntity<String>> output = new DeferredResult<>(20000L);

    output.onError((Throwable t) -> {
        output.setErrorResult(
          ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("An error occurred."));
    });
    output.onTimeout(()->{
    	  output.setErrorResult(
    			   ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
    			   .body("Request timeoeeeeeeeut."));
    });
    ForkJoinPool.commonPool().submit(() -> {
    	this.customerService.prepareDuplicationNidDPP();
        output.setResult(ResponseEntity.ok().build());
    });
    return output;
}
@RequestMapping(value="prepareDuplicationWholeName",method = RequestMethod.GET)
public DeferredResult<ResponseEntity<String>> prepareDuplicationWholeName()
{

    DeferredResult<ResponseEntity<String>> output = new DeferredResult<>(20000L);

    output.onError((Throwable t) -> {
        output.setErrorResult(
          ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("An error occurred."));
    });
    output.onTimeout(()->{
    	  output.setErrorResult(
    			   ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
    			   .body("Request timeoeeeeeeeut."));
    });
    ForkJoinPool.commonPool().submit(() -> {
    	this.customerService.prepareDuplicationWholeName();
        output.setResult(ResponseEntity.ok().build());
    });
    return output;
}
@RequestMapping(value="prepapreErrNoms",method = RequestMethod.GET)
public DeferredResult<ResponseEntity<String>> prepapreErrNoms()
{

    DeferredResult<ResponseEntity<String>> output = new DeferredResult<>(20000L);

    output.onError((Throwable t) -> {
        output.setErrorResult(
          ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("An error occurred."));
    });
    output.onTimeout(()->{
    	  output.setErrorResult(
    			   ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
    			   .body("Request timeoeeeeeeeut."));
    });
    ForkJoinPool.commonPool().submit(() -> {
    	this.customerService.prepapreErrNoms();
        output.setResult(ResponseEntity.ok().build());
    });
    return output;
} 
@RequestMapping(value="validatefn",method = RequestMethod.GET)
public ResponseEntity<?> validatefn(@RequestParam String firstName)
{
	return this.customerService.validateFirstName(firstName);
}
@RequestMapping(value="prepapreErrPrenoms",method = RequestMethod.GET)
public DeferredResult<ResponseEntity<String>> prepapreErrPrenoms()
{

    DeferredResult<ResponseEntity<String>> output = new DeferredResult<>(2000000L);

    output.onError((Throwable t) -> {
        output.setErrorResult(
          ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("An error occurred."));
    });
    output.onTimeout(()->{
    	  output.setErrorResult(
    			   ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
    			   .body("Request timeoeeeeeeeut."));
    });
    ForkJoinPool.commonPool().submit(() -> {
    	this.customerService.prepapreErrPrenoms();
        output.setResult(ResponseEntity.ok().build());
    });
    return output;
} 
@RequestMapping(value="validateln",method = RequestMethod.GET)
public ResponseEntity<?> validateln(@RequestParam String lastName)
{
	return this.customerService.validateFirstName(lastName);
}
@RequestMapping(value="teststep",method = RequestMethod.GET)
public List<StepDTO> teststep()
{
	return this.customerService.test();
}
@RequestMapping(value="getReporting",method = RequestMethod.GET)
public Reporting getReporting()
{
	return this.customerService.getReporting();
}
}
