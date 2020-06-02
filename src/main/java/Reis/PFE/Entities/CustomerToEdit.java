package Reis.PFE.Entities;

import java.util.Date;

public class CustomerToEdit extends Customer{
private boolean toEdit;
public CustomerToEdit(Customer c,boolean toEdit)
{
	//super(c.getId(),c.getCreated_on(),c.getAge(),c.getBirthdate(),c.getBusiness_name(),c.getCitizenship(),
		//	c.getCustomer_type(),c.getEmail(),c.getEntity_type(),c.getFirst_name(),c.getLast_name(),c.getNationality(),
			//c.getTel1(),c.getTel2(),c.getTin(),c.getNid(),c.getTable_initiale(),c.getNot_Edited_in());
	this.toEdit=toEdit;
}
public boolean getToEdit()
{
	return this.toEdit;
}
}
