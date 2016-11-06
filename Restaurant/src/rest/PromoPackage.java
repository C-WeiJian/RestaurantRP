package rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class PromoPackage extends MenuItem {
	private List<MenuItem> pItems = new ArrayList<MenuItem>();	

	public PromoPackage(String name, String description, double price, String type, boolean isPromo, String id) {
		super(name, description, price, type, isPromo, id);
	}
	
	public void addItem(Menu menu){
		Scanner sc = new Scanner(System.in);
		int ala = 1;
		String itemId;
		System.out.println("Ala Carte Menu:");
		System.out.println("---------------");
		System.out.printf("Name: %-20s ItemId:\n", " ");	
		for (MenuItem m : menu.getMenu()){
			if(m.isPromo() == false){
				System.out.printf("%2d) %-22s %s\n", ala, m.getName(), m.getId());
				ala++;
			}
		}
		System.out.println("Choose MenuItem to Add: ");
		itemId = sc.nextLine();
		for (MenuItem m : menu.getMenu()){
			if(m.getId().equals(itemId)){
				pItems.add(m);
				System.out.println("Item added");
				break;
			}
			else
				System.out.println("Item does not exist!");
		}
	}
	
	public void addItem(MenuItem item){
		pItems.add(item);
	}
	
	public void removeItem(Menu menu){
		Scanner sc = new Scanner(System.in);
		int ala = 1;
		String itemId;
		System.out.println("Promotion Menu:");
		System.out.println("---------------");
		System.out.printf("Name: %-20s ItemId:\n", " ");	
		for (MenuItem m : pItems){
			if(m.isPromo() == false){
				System.out.printf("%2d) %-22s %s\n", ala, m.getName(), m.getId());
				ala++;
			}
		}
		System.out.println("Choose MenuItem to Remove: ");
		itemId = sc.nextLine();
		for (MenuItem m : pItems){
			if(m.getId().equals(itemId)){
				pItems.remove(m);
				System.out.println("Item Removed!");
				break;
			}
			else
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
