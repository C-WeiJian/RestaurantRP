package rest;

import java.io.Serializable;
import java.time.LocalDateTime;

@SuppressWarnings("serial")

public class SalesLineItem implements Serializable{
	
	private MenuItem item;
	private int quantity;
	private LocalDateTime dateTime;
	private double totalPrice;
		
	public SalesLineItem(MenuItem item, int quantity, LocalDateTime dateTime) {
		this.item = item;
		this.quantity = quantity;
		this.dateTime = dateTime;
		this.totalPrice = 0;
	}
	
	public double getTotalPrice() {
		totalPrice = item.getPrice()*quantity;
		return totalPrice;
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

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
}
