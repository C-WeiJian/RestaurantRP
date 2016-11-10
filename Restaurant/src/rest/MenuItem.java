package rest;

import java.io.Serializable;


/**
 * Represents an Ala Carte item (MenuItem) in the menu
 * @version 1.0
 * @since 2016-10-28
 */
@SuppressWarnings("serial")
public class MenuItem implements Serializable{
	
	/** The full name of the MenuItem. */
	private String name;
	
	/** The description of the MenuItem. */
	private String description;
	
	/** The price of the MenuItem. */
	private double price;
	
	/** The type of the MenuItem:  
	 * Main (M), Beverage (B), Desert (D). 
	 */
	private String type;
	
	/** Checks if the MenuItem is a subtype, PromoPackage 
	 * 	Set as false
	 */
	private boolean isPromo;
	
	/** The id of the MenuItem. */
	private String id;
	
	/**
	 * Instantiates a new menu item.
	 *
	 * @param name This MenuItem's name
	 * @param description This MenuItem's description
	 * @param price This MenuItem's price
	 * @param type This MenuItem's type
	 * @param isPromo This object's instance type
	 * @param id This MenuItem's id
	 */
	public MenuItem(String name, String description, double price, String type, boolean isPromo, String id) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.type = type;
		this.isPromo = isPromo;
		this.id = id;
	}
	
	/**
	 * Gets the full name of this MenuItem.
	 *
	 * @return this MenuItem's full name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the full name of this MenuItem.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the description of this MenuItem.
	 *
	 * @return this MenuItem's description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets the description of this MenuItem.
	 *
	 * @param description the description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Gets the price of this MenuItem.
	 *
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}
	
	/**
	 * Sets the price of this MenuItem
	 *
	 * @param price the new price
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	
	/**
	 * Gets the type of this MenuItem.
	 *
	 * @return this MenuItem's type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Sets the type if this MenuItem
	 * Should be a capitalised letter:
	 * M (Main), B (Beverage), or D (Desert)
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Checks if the object is an instance of PromoPackage.
	 *
	 * @return true, if is a PromoPackage
	 */
	public boolean isPromo() {
		return isPromo;
	}
	
	/**
	 * Sets the status of the object.
	 * If Object is an instance of PromoPackage,
	 * set as true. 
	 *
	 * @param isPromo the new promo
	 */
	public void setPromo(boolean isPromo) {
		this.isPromo = isPromo;
	}
	
	/**
	 * Gets the id of this MenuItem.
	 *
	 * @return this MenuItem's id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets this Menuitem's ID.
	 * Should be two digits
	 * i.e. 
	 * if ID of the MenuItem is one, input 01
	 * if ID of the MenuItem is 21, input 21
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
}
