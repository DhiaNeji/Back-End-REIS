package Reis.PFE.Entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="data_collection")
public class DataCollection {
@Id
private int id;
@Column(name = "date", columnDefinition = "TIMESTAMP")
private LocalDateTime date;
public DataCollection(int id, LocalDateTime date) {
	super();
	this.id = id;
	this.date = date;
} 
public DataCollection()
{
	
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public LocalDateTime getDate() {
	return date;
}
public void setDate(LocalDateTime date) {
	this.date = date;
}

}
