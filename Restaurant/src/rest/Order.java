package rest;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.io.Serializable;


/**
 * Represents the Order made by customers
 *
 * @version 1.0
 * @since 2016-10-28
 */
@SuppressWarnings("serial")
public class Order implements Serializable{

	/** The date and time the order is made. */
	private LocalDateTime dateTime;
	
	/** The list of orders made by customers. */
	private List<OrderLineItem> orders;
	
	/** The subtotal price of the customer's orders. */
	private double subtotal;
	
	/** The service charge. */
	private double svcharge;
	
	/** The Good Service Tax (gst). */
	private double gst;
	
	/** The total price charged. */
	private double total;
	
	/** The order id. */
	private int orderId;
	
	/** The table id. */
	private int tableId;
	
	/** The staff who served the customer . */
	private Staff staff;

	
	/**
	 * Instantiates a new order.
	 *
	 * @param orderId this Order's order id
	 * @param staff the Staff who took the order
	 * @param tableId this Order's table id
	 */
	public Order(int orderId, Staff staff, int tableId) {
		this.orders = new ArrayList<OrderLineItem>();
		this.dateTime = LocalDateTime.now();
		this.orderId = orderId;
		this.staff = staff;
		this.tableId = tableId;
	}
	
	/**
	 * Instantiates a new order for loading in preset orders quickly
	 * for test cases required by assignment.
	 *
	 * @param orderId the order id
	 * @param staff the Staff object
	 * @param tableId the table id
	 * @param dateTime the date time
	 */
	public Order(int orderId, Staff staff, int tableId, LocalDateTime dateTime) {
		this.orders = new ArrayList<OrderLineItem>();
		this.dateTime = dateTime;
		this.orderId = orderId;
		this.staff = staff;
		this.tableId = tableId;
	}
	
	/**
	 * Calculate the bill.
	 *
	 * @return the double
	 */
	private double calcBill() {
		double bill = 0;	
		for (OrderLineItem i : orders) {
			bill += i.getMenuItem().getPrice() * i.getQuantity();
		}
		return bill;
	}

	/**
	 * Adds a new menu item to the order.
	 *
	 * @param item the item to be added
	 * @param quantity the quantity
	 */
	public void addMenuItem(MenuItem item, int quantity) {
		OrderLineItem n = checkItem(item);
		if (n != null) {
			int initial = n.getQuantity();
			int newquantity = quantity + initial;
			n.setQuantity(newquantity);
			System.out.println(quantity + " " + item.getName() +" added!");
		}
		else {
			orders.add(new OrderLineItem(item, quantity));
		}
	}
	
	/**
	 * Removes the menu item from the order.
	 *
	 * @param item the item
	 * @param quantity the quantity of items
	 */
	public void removeMenuItem(MenuItem item, int quantity) {
		OrderLineItem n = checkItem(item);
		if (n != null) {
			int initial = n.getQuantity();
			int newquantity = initial - quantity;
			if (newquantity > 0) {
				n.setQuantity(newquantity);
				System.out.println(quantity + " " + item.getName() +" removed!");
			}
			else {
				orders.remove(n);
				System.out.println("All " + item.getName() + " items removed!");
			}
		}
		else {
			System.out.println("No changes made (Error: Item was not ordered at all).");
		}
	}
	
	/**
	 * Check if a MenuItem is in the order.
	 *
	 * @param item the MenuItem to be checked
	 * @return the OrderLineItem if it is in the order
	 */
	public OrderLineItem checkItem(MenuItem item) {
		for (OrderLineItem n : orders) {
			if (n.getMenuItem().getName().equals(item.getName())) return n;	
		}
		return null;
	}

	/**
	 * Gets the order id.
	 *
	 * @return the order id
	 */
	public int getOrderId() {
		return orderId;
	}

	/**
	 * Sets the order id.
	 *
	 * @param orderId the new order id
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
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
	 * Gets the staff.
	 *
	 * @return the staff
	 */
	public Staff getStaff() {
		return staff;
	}

	/**
	 * Sets the staff.
	 *
	 * @param staff the new staff
	 */
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	
	/**
	 * Gets the orders.
	 *
	 * @return the orders
	 */
	public List<OrderLineItem> getOrders() {
		return orders;
	}
	
	/**
	 * Gets the date time.
	 *
	 * @return the date time
	 */
	public LocalDateTime getdateTime() {
		return dateTime;
	}
	
	/**
	 * Sets the date time.
	 *
	 * @param dateTime the new date time
	 */
	public void setdateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	
	/**
	 * Gets the year.
	 *
	 * @return the year
	 */
	public int getYear(){
		return dateTime.getYear();
	}
	
	/**
	 * Gets the month.
	 *
	 * @return the month
	 */
	public int getMonth(){
		return dateTime.getMonthValue();
	}

	/**
	 * Gets the subtotal.
	 *
	 * @return the subtotal
	 */
	public double getSubtotal() {
		this.subtotal = calcBill();
		return subtotal;
	}

	/**
	 * Sets the subtotal.
	 *
	 * @param subtotal the new subtotal
	 */
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	/**
	 * Gets the service charge.
	 *
	 * @return the service charge
	 */
	public double getSvcharge() {
		svcharge = subtotal*0.1;
		return svcharge;
	}

	/**
	 * Sets the service charge.
	 *
	 * @param svcharge the new service charge
	 */
	public void setSvcharge(double svcharge) {
		this.svcharge = svcharge;
	}

	/**
	 * Gets the gst.
	 *
	 * @return the gst
	 */
	public double getGst() {
		gst = (subtotal + svcharge)*0.07;
		return gst;
	}

	/**
	 * Sets the gst.
	 *
	 * @param gst the new gst
	 */
	public void setGst(double gst) {
		this.gst = gst;
	}

	/**
	 * Gets the total.
	 *
	 * @return the total
	 */
	public double getTotal() {
		total = subtotal + svcharge + gst;
		return total;
	}

	/**
	 * Sets the total.
	 *
	 * @param total the new total
	 */
	public void setTotal(double total) {
		this.total = total;
	}
	
}
