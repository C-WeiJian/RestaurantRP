package rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderLineItem {
	//Hashmap with menu item and quantity
	private HashMap<MenuItem, Integer> order;
	
	public OrderLineItem() {
		order = new HashMap<MenuItem, Integer>();
	}
	
	public void addMenuItem(MenuItem item, int quantity) {
		order.put(item, quantity);
	}
	
	public void removeMenuItem(MenuItem item, int quantity) {
		int initial = order.get(item);
		int newquantity = initial - quantity;
		order.put(item, newquantity);
	}
	
	public HashMap<MenuItem, Integer> getOrder() {
		return this.order;
	}
}
