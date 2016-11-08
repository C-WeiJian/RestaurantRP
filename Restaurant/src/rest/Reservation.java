package rest;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SuppressWarnings("serial")

public class Reservation implements Serializable {
	private long custContact;
	private int resPax;
	private int tableId;
	private boolean AM = false; //PM = false;
	private String dateTime;
	//private LocalDateTime dateTime;
	//private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); //format of time //remove this
	
	public Reservation(int resPax, String dateTime, boolean AM, int tableId, long custContact) {
		this.resPax = resPax;
		this.setDateTime(dateTime);
		this.AM = AM;
		this.tableId = tableId;
		this.custContact = custContact;
	}

	public long getCustContact() {
		return custContact;
	}
	
	public void setCustContact(long custContact) {
		this.custContact = custContact;
	}
	
	public int getPax() {
		return resPax;
	}
	
	public void setPax(int pax) {
		this.resPax = pax;
	}
	
	public int getTableId() {
		return tableId;
	}
	
	public void setTableId(int table) {
		this.tableId = table;
	}

	public boolean getAM() {
		return AM;
	}
	
	public void setAM(boolean aM) {
		AM = aM;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
}
