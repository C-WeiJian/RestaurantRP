package rest;

import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Calendar; //Use java.util.Calendar class if you need to extract year, month, day, hour, minute, and second, or manipulating these field (e.g., 7 days later, 3 weeks earlier).
import java.text.DateFormat; //Use java.text.DateFormat to format a Date (form Date to text) and parse a date string (from text to Date). 
import java.util.Date; //timestamp
import java.util.Iterator;

public class Order {
	private Date date;
	private OrderLineItem orders;
	private double totalPrice;
	private int orderId;
	private int tableId;
	
	public Order() {
		this.orders = new OrderLineItem();
	}
	
	public double callBill() {
		double bill = 0;
		
		for (Entry<MenuItem, Integer> entry : orders.getOrder().entrySet()) {
			bill += entry.getKey().getPrice() * entry.getValue();
		}
		
		return bill;
	}

	public void addMenuItem() {
		Scanner in = new Scanner(System.in);
		System.out.print("Enter item ID: ");
		String itemId = in.nextLine();
		System.out.print("Enter quantity: ");
		int qty = in.nextInt();
		MenuItem item = menu.getMenuItem(itemId);
		
		this.orders.addMenuItem(item, quantity);
		System.out.println(item +" added!");
	}
	
	public void removeMenuItem(MenuItem item, int quantity) {
		this.orders.removeMenuItem(item, quantity);
		System.out.println(item +" removed!");
	}
	
	public int searchList(int itemId) {
		Iterator<Order> ite = orderList.listIterator();
		Order n = ite.next();
		while (ite.hasNext() || n.getOrderId() != orderId) {
			n = ite.next();
		}
		return orderList.indexOf(n);
	}
	
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getTableId() {
		return tableId;
	}

	public void setTableId(int tableId) {
		this.tableId = tableId;
	}
	

	
//	public String getTime() {
//		return time;
//	}
//	public void setTime(String time) {
//		this.time = time;
//	}
}
