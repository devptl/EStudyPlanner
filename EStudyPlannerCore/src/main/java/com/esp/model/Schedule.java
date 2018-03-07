package com.esp.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Schedule {

	@Id
	private String studentsUserName;
	private String slotOneIn;
	private String slotOneOut;
	private String slotTwoIn;
	private String slotTwoOut;
	private String slotThreeIn;
	private String slotThreeOut;

	public String getStudentsUserName() {
		return studentsUserName;
	}

	public String getSlotOneIn() {
		return slotOneIn;
	}

	public String getSlotOneOut() {
		return slotOneOut;
	}

	public String getSlotTwoIn() {
		return slotTwoIn;
	}

	public String getSlotTwoOut() {
		return slotTwoOut;
	}

	public String getSlotThreeIn() {
		return slotThreeIn;
	}

	public String getSlotThreeOut() {
		return slotThreeOut;
	}

	public void setStudentsUserName(String studentsUserName) {
		this.studentsUserName = studentsUserName;
	}

	public void setSlotOneIn(String slotOneIn) {
		this.slotOneIn = slotOneIn;
	}

	public void setSlotOneOut(String slotOneOut) {
		this.slotOneOut = slotOneOut;
	}

	public void setSlotTwoIn(String slotTwoIn) {
		this.slotTwoIn = slotTwoIn;
	}

	public void setSlotTwoOut(String slotTwoOut) {
		this.slotTwoOut = slotTwoOut;
	}

	public void setSlotThreeIn(String slotThreeIn) {
		this.slotThreeIn = slotThreeIn;
	}

	public void setSlotThreeOut(String slotThreeOut) {
		this.slotThreeOut = slotThreeOut;
	}

	public Schedule() {

	}

	public Schedule(String studentsUserName, String slotOneIn, String slotOneOut, String slotTwoIn, String slotTwoOut,
			String slotThreeIn, String slotThreeOut) {
		
		this.studentsUserName = studentsUserName;
		this.slotOneIn = slotOneIn;
		this.slotOneOut = slotOneOut;
		this.slotTwoIn = slotTwoIn;
		this.slotTwoOut = slotTwoOut;
		this.slotThreeIn = slotThreeIn;
		this.slotThreeOut = slotThreeOut;
	}

}
