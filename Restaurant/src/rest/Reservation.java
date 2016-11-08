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
	private LocalDateTime dateTime;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); //format of time
	
	public Reservation(int resPax, LocalDateTime dateTime, boolean AM, int tableId, long custContact) {
		this.resPax = resPax;
		this.dateTime = dateTime;
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
	public int getTable() {
		return tableId;
	}
	public void setTable(int table) {
		this.tableId = table;
	}
	public String getDateTime() {
		return dateTime.format(formatter);
	}
	public LocalDateTime getLocalDateTime(){
		return dateTime;
	}
	public void setDateTime(String time) {
		this.dateTime = LocalDateTime.parse(time, formatter);
	}
	public int getMonth(){
		return dateTime.getMonthValue();
	}
	public int getDay(){
		return dateTime.getDayOfMonth();
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
//	public boolean getPM() {
//		return PM;
//	}
//	public void setPM(boolean pM) {
//		PM = pM;
//	}
//	public boolean amSession(LocalDateTime bookingTime){
//		LocalDateTime amOpen = LocalDateTime.of(bookingTime.getYear(), bookingTime.getMonthValue(), bookingTime.getDayOfMonth(), 11, 00);
//		LocalDateTime amClose = LocalDateTime.of(bookingTime.getYear(), bookingTime.getMonthValue(), bookingTime.getDayOfMonth(), 15, 00);
//		if (bookingTime.isAfter(amOpen) && bookingTime.isBefore(amClose))
//			return true;
//		else 
//			return false;
//	}
//	public boolean pmSession(LocalDateTime bookingTime){
//	LocalDateTime pmOpen = LocalDateTime.of(bookingTime.getYear(), bookingTime.getMonthValue(), bookingTime.getDayOfMonth(), 18, 00);
//	LocalDateTime pmClose = LocalDateTime.of(bookingTime.getYear(), bookingTime.getMonthValue(), bookingTime.getDayOfMonth(), 22, 00);
//	if (bookingTime.isAfter(pmOpen) && bookingTime.isBefore(pmClose))
//		return true;
//	else 
//		return false;
//	}
}
