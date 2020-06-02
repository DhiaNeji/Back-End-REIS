package Reis.PFE.Entities;

public class Edited_in {
private String Chant;
private boolean edited;
public String getChant() {
	return Chant;
}
public void setChant(String chant) {
	Chant = chant;
}
public boolean isEdited() {
	return edited;
}
public void setEdited(boolean edited) {
	this.edited = edited;
}
public Edited_in(String chant, boolean edited) {
	super();
	Chant = chant;
	this.edited = edited;
}

}
