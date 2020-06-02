package Reis.PFE.Entities;

import java.util.List;
import java.util.Map;

public class Stepper {
private boolean isInitialized=false;
private Step step;
private List<CustomerSimpleCorrectionDTO>data;
private Map<String,List<CustomerDuplicationDTO>> duplicatedData;
private Map<String,List<CustomerWholeNameDuplDTO>> wholeNameDup;
public Stepper(boolean isInitialized, Step step, List<CustomerSimpleCorrectionDTO> data,
		Map<String, List<CustomerDuplicationDTO>> duplicatedData) {
	super();
	this.isInitialized = isInitialized;
	this.step = step;
	this.data = data;
	this.duplicatedData = duplicatedData;
}

public Stepper(boolean isInitialized, Step step, List<CustomerSimpleCorrectionDTO> data,
		Map<String, List<CustomerDuplicationDTO>> duplicatedData,
		Map<String, List<CustomerWholeNameDuplDTO>> wholeNameDup) {
	super();
	this.isInitialized = isInitialized;
	this.step = step;
	this.data = data;
	this.duplicatedData = duplicatedData;
	this.wholeNameDup = wholeNameDup;
}

public Stepper()
{
	
}
public Stepper(boolean isInit)
{
	this.isInitialized=isInit;
}
public boolean isInitialized() {
	return isInitialized;
}
public void setInitialized(boolean isInitialized) {
	this.isInitialized = isInitialized;
}
public Step getStep() {
	return step;
}
public void setStep(Step step) {
	this.step = step;
}
public List<CustomerSimpleCorrectionDTO> getData() {
	return data;
}
public void setData(List<CustomerSimpleCorrectionDTO> data) {
	this.data = data;
}
public Map<String, List<CustomerDuplicationDTO>> getDuplicatedData() {
	return duplicatedData;
}
public void setDuplicatedData(Map<String, List<CustomerDuplicationDTO>> duplicatedData) {
	this.duplicatedData = duplicatedData;
}

public Map<String, List<CustomerWholeNameDuplDTO>> getWholeNameDup() {
	return wholeNameDup;
}

public void setWholeNameDup(Map<String, List<CustomerWholeNameDuplDTO>> wholeNameDup) {
	this.wholeNameDup = wholeNameDup;
}

}
