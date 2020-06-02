package Reis.PFE.Entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cleaning_history")
public class CleaningHistory {
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  @Column(columnDefinition = "serial")
	  private Long id;
	  @Column(name = "started_on", columnDefinition = "TIMESTAMP")
	  private LocalDateTime started_on;
	  private int step_1;
	  private int step_2;
	  private int step_3;
	  private int step_4;
	  private int step_5;
	  private int step_6;
	  private int step_7;
	  private int step_8;
	  private int step_9;
	  private int step_10;
	  public CleaningHistory()
	  {
		  
	  }
	public CleaningHistory(Long id, LocalDateTime started_on, int step_1, int step_2, int step_3, int step_4,
			int step_5, int step_6, int step_7, int step_8, int step_9, int step_10) {
		super();
		this.id = id;
		this.started_on = started_on;
		this.step_1 = step_1;
		this.step_2 = step_2;
		this.step_3 = step_3;
		this.step_4 = step_4;
		this.step_5 = step_5;
		this.step_6 = step_6;
		this.step_7 = step_7;
		this.step_8 = step_8;
		this.step_9 = step_9;
		this.step_10 = step_10;
	}
	public CleaningHistory(LocalDateTime started_on)
	{
		this.id=null;
		this.started_on=started_on;
		this.step_1 = 0;
		this.step_2 = 0;
		this.step_3 = 0;
		this.step_4 = 0;
		this.step_5 = 0;
		this.step_6 = 0;
		this.step_7 = 0;
		this.step_8 = 0;
		this.step_9 = 0;
		this.step_10 = 0;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDateTime getStarted_on() {
		return started_on;
	}
	public void setStarted_on(LocalDateTime started_on) {
		this.started_on = started_on;
	}
	public int getStep_1() {
		return step_1;
	}
	public void setStep_1(int step_1) {
		this.step_1 = step_1;
	}
	public int getStep_2() {
		return step_2;
	}
	public void setStep_2(int step_2) {
		this.step_2 = step_2;
	}
	public int getStep_3() {
		return step_3;
	}
	public void setStep_3(int step_3) {
		this.step_3 = step_3;
	}
	public int getStep_4() {
		return step_4;
	}
	public void setStep_4(int step_4) {
		this.step_4 = step_4;
	}
	public int getStep_5() {
		return step_5;
	}
	public void setStep_5(int step_5) {
		this.step_5 = step_5;
	}
	public int getStep_6() {
		return step_6;
	}
	public void setStep_6(int step_6) {
		this.step_6 = step_6;
	}
	public int getStep_7() {
		return step_7;
	}
	public void setStep_7(int step_7) {
		this.step_7 = step_7;
	}
	public int getStep_8() {
		return step_8;
	}
	public void setStep_8(int step_8) {
		this.step_8 = step_8;
	}
	public int getStep_9() {
		return step_9;
	}
	public void setStep_9(int step_9) {
		this.step_9 = step_9;
	}
	public int getStep_10() {
		return step_10;
	}
	public void setStep_10(int step_10) {
		this.step_10 = step_10;
	}
	  
}
