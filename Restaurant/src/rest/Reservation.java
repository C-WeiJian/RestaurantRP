package rest;

import java.io.Serializable;

/**
 * The Class Reservation.
 * @version 1.0
 * @since 2016-10-28
 */
@SuppressWarnings("serial")

public class Reservation implements Serializable {
	
	/** The contact number of the customer. */
	private long custContact;
	
	/** The number of people reserved for. */
	private int resPax;
	
	/** The table id. */
	private int tableId;
	
	/** True for AM session and False for PM session. */
	private boolean AM = false; //PM = false;
	
	/** The date and time. */
	private String dateTime;
	
	/**
	 * Instantiates a new reservation.
	 *
	 * @param resPax the number of people reserved for
	 * @param dateTime the date time
	 * @param AM the status of whether the reservation is AM or PM session
	 * @param tableId the table id
	 * @param custContact the customer contact
	 */
	public Reservation(int resPax, String dateTime, boolean AM, int tableId, long custContact) {
		this.resPax = resPax;
		this.setDateTime(dateTime);
		this.AM = AM;
		this.tableId = tableId;
		this.custContact = custContact;
	}

	/**
	 * Gets the customer's contact.
	 *
	 * @return the customer's contact
	 */
	public long getCustContact() {
		return custContact;
	}
	
	/**
	 * Sets the customer's contact.
	 *
	 * @param custContact the new customer's contact
	 */
	public void setCustContact(long custContact) {
		this.custContact = custContact;
	}
	
	/**
	 * Gets the pax.
	 *
	 * @return the pax
	 */
	public int getPax() {
		return resPax;
	}
	
	/**
	 * Sets the pax.
	 *
	 * @param pax the new pax
	 */
	public void setPax(int pax) {
		this.resPax = pax;
	}
	
	/**
	 * Gets the table id.
	 *
	 * @return the table id
	 */
	public int getTableId() {
		return tableId;
	}
	
	/**
	 * Sets the table id.
	 *
	 * @param table the new table id
	 */
	public void setTableId(int table) {
		this.tableId = table;
	}

	/**
	 * Gets the session that the reservation is booked for.
	 * Session can either be AM or PM
	 * @return the am
	 */
	public boolean getAM() {
		return AM;
	}
	
	/**
	 * Sets the session that the reservation is booked for.
	 * Session can either be AM or PM
	 * @param aM the new am
	 */
	public void setAM(boolean aM) {
		AM = aM;
	}

	/**
	 * Gets the date time.
	 *
	 * @return the date time
	 */
	public String getDateTime() {
		return dateTime;
	}

	/**
	 * Sets the date time.
	 *
	 * @param dateTime the new date time
	 */
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
}
