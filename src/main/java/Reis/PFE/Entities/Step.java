package Reis.PFE.Entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name="step")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Step {
	 @EmbeddedId
	 private StepId stepId; 
	 @Column(name = "started_on", columnDefinition = "TIMESTAMP")
	 private LocalDateTime started_on;
	 private int data_length;
	 private int edited_data_length;
	 private int ignored_data_length;
	 private int edited_duplicates_length;
	 private int deleted_data_length;
	 private int duplicates_data_length;
	 public Step()
	 {
		 
	 }

	public Step(StepId stepId, LocalDateTime started_on, int data_length, int edited_data_length,
			int ignored_data_length, int edited_duplicates_length, int deleted_data_length,
			int duplicates_data_length) {
		super();
		this.stepId = stepId;
		this.started_on = started_on;
		this.data_length = data_length;
		this.edited_data_length = edited_data_length;
		this.ignored_data_length = ignored_data_length;
		this.edited_duplicates_length = edited_duplicates_length;
		this.deleted_data_length = deleted_data_length;
		this.duplicates_data_length = duplicates_data_length;
	}

	public int getData_length() {
		return data_length;
	}
	public void setData_length(int data_length) {
		this.data_length = data_length;
	}
	public int getEdited_data_length() {
		return edited_data_length;
	}
	public void setEdited_data_length(int edited_data_length) {
		this.edited_data_length = edited_data_length;
	}
	public int getIgnored_data_length() {
		return ignored_data_length;
	}
	public void setIgnored_data_length(int ignored_data_length) {
		this.ignored_data_length = ignored_data_length;
	}
	public int getEdited_duplicates_length() {
		return edited_duplicates_length;
	}
	public void setEdited_duplicates_length(int edited_duplicates_length) {
		this.edited_duplicates_length = edited_duplicates_length;
	}
	public int getDeleted_data_length() {
		return deleted_data_length;
	}
	public void setDeleted_data_length(int deleted_data_length) {
		this.deleted_data_length = deleted_data_length;
	}
	public int getDuplicates_data_length() {
		return duplicates_data_length;
	}
	public void setDuplicates_data_length(int duplicates_data_length) {
		this.duplicates_data_length = duplicates_data_length;
	}

	public StepId getStepId() {
		return stepId;
	}

	public void setStepId(StepId stepId) {
		this.stepId = stepId;
	}

	public LocalDateTime getStarted_on() {
		return started_on;
	}

	public void setStarted_on(LocalDateTime started_on) {
		this.started_on = started_on;
	}
	 
}
