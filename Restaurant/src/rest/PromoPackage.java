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
		System.out.println("Name: \t\t\t\t Item ID:");		
		for (MenuItem m : menu.getMenu()){
			if(m.isPromo() == false){
				System.out.println(ala + ") " + m.getName() + "\t\t\t\t" + m.getId());
				ala++;
			}
		}
		System.out.println("Choose MenuItem to Add: ");
		itemId = sc.nextLine();
		for (MenuItem m : menu.getMenu()){
			if(m.getId().equals(itemId)){
				pItems.add(menu.getMenuItem(itemId));
				break;
			}
		}
	}
	
	public void removeItem(Menu menu){
		Scanner sc = new Scanner(System.in);
		int ala = 1;
		String itemId;
		System.out.println("Promotion Menu:");
		System.out.println("---------------");
		System.out.println("Name: \t\t\t\t Item ID:");		
		for (MenuItem m : pItems){
			if(m.isPromo() == false){
				System.out.println(ala + ") " + m.getName() + "\t\t\t\t" + m.getId());
				ala++;
			}
		}
		System.out.println("Choose MenuItem to Remove: ");
		itemId = sc.nextLine();
		for (MenuItem m : pItems){
			if(m.getId().equals(itemId))
				pItems.remove(m);	
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
