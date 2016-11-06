package rest;

import java.io.Serializable;

@SuppressWarnings("serial")

public class Staff implements Serializable {

	private String name, jobTitle;
	private char gender;
	private int employeeId;

	public Staff(int employeeId, String name, String jobTitle, char gender) {
		this.gender = gender;
		this.jobTitle = jobTitle;
		this.name = name;
		this.employeeId = employeeId;
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

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
}
