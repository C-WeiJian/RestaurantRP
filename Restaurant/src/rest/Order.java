package rest;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
//import java.time.Month;
import java.time.format.DateTimeFormatter;
//import java.util.Map.Entry;
//import java.util.Scanner;
import java.util.Iterator;
import java.io.Serializable;

@SuppressWarnings("serial")
public class Order implements Serializable{

	private LocalDateTime dateTime;
	private List<OrderLineItem> orders;
//	private double totalPrice;
	private int orderId;
//	private int tableId;
//	private Staff staff;

	
	public Order(int orderId) {
		this.orders = new ArrayList<OrderLineItem>();
		this.dateTime = LocalDateTime.now();
		this.orderId = orderId;
	}
	
	public double callBill() {
		double bill = 0;	
		for (OrderLineItem i : orders) {
			bill += i.getMenuItem().getPrice() * i.getQuantity();
		}
		return bill;
	}

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
	
	public OrderLineItem checkItem(MenuItem item) {
		Iterator<OrderLineItem> al = orders.listIterator();
		while (al.hasNext()) {
			OrderLineItem n = al.next();
			if (n.getMenuItem().getName().equals(item.getName())) return n;
			
		}
		return null;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

/*	public int getTableId() {
		return tableId;
	}

	public void setTableId(int tableId) {
		this.tableId = tableId;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}*/
	
	public List<OrderLineItem> getOrders() {
		return orders;
	}
	
	public LocalDateTime getdateTime() {
		return dateTime;
	}
	
	public void setdateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	
	public int getYear(){
		return dateTime.getYear();
	}
	
	public int getMonth(){
		return dateTime.getMonthValue();
	}
}
