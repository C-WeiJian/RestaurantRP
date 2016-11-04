package rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderLineItem {
	
	private MenuItem item;
	private int quantity;
	
	public OrderLineItem(MenuItem item, int quantity) {
		this.item = item;
		this.quantity = quantity;
	}
	
	public MenuItem getMenuItem() {
		return this.item;
	}
	
	public void setMenuItem(MenuItem item) {
		this.item = item;
	}
	
	public int getQuantity() {
		return this.quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	//Hashmap with menu item and quantity
//	private HashMap<MenuItem, Integer> orders;
//	
//	public OrderLineItem() {
//		orders = new HashMap<MenuItem, Integer>();
//	}
//	
//	public void addMenuItem(MenuItem item, int quantity) {
//		int initial;
//		if (orders.containsKey(item)) {
//			initial = orders.get(item);
//		}
//		else initial = 0;
//		int newquantity = initial + quantity;
//		orders.put(item, newquantity);
//	}
//	
//	public void removeMenuItem(MenuItem item, int quantity) {
//		int initial = orders.get(item);
//		int newquantity = initial - quantity;
//		if (newquantity < 0) newquantity = 0;
//		orders.put(item, newquantity);
//	}
//	
//	public HashMap<MenuItem, Integer> getOrder() {
//		return this.orders;
//	}
	
}
