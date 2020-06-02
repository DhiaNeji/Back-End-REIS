package Reis.PFE.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Reis.PFE.Entities.Reporting;
import Reis.PFE.Entities.Step;
import Reis.PFE.Entities.StepDTO;
@Service
public class ReportingService {
@Autowired
private StepService stepService;
@Autowired
private DataCollectionService dataCollectionService;
@Autowired
private CustomerService customerService;
public Reporting get()
{
	Reporting reporting =new Reporting();
	long data_collection_count=this.dataCollectionService.dataCollectionCount();
	long customers_count=this.customerService.getcustomerCount();
	long err_data_count=this.stepService.getErrDataCount();
	long edited_data_count=this.stepService.getEditedDataCount();
	long ignored_data_count=this.stepService.getIgnoredDataCount();
	reporting.setData_collection_count(data_collection_count);
	reporting.setCustomers_count(customers_count);
	reporting.setErr_data_count(err_data_count);
	reporting.setEdited_data_count(edited_data_count);
	reporting.setIgnored_data_count(ignored_data_count);
	List<StepDTO> l=this.stepService.getEditedDataLength();
	List<StepDTO> ll=this.stepService.getGroupedSteps();
	List<StepDTO> lll=this.stepService.getDuplicationSteps();
	reporting.setLast_cleaning_progress(l);
	reporting.setLast_cleaning_edited_ignored_data(ll);
	reporting.setLast_cleaning_err_data(lll);
	return reporting;
}
}
