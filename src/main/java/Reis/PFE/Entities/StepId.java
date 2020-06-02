package Reis.PFE.Entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import net.bytebuddy.implementation.EqualsMethod;
@Embeddable
public class StepId implements Serializable{

private int id;
private int data_collection_id;

public StepId(int id, int data_collection_id) {
	super();
	this.id = id;
	this.data_collection_id = data_collection_id;
}
public StepId()
{
	
}
@Override
public boolean equals(Object obj)
{
	if(obj instanceof StepId)
	{
		return this.id+this.data_collection_id==(((StepId) obj).id+((StepId) obj).data_collection_id);
	}
	else
		return false;
}
@Override
public int hashCode() {
return this.id;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getData_collection_id() {
	return data_collection_id;
}
public void setData_collection_id(int data_collection_id) {
	this.data_collection_id = data_collection_id;
}

}
