package Reis.PFE.Entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.TypeDef;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import java.util.List;
import Reis.PFE.dao.Auditable;

@Entity
@Table(name="customers_clean") //modelmapper
@TypeDef(name = "AddressType", typeClass = AddressType.class)

public class Customer extends Auditable<String> implements Serializable{
@Id
private int id;
private Date created_on;
private int age;
@Column(name="birth_date")
private String birthdate;
private String business_name;
private String citizenship;
private int customer_type;
private String email;
private String entity_type;
private String first_name;
private String last_name;
private String nationality;
private String tel1;
private String tel2;
private String tin;
private String nid;
private String table_initiale;
private String Not_Edited_in;
@Column(name="step_1")
private String step_1;
@Column(name="step_2")
private String step_2;
@Column(name="step_3")
private String step_3;
@Column(name="step_4")
private String step_4;
@Column(name="step_5")
private String step_5;
@Column(name="step_6")
private String step_6;
public Customer(int id, Date created_on, int age, String birth_date, String business_name, String citizenship,
		int customer_type, String email, String entity_type, String first_name, String last_name, String nationality,
		String tel1, String tel2, String tin, String nid,String table_initiale,String not_edited_in,String step_1,String step_2,String step_3,
		String step_4,String step_5,String step_6) {
	super();
	this.id = id;
	this.created_on = created_on;
	this.age = age;
	this.birthdate = birth_date;
	this.business_name = business_name;
	this.citizenship = citizenship;
	this.customer_type = customer_type;
	this.email = email;
	this.entity_type = entity_type;
	this.first_name = first_name;
	this.last_name = last_name;
	this.nationality = nationality;
	this.tel1 = tel1;
	this.tel2 = tel2;
	this.tin = tin;
	this.nid = nid;
	this.table_initiale=table_initiale;
	this.Not_Edited_in=not_edited_in;
	this.step_1=step_1;
	this.step_2=step_2;
	this.step_3=step_3;
	this.step_4=step_4;
	this.step_5=step_5;
	this.step_6=step_6;
}
public Customer()
{
	
}

public String getStep_1() {
	return step_1;
}
public void setStep_1(String step_1) {
	this.step_1 = step_1;
}
public String getStep_2() {
	return step_2;
}
public void setStep_2(String step_2) {
	this.step_2 = step_2;
}
public String getStep_3() {
	return step_3;
}
public void setStep_3(String step_3) {
	this.step_3 = step_3;
}
public String getStep_4() {
	return step_4;
}
public void setStep_4(String step_4) {
	this.step_4 = step_4;
}
public String getStep_5() {
	return step_5;
}
public void setStep_5(String step_5) {
	this.step_5 = step_5;
}
public String getStep_6() {
	return step_6;
}
public void setStep_6(String step_6) {
	this.step_6 = step_6;
}
public String getNot_Edited_in() {
	return Not_Edited_in;
}
public void setNot_Edited_in(String not_Edited_in) {
	Not_Edited_in = not_Edited_in;
}
public String getTable_initiale() {
	return table_initiale;
}
public void setTable_initiale(String table_initiale) {
	this.table_initiale = table_initiale;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public Date getCreated_on() {
	return created_on;
}
public void setCreated_on(Date created_on) {
	this.created_on = created_on;
}
public int getAge() {
	return age;
}
public void setAge(int age) {
	this.age = age;
}
public String getBirthdate() {
	return this.birthdate;
}
public void setBirthdate(String birth_date) {
	this.birthdate = birth_date;
}
public String getBusiness_name() {
	return business_name;
}
public void setBusiness_name(String business_name) {
	this.business_name = business_name;
}
public String getCitizenship() {
	return citizenship;
}
public void setCitizenship(String citizenship) {
	this.citizenship = citizenship;
}
public int getCustomer_type() {
	return customer_type;
}
public void setCustomer_type(int customer_type) {
	this.customer_type = customer_type;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getEntity_type() {
	return entity_type;
}
public void setEntity_type(String entity_type) {
	this.entity_type = entity_type;
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
public String getNationality() {
	return nationality;
}
public void setNationality(String nationality) {
	this.nationality = nationality;
}
public String getTel1() {
	return tel1;
}
public void setTel1(String tel1) {
	this.tel1 = tel1;
}
public String getTel2() {
	return tel2;
}
public void setTel2(String tel2) {
	this.tel2 = tel2;
}
public String getTin() {
	return tin;
}
public void setTin(String tin) {
	this.tin = tin;
}
public String getNid() {
	return nid;
}
public void setNid(String nid) {
	this.nid = nid;
}
@Override
	public boolean equals(Object obj) {
	if(!(obj instanceof Customer ))
		return false;
	else
	{
		if(((Customer)obj).id==this.id)
		{
	return true;
		}
		else 
	return false;
	}
	}
}
