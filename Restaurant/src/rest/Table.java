package rest;

public class Table {
	private int tableID;
	private int capacity;
	private boolean status; //true means available
	private Order order;
	
	public Table(int tableID, int capacity, boolean status) {
		this.tableID = tableID;
		this.capacity = capacity;
		this.status = status;
	}
	
	public int getTableID() {
		return tableID;
	}
	public void setTableID(int tableID) {
		this.tableID = tableID;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	
}
