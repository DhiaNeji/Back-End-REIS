package Reis.PFE.Entities;

import java.sql.Date;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cleaning_notification")
public class Notification {
	 @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  @Column(columnDefinition = "serial")
	  private Long id;
	 private String description;
	 @Column(name = "created_on", columnDefinition = "TIMESTAMP")
	 private LocalDateTime created_on;
	 public Notification() {};
	 public Notification(Long id, String description, LocalDateTime created_on) {
			super();
			this.id = id;
			this.description = description;
			this.created_on = created_on;
		}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDateTime getCreated_on() {
		return created_on;
	}
	public void setCreated_on(LocalDateTime created_on) {
		this.created_on = created_on;
	}
	 
}

