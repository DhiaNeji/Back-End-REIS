package Reis.PFE.Entities;

public class Address {
private String adressLine1;
private String adressLine2;
private String city;
private String coutry;
private String zipcode;
public String getAdressLine1() {
	return adressLine1;
}
public void setAdressLine1(String adressLine1) {
	this.adressLine1 = adressLine1;
}
public String getAdressLine2() {
	return adressLine2;
}
public void setAdressLine2(String adressLiné) {
	this.adressLine2 = adressLiné;
}
public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}
public String getCoutry() {
	return coutry;
}
public void setCoutry(String coutry) {
	this.coutry = coutry;
}
public String getZipcode() {
	return zipcode;
}
public void setZipcode(String zipcode) {
	this.zipcode = zipcode;
}
public Address(String adressLine1, String adressLine2, String city, String coutry, String zipcode) {
	super();
	this.adressLine1 = adressLine1;
	this.adressLine2 = adressLine2;
	this.city = city;
	this.coutry = coutry;
	this.zipcode = zipcode;
}

}
