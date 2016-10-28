package rest;

import java.util.List;
import java.util.Map;

public class OrderLineItem {
	private Map<MenuItem, Integer> order;
	
	public void addMenuItem(MenuItem item, int quantity) {
		order.put(item, quantity);
	}
	
	public void removeMenuItem(MenuItem item, int quantity) {
		int initial = order.get(item);
		int newquantity = initial - quantity;
		order.put(item, newquantity);
	}
	
	public Map<MenuItem, Integer> getOrder() {
		return this.order;
	}
}
