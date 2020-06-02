package Reis.PFE.Entities;

import java.util.List;

public class Reporting {
private long data_collection_count;
private long customers_count;
private long err_data_count;
private long edited_data_count;
private long ignored_data_count;
private List<StepDTO> last_cleaning_progress;
private List<StepDTO> last_cleaning_err_data;
private List<StepDTO> last_cleaning_edited_ignored_data;
public Reporting()
{
	
}
public long getData_collection_count() {
	return data_collection_count;
}
public void setData_collection_count(long data_collection_count) {
	this.data_collection_count = data_collection_count;
}
public long getCustomers_count() {
	return customers_count;
}
public void setCustomers_count(long customers_count) {
	this.customers_count = customers_count;
}
public long getEdited_data_count() {
	return edited_data_count;
}
public void setEdited_data_count(long edited_data_count) {
	this.edited_data_count = edited_data_count;
}
public long getIgnored_data_count() {
	return ignored_data_count;
}
public void setIgnored_data_count(long ignored_data_count) {
	this.ignored_data_count = ignored_data_count;
}
public long getErr_data_count() {
	return err_data_count;
}
public void setErr_data_count(long err_data_count) {
	this.err_data_count = err_data_count;
}
public List<StepDTO> getLast_cleaning_progress() {
	return last_cleaning_progress;
}
public void setLast_cleaning_progress(List<StepDTO> last_cleaning_progress) {
	this.last_cleaning_progress = last_cleaning_progress;
}
public List<StepDTO> getLast_cleaning_err_data() {
	return last_cleaning_err_data;
}
public void setLast_cleaning_err_data(List<StepDTO> last_cleaning_err_data) {
	this.last_cleaning_err_data = last_cleaning_err_data;
}
public List<StepDTO> getLast_cleaning_edited_ignored_data() {
	return last_cleaning_edited_ignored_data;
}
public void setLast_cleaning_edited_ignored_data(List<StepDTO> last_cleaning_edited_ignored_data) {
	this.last_cleaning_edited_ignored_data = last_cleaning_edited_ignored_data;
}

}
