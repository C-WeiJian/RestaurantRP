package rest;

import java.io.Serializable;

@SuppressWarnings("serial")

public class OrderLineItem implements Serializable{
	
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

	
}
