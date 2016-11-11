package rest;

import java.io.Serializable;

/**
 * Represents a staff member of REstaurant
 * @version 1.0
 * @since 2016-10-28
 */
@SuppressWarnings("serial")

public class Staff implements Serializable {

	/** The job title of the staff. */
	private String name, jobTitle;
	
	/** The gender of the staff. */
	private char gender;
	
	/** The staff id of the staff. */
	private int staffId;

	/**
	 * Instantiates a new staff.
	 *
	 * @param staffId this Staff's id
	 * @param name this Staff's full name
	 * @param jobTitle this Staff's job title
	 * @param gender this Staff's gender
	 */
	public Staff(int staffId, String name, String jobTitle, char gender) {
		this.gender = gender;
		this.jobTitle = jobTitle;
		this.name = name;
		this.staffId = staffId;
	}

	/**
	 * Gets the full name of this Staff.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the full name of this staff.
	 *
	 * @param name this Staff's new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets this Staff's job title.
	 *
	 * @return the job title
	 */
	public String getJobTitle() {
		return jobTitle;
	}

	/**
	 * Sets this Staff's job title.
	 *
	 * @param jobTitle this Staff's new job title
	 */
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	/**
	 * Gets this Staff's gender.
	 *
	 * @return the gender
	 */
	public char getGender() {
		return gender;
	}

	/**
	 * Sets this Staff's gender.
	 *
	 * @param gender this Staff's new gender
	 */
	public void setGender(char gender) {
		this.gender = gender;
	}

	/**
	 * Gets This Staff's id.
	 *
	 * @return the staff id
	 */
	public int getStaffId() {
		return staffId;
	}

	/**
	 * Sets this Staff's id.
	 *
	 * @param staffId this Staff's new staff id
	 */
	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}
}
