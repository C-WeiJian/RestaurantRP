package rest;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class PromoPackage extends MenuItem {
	private List<MenuItem> pItems = new ArrayList<MenuItem>();		// list of MenuItems part of the PromoPackage

	public PromoPackage(String name, String description, double price, String type, boolean isPromo, String id) {
		super(name, description, price, type, isPromo, id);
	}
	
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
	
	public void addItem(MenuItem item){
		pItems.add(item);
	}
	
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

	public List<MenuItem> getpItems() {
		return pItems;
	}

	public void setpItems(List<MenuItem> pItems) {
		this.pItems = pItems;
	}
}
