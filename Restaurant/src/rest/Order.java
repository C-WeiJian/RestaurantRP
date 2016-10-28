package rest;

import java.util.List;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Iterator;

public class Order {
	private LocalDateTime dateTime;
	private OrderLineItem orders;
	private double totalPrice;
	private int orderId;
	private int tableId;
	private Staff staff;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); //format of time
	
	public Order() {
		this.orders = new OrderLineItem();
		this.dateTime = LocalDateTime.now();
	}
	
	public double callBill() {
		double bill = 0;
		
		for (Entry<MenuItem, Integer> entry : orders.getOrder().entrySet()) {
			bill += entry.getKey().getPrice() * entry.getValue();
		}
		
		return bill;
	}

	public void addMenuItem(Menu menu) {
		Scanner in = new Scanner(System.in);
		System.out.print("Enter item ID: ");
		String itemId = in.next();
		System.out.print("Enter quantity: ");
		int qty = in.nextInt();
		MenuItem item = menu.getMenuItem(itemId);
		this.orders.addMenuItem(item, qty);
		System.out.println(item +" added!");
	}
	
	public void removeMenuItem(MenuItem item, int quantity) {
		this.orders.removeMenuItem(item, quantity);
		System.out.println(item +" removed!");
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

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	
	public OrderLineItem getOrders() {
		return orders;
	}
	
	public String getdateTime() {
		return dateTime.format(formatter);
	}
	public void setdateTime(String time) {
		this.dateTime = LocalDateTime.parse(time, formatter);
	}
	
	public int getYear(){
		return dateTime.getYear();
	}
	
	public int getMonth(){
		return dateTime.getMonthValue();
	}
}
