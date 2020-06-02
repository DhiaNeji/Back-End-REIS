package Reis.PFE.Entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customersclusters")
public class Cluster {



	public Cluster(int id, int idcluster, String whole_name, String score, String nid,String birth_date) {
		super();
		this.id = id;
		this.idcluster = idcluster;
		this.whole_name = whole_name;
		this.score = score;
		this.nid = nid;
		this.birth_date=birth_date;
	}


	public Cluster(int id, int idcluster, String whole_name, String nid) {
		super();
		this.id = id;
		this.idcluster = idcluster;
		this.whole_name = whole_name;
		this.nid = nid;
	}


	public Cluster() {
		super();
	}

	@Id

	@Column(name = "id")
	private int id;

	@Column(name = "idcluster")
	private int idcluster;

	@Column(name = "whole_name")
	private String whole_name;

	@Column(name = "score")
	private String score;
	@Column(name = "nid")
	private String nid;
	@Column(name="birth_date")
	private String birth_date;
	
	public String getBirth_date() {
		return birth_date;
	}


	public void setBirth_date(String birth_date) {
		this.birth_date = birth_date;
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


	public String getNid() {
		return nid;
	}


	public void setNid(String nid) {
		this.nid = nid;
	}


	public String getScore() {
		return score;
	}


	public void setScore(String score) {
		this.score = score;
	}


}
