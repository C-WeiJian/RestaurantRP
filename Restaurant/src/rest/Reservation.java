package rest;

public class Reservation {
	private long custContact;
	private int pax;
	private int table;
	private String[] session;
	
	public long getCustContact() {
		return custContact;
	}
	public void setCustContact(long custContact) {
		this.custContact = custContact;
	}
	public int getPax() {
		return pax;
	}
	public void setPax(int pax) {
		this.pax = pax;
	}
	public int getTable() {
		return table;
	}
	public void setTable(int table) {
		this.table = table;
	}
	public String[] getSession() {
		return session;
	}
	public void setSession(String[] session) {
		this.session = session;
	}
	
}
