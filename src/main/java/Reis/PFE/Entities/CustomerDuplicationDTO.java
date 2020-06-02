package Reis.PFE.Entities;

public class CustomerDuplicationDTO {
private long id;
private String nid;
private int age;
private String birthdate;
private String first_name;
private String last_name;
private String step_14;
private String step_15;
private boolean to_delete;
private String currentStep;

public CustomerDuplicationDTO(long id, String nid, int age, String birthdate, String first_name, String last_name,
		String step_14, String step_15, boolean to_delete, String currentStep) {
	super();
	this.id = id;
	this.nid = nid;
	this.age = age;
	this.birthdate = birthdate;
	this.first_name = first_name;
	this.last_name = last_name;
	this.step_14 = step_14;
	this.step_15 = step_15;
	this.to_delete = to_delete;
	this.currentStep = currentStep;
}
public CustomerDuplicationDTO()
{
	
}
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getNid() {
	return nid;
}
public void setNid(String nid) {
	this.nid = nid;
}
public int getAge() {
	return age;
}
public void setAge(int age) {
	this.age = age;
}
public String getBirthdate() {
	return birthdate;
}
public void setBirthdate(String birthdate) {
	this.birthdate = birthdate;
}
public String getFirst_name() {
	return first_name;
}
public void setFirst_name(String first_name) {
	this.first_name = first_name;
}
public String getLast_name() {
	return last_name;
}
public void setLast_name(String last_name) {
	this.last_name = last_name;
}

public boolean isTo_delete() {
	return to_delete;
}
public void setTo_delete(boolean to_delete) {
	this.to_delete = to_delete;
}
public String getCurrentStep() {
	return currentStep;
}
public void setCurrentStep(String currentStep) {
	this.currentStep = currentStep;
}
public String getStep_14() {
	return step_14;
}
public void setStep_14(String step_14) {
	this.step_14 = step_14;
}
public String getStep_15() {
	return step_15;
}
public void setStep_15(String step_15) {
	this.step_15 = step_15;
}
}
