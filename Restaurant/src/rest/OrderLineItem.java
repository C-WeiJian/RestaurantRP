package rest;

import java.io.Serializable;

/**
 * The Class OrderLineItem.
 * To calculate the total quantity of a MenuItem in a single order
 * @version 1.0
 * @since 2016-10-28
 */
@SuppressWarnings("serial")

public class OrderLineItem implements Serializable{
	
	/** The item. */
	private MenuItem item;
	
	/** The total quantity of the MenuItem. */
	private int quantity;
	
	/**
	 * Instantiates a new order line item.
	 *
	 * @param item the item that is ordered
	 * @param quantity the quantity
	 */
	public OrderLineItem(MenuItem item, int quantity) {
		this.item = item;
		this.quantity = quantity;
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
	 * Gets the total quantity of the MenuItem.
	 *
	 * @return the quantity
	 */
	public int getQuantity() {
		return this.quantity;
	}
	
	/**
	 * Sets the quantity of the MenuItem.
	 *
	 * @param quantity the new quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	
}
