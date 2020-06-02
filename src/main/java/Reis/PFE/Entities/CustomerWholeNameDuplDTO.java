package Reis.PFE.Entities;

public class CustomerWholeNameDuplDTO {
	private int id;
	private int idcluster;
	private String whole_name;
	private String first_name;
	private String last_name;
	private String score;
	private String nid;
	private String birth_date;
	private boolean to_delete;
	private String current_step;
	private String step_16;
	public CustomerWholeNameDuplDTO(int id, int idcluster, String whole_name, String score, String nid,
			String birth_date, boolean to_delete, String current_step, String step_16) {
		super();
		this.id = id;
		this.idcluster = idcluster;
		this.whole_name = whole_name;
		this.score = score;
		this.nid = nid;
		this.birth_date = birth_date;
		this.to_delete = to_delete;
		this.current_step = current_step;
		this.step_16 = step_16;
	}
	public CustomerWholeNameDuplDTO() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdcluster() {
		return idcluster;
	}
	public void setIdcluster(int idcluster) {
		this.idcluster = idcluster;
	}
	public String getWhole_name() {
		return whole_name;
	}
	public void setWhole_name(String whole_name) {
		this.whole_name = whole_name;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getNid() {
		return nid;
	}
	public void setNid(String nid) {
		this.nid = nid;
	}
	public String getBirth_date() {
		return birth_date;
	}
	public void setBirth_date(String birth_date) {
		this.birth_date = birth_date;
	}
	public boolean isTo_delete() {
		return to_delete;
	}
	public void setTo_delete(boolean to_delete) {
		this.to_delete = to_delete;
	}
	public String getCurrent_step() {
		return current_step;
	}
	public void setCurrent_step(String current_step) {
		this.current_step = current_step;
	}
	public String getStep_16() {
		return step_16;
	}
	public void setStep_16(String step_16) {
		this.step_16 = step_16;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	
}
