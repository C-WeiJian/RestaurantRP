package rest;

public class Staff {
Staff(int employeeId, String name, String jobTitle, char gender){
	this.setGender(gender);
	this.setJobTitle(jobTitle);
	this.setName(name);
	this.setEmployeeId(employeeId);
}
private String name, jobTitle;
private char gender;
private int employeeId;
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
