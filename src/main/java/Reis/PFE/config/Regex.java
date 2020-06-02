package Reis.PFE.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "settings.regex")
@Configuration
@Component
public class Regex {
private String birthDateRegex;
private String cinTNRegex;
private String passeportRegex;
private String carteSejourRegex;
private String matriculefiscaleRegex;
private String registrecommercial;
private String emailRegex;

public String getEmailRegex() {
	return emailRegex;
}
public void setEmailRegex (String emailRegex) {
	this.emailRegex = emailRegex;
}
public String getRegistrecommercial() {
	return registrecommercial;
}
public void setRegistrecommercial(String registrecommercial) {
	this.registrecommercial = registrecommercial;
}
public String getBirthDateRegex() {
	return birthDateRegex;
}
public void setBirthDateRegex(String birthDateRegex) {
	this.birthDateRegex = birthDateRegex;
}
public String getCinTNRegex() {
	return cinTNRegex;
}
public void setCinTNRegex(String cinRegex) {
	this.cinTNRegex = cinRegex;
}
public String getPasseportRegex() {
	return passeportRegex;
}
public void setPasseportRegex(String passeportRegex) {
	this.passeportRegex = passeportRegex;
}
public String getCarteSejourRegex() {
	return carteSejourRegex;
}
public void setCarteSejourRegex(String carteSejourRegex) {
	this.carteSejourRegex = carteSejourRegex;
}
public String getMatriculefiscaleRegex() {
	return matriculefiscaleRegex;
}
public void setMatriculefiscaleRegex(String matriculefiscaleRegex) {
	this.matriculefiscaleRegex = matriculefiscaleRegex;
}

}
