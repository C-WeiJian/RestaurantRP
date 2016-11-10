package rest;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * Represents a Table
 */
@SuppressWarnings("serial")

public class Table implements Serializable {

	/** The table id. */
	private int tableId;
	
	/** The capacity. */
	private int capacity;	//capacities of 2, 4, 8, 10
	
	/** The status.
	 * 1  - Available
	 * 0  - Reserved
	 * -1 - Occupied
	 *  */
	private int status; //1 - available/vacant, 0 - reserved, -1 - occupied
	
	/** The order id. */
	private int orderId;
	
	/**
	 * Instantiates a new table.
	 *
	 * @param tableId the table id
	 * @param capacity the capacity
	 * @param status the status
	 */
	public Table(int tableId, int capacity, int status) {
		this.tableId = tableId;
		this.capacity = capacity;
		this.status = status;
		this.orderId = -1;
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
	 * @param tableId the new table id
	 */
	public void setTableId(int tableId) {
		this.tableId = tableId;
	}
	
	/**
	 * Gets the capacity.
	 *
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}
	
	/**
	 * Sets the capacity.
	 *
	 * @param capacity the new capacity
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	
	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	
	/**
	 * Gets the order id.
	 *
	 * @return the order id
	 */
	public int getorderId() {
		return orderId;
	}
	
	/**
	 * Sets the order id.
	 *
	 * @param orderId the new order id
	 */
	public void setorderId(int orderId){
		this.orderId = orderId;
	}
	
}
