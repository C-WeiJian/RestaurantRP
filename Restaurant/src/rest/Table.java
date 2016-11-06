package rest;

import java.io.Serializable;

@SuppressWarnings("serial")

public class Table implements Serializable {

	private int tableId;
//	private int pax;
	private int capacity;	//capacities of 2, 4, 6, 8, 10
	private boolean status; //true means available
	private int orderId;
	
	public Table(int tableId, int capacity, boolean status) {
		this.tableId = tableId;
		this.capacity = capacity;
		this.status = status;
		this.orderId = -1;
	}
	
	public int getTableId() {
		return tableId;
	}
	public void setTableId(int tableId) {
		this.tableId = tableId;
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
	
	public int getorderId() {
		return orderId;
	}
	
	public void setorderId(int orderId){
		this.orderId = orderId;
	}
	
//	public Order getOrder() {
//		return order;
//	}
//	
//	public void setOrder(Order order) {
//		this.order = order;
//	}
//
//	public int getPax() {
//		return pax;
//	}
//
//	public void setPax(int pax) {
//		this.pax = pax;
//	}
	
}
