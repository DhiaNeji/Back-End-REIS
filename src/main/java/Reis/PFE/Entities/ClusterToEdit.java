package Reis.PFE.Entities;

public class ClusterToEdit extends Cluster{
private String step_9;
private boolean to_delete;
public ClusterToEdit(int id, int idcluster, String whole_name, String score, String nid,String birth_date,String step_9)
{
	super(id,idcluster,whole_name,score,nid,birth_date);
	this.step_9=step_9;
	this.to_delete=false;
}
public ClusterToEdit()
{
	
}
public String getStep_9() {
	return step_9;
}
public void setStep_9(String step_9) {
	this.step_9 = step_9;
}
public boolean isTo_delete() {
	return to_delete;
}
public void setTo_delete(boolean to_delete) {
	this.to_delete = to_delete;
}

}
