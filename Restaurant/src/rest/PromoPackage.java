package rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a PromoPackage item in the menu.
 */
@SuppressWarnings("serial")
public class PromoPackage extends MenuItem {
	
	/** The list of MenuItems. */
	private List<MenuItem> pItems = new ArrayList<MenuItem>();		// list of MenuItems part of the PromoPackage

	/**
	 * Instantiates a new PromoPackage.
	 *
	 * @param name this PromoPackage's name
	 * @param description this PromoPackage's description
	 * @param price this PromoPackage's price
	 * @param type this PromoPackage's type: Set as P (Promotion)
	 * @param isPromo whether this is a Promo Item: Set as true
	 * @param id this PrmoPackage's ID
	 */
	public PromoPackage(String name, String description, double price, String type, boolean isPromo, String id) {
		super(name, description, price, type, isPromo, id);
	}
	
	/**
	 * Adds a MenuItem to the PromoPackage.
	 *
	 * @param menu the main menu.
	 */
	public void addItem(Menu menu){
		Scanner sc = new Scanner(System.in);
		boolean found = false;
		String itemId;
		menu.showAlaCarte();
		System.out.println("Choose MenuItem to Add: ");
		itemId = sc.nextLine();
		for (MenuItem m : menu.getMenu()){
			if(m.getId().equals(itemId) || m.getName().equals(itemId)){
				pItems.add(m);
				System.out.println("Item added");
				found = true;
				break;
			}
		}
		if (!found) {
			System.out.println("Item does not exist!");
		}
	}
	
	/**
	 * Adds the item to the PromoPackage for creating preset PromoPackages quickly
	 * for test cases required by assignment
	 * 
	 *
	 * @param item the item
	 */
	public void addItem(MenuItem item){
		pItems.add(item);
	}
	
	/**
	 * Removes the MenuItem in the PromoPackage.
	 *
	 * @param menu the main menu
	 */
	public void removeItem(Menu menu){
		Scanner sc = new Scanner(System.in);
		boolean found = false;
		int ala = 1;
		String itemId;
		System.out.println("Promotion Menu:");
		System.out.println("---------------");
		System.out.printf("Name: %-20s ItemId:\n", " ");	
		for (MenuItem m : pItems){
			if(m.isPromo() == false){
				System.out.printf("%-22s %s\n", m.getName(), m.getId());
				ala++;
			}
		}
		System.out.println("Choose MenuItem to Remove: ");
		itemId = sc.nextLine();
		for (MenuItem m : pItems){
			if(m.getId().equals(itemId)|| m.getName().equals(itemId)){
				pItems.remove(m);
				System.out.println("Item Removed!");
				found = true;
				break;
			}
		}
		if (!found) {
			System.out.println("Item does not exist!");
		}
	}

	/**
	 * Gets this PromoPackage's list of MenuItems.
	 *
	 * @return pItems this PromoPackage's list of MenuItems
	 */
	public List<MenuItem> getpItems() {
		return pItems;
	}

	/**
	 * Sets this PromoPacakge's list of MenuItems.
	 *
	 * @param pItems this PromoPackage's new list of MenuItems
	 */
	public void setpItems(List<MenuItem> pItems) {
		this.pItems = pItems;
	}
}
