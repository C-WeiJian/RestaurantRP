package rest;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents the MenuItems served and paid for in REstaurant
 * @version 1.0
 * @since 2016-10-28
 */
@SuppressWarnings("serial")

public class SalesLineItem implements Serializable{
	
	/** This SalesLineItem's MenuItem. */
	private MenuItem item;
	
	/** The quantity of the MenuItem served and paid for. */
	private int quantity;
	
	/** The date and time of the sale. */
	private LocalDateTime dateTime;
	
	/** The total earnings from sale of this SalesLineItem's MenuItem. */
	private double totalPrice;
		
	/**
	 * Instantiates a new sales line item.
	 *
	 * @param item This SalesLineItem's MenuItem
	 * @param quantity This SalesLineItem's quantity
	 * @param dateTime This SalesLineItem's date and time
	 */
	public SalesLineItem(MenuItem item, int quantity, LocalDateTime dateTime) {
		this.item = item;
		this.quantity = quantity;
		this.dateTime = dateTime;
		this.totalPrice = 0;
	}
	
	/**
	 * Gets the total earnings of the MenuItem.
	 *
	 * @return the total price
	 */
	public double getTotalPrice() {
		totalPrice = item.getPrice()*quantity;
		return totalPrice;
	}

	/**
	 * Gets the MenuItem.
	 *
	 * @return the menu item
	 */
	public MenuItem getMenuItem() {
		return this.item;
	}
	
	/**
	 * Sets the MenuItem.
	 *
	 * @param item the new menu item
	 */
	public void setMenuItem(MenuItem item) {
		this.item = item;
	}
	
	/**
	 * Gets the MenuItem's sales quantity.
	 *
	 * @return the quantity
	 */
	public int getQuantity() {
		return this.quantity;
	}
	
	/**
	 * Sets the MenuItem's sales quantity.
	 *
	 * @param quantity the new quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * Gets the date and time of the sale of the MenuItem.
	 *
	 * @return the date time
	 */
	public LocalDateTime getDateTime() {
		return dateTime;
	}

	/**
	 * Sets the date and time of the sale of the MenuItem.
	 *
	 * @param dateTime the new date time
	 */
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
}
