package Reis.PFE.Entities;

import java.time.LocalDateTime;


public class StepDTO {
	private String id;
	 private LocalDateTime started_on;
	 private int data_length;
	 private int edited_data_length;
	 private int ignored_data_length;
	 private int edited_duplicates_length;
	 private int deleted_data_length;
	 private int duplicates_data_length;
	 private int rested_data_length;
	 private int data_collection_id;
	 public StepDTO()
	 {
		 
	 }
	public StepDTO(String id, LocalDateTime started_on, int data_length, int edited_data_length, int ignored_data_length,
			int edited_duplicates_length, int deleted_data_length, int duplicates_data_length,int data_collection_id) {
		super();
		this.id = id;
		this.started_on = started_on;
		this.data_length = data_length;
		this.edited_data_length = edited_data_length;
		this.ignored_data_length = ignored_data_length;
		this.edited_duplicates_length = edited_duplicates_length;
		this.deleted_data_length = deleted_data_length;
		this.duplicates_data_length = duplicates_data_length;
		this.rested_data_length=this.data_length-this.edited_data_length;
		this.data_collection_id=data_collection_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public LocalDateTime getStarted_on() {
		return started_on;
	}
	public void setStarted_on(LocalDateTime started_on) {
		this.started_on = started_on;
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
	public int getRested_data_length() {
		return rested_data_length;
	}
	public void setRested_data_length(int rested_data_length) {
		this.rested_data_length = rested_data_length;
	}
	public int getData_collection_id() {
		return data_collection_id;
	}
	public void setData_collection_id(int data_collection_id) {
		this.data_collection_id = data_collection_id;
	}
	 
}
