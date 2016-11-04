package rest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reservation {
	private long custContact;
	private int resPax;
	private int tableNo;
	private boolean AM = false, PM = false;
	private LocalDateTime dateTime;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); //format of time
	
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
	public int getTable() {
		return tableNo;
	}
	public void setTable(int table) {
		this.tableNo = table;
	}
	public String getdateTime() {
		return dateTime.format(formatter);
	}
	
	public void setdateTime(String time) {
		this.dateTime = LocalDateTime.parse(time, formatter);
	}
	
	public int getHour(){
		return dateTime.getHour();
	}
	
	public int getMin(){
		return dateTime.getMinute();
	}
	public boolean getAM() {
		return AM;
	}
	public void setAM(boolean aM) {
		AM = aM;
	}
	public boolean getPM() {
		return PM;
	}
	public void setPM(boolean pM) {
		PM = pM;
	}
}
