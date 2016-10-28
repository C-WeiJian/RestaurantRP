package rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


public class Menu {
	private char section;
	private static List<MenuItem> menu = new ArrayList<MenuItem>();
	
	public void loadMenu(){
		List list;
		menu = new ArrayList<MenuItem>();
		try	{
			// read from serialized file the list of professors
			list = (ArrayList)SerializeDB.readSerializedObject("menu.dat");
			for (int i = 0 ; i < list.size() ; i++) {
				MenuItem m = (MenuItem)list.get(i);
				menu.add(new MenuItem(m.getName(), m.getDescription(), m.getPrice(), m.getType(), m.isPromo(), m.getId()));
			}
		} catch ( Exception e ) {
			System.out.println( "Exception >> " + e.getMessage() );
		}
	}
	
	public void saveMenu(){
		SerializeDB.writeSerializedObject("menu.dat", menu);
	}
	
	public void showMenu(){
		int promo = 1, ala = 1;
		System.out.println("Promotion Menu:");
		System.out.println("---------------");
		System.out.println("Name:\t\t\t\t Price:");
		for (MenuItem m : menu){
			if(m.isPromo() == true){
				System.out.println(promo + ") " + m.getName() + "\t\t\t\t" + m.getPrice());
				System.out.println("   " + m.getDescription());
				promo++;
			}
			else break;
		}
		System.out.println("Ala Carte Menu:");
		System.out.println("---------------");
		System.out.println("Drinks: ");
		System.out.println("Name:\t\t\t\t Price:");		
		for (MenuItem m : menu){
			if(m.isPromo() == false){
				System.out.println(ala + ") " + m.getName() + "\t\t\t\t" + m.getPrice());
				System.out.println("\t" + m.getDescription());
				ala++;
			}
		}	
	}
	
	public void createMenuItem(){
		Scanner sc = new Scanner(System.in);
		String name, description, type, id;
		double price;
		System.out.println("Name of new MenuItem: ");
		name = sc.nextLine();
		System.out.println("Description of new MenuItem: ");
		description = sc.nextLine();
		System.out.println("Price of new MenuItem: ");
		System.out.print("$ ");
		price = sc.nextDouble();
		System.out.println("Type of new MenuItem: ");
		type = sc.nextLine();
		System.out.println("ID of new MenuItem: ");
		id = sc.nextLine();
		MenuItem tempMItem = new MenuItem(name, description, price, type, false, id);		
		menu.add(tempMItem);
	}
	
	public void updateMenuItem(){
		
	}
	public void removeMenuItem(String name){
		MenuItem temp = getMenuItem(name);
		if (temp != null){
			menu.remove(temp);
			System.out.println(name + "succesfully removed");
		}
		else 
			System.out.println("Item does not exist");
	}
	
	public void createPromo(){
		Scanner sc = new Scanner(System.in);
		String name, description, id;
		double price;
		int choice = -1;
		System.out.println("Name of new PromoPackage: ");
		name = sc.nextLine();
		System.out.println("Description of new PromoPackage: ");
		description = sc.nextLine();
		System.out.println("Price of new PromoPackage: ");
		System.out.print("$ ");
		price = sc.nextDouble();
		System.out.println("ID of new MenuItem: ");
		id = sc.nextLine();
		PromoPackage tempMPromo = new PromoPackage(name, description, price, "p", true, id);	
		do {
			System.out.println("----------------------");
			System.out.println("1. Add new item");
			System.out.println("2. Done");
			System.out.println("----------------------");
			choice = sc.nextInt();
			switch (choice){
			case 1: 
				tempMPromo.addItem(this);
				break;
			case 2:
				continue;
			default:
				System.out.println("Please enter a valid choice:");
			} 
		}while (choice != 2);	
		menu.add(tempMPromo);
	}
	
	public void updatePromo(){
		Scanner sc = new Scanner(System.in);
		int choice = -1, promo = 1;
		String promoId;
		System.out.println("Promotion Menu:");
		System.out.println("---------------");
		System.out.println("Name:\t\t\t\t PromoId:");
		for (MenuItem m : menu){
			if(m.isPromo() == true){
				System.out.println(promo + ") " + m.getName() + "\t\t\t\t" + m.getId());
				System.out.println("   " + m.getDescription());
				promo++;
			}
			else break;
		}
		System.out.println("PromoPackage ID to edit: ");
		promoId = sc.nextLine();
		PromoPackage tempPromo = (PromoPackage) getMenuItem(promoId);
		do{
			System.out.println("----------------------");
			System.out.println("1. Add new item");
			System.out.println("2. Remove item");
			System.out.println("3. Done");
			System.out.println("----------------------");
			switch(choice){
			case 1: 
				tempPromo.addItem(this);
				break;
			case 2:
				tempPromo.removeItem(this);
				break;
			case 3: 
				continue;
			}
		}while(choice != 3);
	}
	
	public MenuItem getMenuItem(String name){
		for (MenuItem m : menu){
			if (m.getName().equals(name) || m.getId().equals(name)){
				return m;
			}
		}
		return null;
	}

	public List<MenuItem> getMenu() {
		return menu;
	}

	public void setMenu(List<MenuItem> menu) {
		Menu.menu = menu;
	}
}
