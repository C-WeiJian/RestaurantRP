package rest;

import java.io.Serializable;

@SuppressWarnings("serial")

public class Staff implements Serializable {

	private String name, jobTitle;
	private char gender;
	private int staffId;

	public Staff(int staffId, String name, String jobTitle, char gender) {
		this.gender = gender;
		this.jobTitle = jobTitle;
		this.name = name;
		this.staffId = staffId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public int getStaffId() {
		return staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}
}
