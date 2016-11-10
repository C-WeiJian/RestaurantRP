package rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * The Menu Class
 * 
 * @version 1.0
 * @since 2016-10-28
 */
public class Menu {
	
	/** The section. */
	private char section;
	
	/** The menu, which is a list of MenuItems. */
	private static List<MenuItem> menu = new ArrayList<MenuItem>();

	/**
	 * Load menu from the menu.dat file.
	 */
	public void loadMenu(){
		List list;
		menu = new ArrayList<MenuItem>();
		try	{
			list = (ArrayList)SerializeDB.readSerializedObject("menu.dat");
			for (int i = 0 ; i < list.size() ; i++) {
				if (list.get(i) instanceof MenuItem){
					MenuItem m = (MenuItem)list.get(i);
					menu.add(m);
				}
				else if (list.get(i) instanceof PromoPackage){
					System.out.println("I'm a PromoPackage");
					PromoPackage p = (PromoPackage)list.get(i);
					menu.add(p);
				}
			}
		} catch ( Exception e ) {
			System.out.println( "Exception >> " + e.getMessage() );
		}
	}
	
	/**
	 * Save current menu to menu.dat.
	 */
	public void saveMenu(){
		SerializeDB.writeSerializedObject("menu.dat", menu);
	}
	
	/**
	 * Prints menu according to type.
	 */
	public void showMenu(){
		int promo = 1, main = 1, beverage = 1, desert = 1;
		System.out.println("---------------------------------------");		
		System.out.println("Promotion Menu:");
		System.out.println("---------------------------------------");
		System.out.printf("Name: %-20s Price: %2s ID: \n", " ", " ");
		for (MenuItem m : menu){
			if(m.isPromo() == true){
				int n = 1;
				System.out.printf("%2d) %-22s $%.2f %3s %3s\n", promo, m.getName(), m.getPrice(), " ", m.getId());
				System.out.printf("%4s %-20s\n", " ", m.getDescription());
				if (m instanceof PromoPackage){
					//System.out.println("I'm a PromoPackage");
					for(MenuItem i : ((PromoPackage)m).getpItems()){
						System.out.printf("%4s (%d) %-20s\n", " ", n, i.getName());
						n++;
					}
				}
				else if (m instanceof MenuItem){
					System.out.println(":(");
				}
				promo++;
			}
		}
		System.out.println("---------------------------------------");
		System.out.println("Ala Carte Menu:");
		System.out.println("---------------------------------------");
		System.out.printf("Name: %-20s Price: %2s ID:\n", " ", " ");
		System.out.println("Mains");
		for (MenuItem m : menu){
			if(m.isPromo() == false){
				if(m.getType().equals("M")){
					System.out.printf("%2d) %-22s $%4.2f %3s %3s\n", main, m.getName(), m.getPrice(), " ", m.getId());
					System.out.printf("%4s %-20s\n", " ",  m.getDescription());
					main++;
				}
			}
		}
		System.out.println("Beverages");
		for (MenuItem m : menu){
			if(m.isPromo() == false){
				if(m.getType().equals("B")){
					System.out.printf("%2d) %-22s $%.2f %7s\n", beverage, m.getName(), m.getPrice(), m.getId());
					System.out.printf("%4s %-20s\n", " ",  m.getDescription());
					beverage++;
				}
			}
		}	
		System.out.println("Deserts");
		for (MenuItem m : menu){
			if(m.isPromo() == false){
				if(m.getType().equals("D")){
					System.out.printf("%2d) %-22s $%.2f %7s\n", desert, m.getName(), m.getPrice(), m.getId());
					System.out.printf("%4s %-20s\n", " ",  m.getDescription());
					desert++;
				}
			}
		}	
	}
	
	
	
	/**
	 * Creates a new menu item and adds it to the menu.
	 */
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
		sc.nextLine();
		System.out.println("Type of new MenuItem: ");
		type = sc.nextLine();
		System.out.println("ID of new MenuItem: ");
		id = sc.nextLine();
		MenuItem tempMItem = new MenuItem(name, description, price, type.toUpperCase(), false, type + id);
		check(tempMItem);
		menu.add(tempMItem);
	}
	
	/**
	 * Allows users to update a certain menu item.
	 */
	public void updateMenuItem(){
		String input = "", temp;
		int choice = -1, current = 0, previous;
		Scanner sc = new Scanner(System.in);
		System.out.println("Which item do you want to edit?");
		input = sc.nextLine();
		for (MenuItem m : menu) {
			if (m.getName().equals(input) || m.getId().equals(input.toUpperCase())){
				do{
					System.out.println("What do you want to edit?");
					System.out.println("1. Name");
					System.out.println("2. Description");
					System.out.println("3. Price");
					System.out.println("4. Type");
					System.out.println("5. ID");
					System.out.println("6. Back");
					
					choice = sc.nextInt();
					sc.nextLine();
					switch (choice){
					case 1: 
						System.out.println("Enter new name:");
						temp = sc.nextLine();
						do{
							previous = current;
							for(MenuItem n : menu){
								if(n.getName().equals(temp)){
									System.out.println("Name already exists, please enter a new name");
									temp = sc.nextLine();
									current++;
								}
							}
						} while( previous != current);
						m.setName(temp);
						break;
					case 2:
						System.out.println("Enter new description:");
						m.setDescription(sc.nextLine());
						break;
					case 3:
						System.out.println("Enter new price:");
						m.setPrice(sc.nextDouble());
						sc.nextLine();
						break;
					case 4:
						System.out.println("Enter new Type:");
						m.setType(sc.nextLine().toUpperCase());
						m.setId(m.getType() + m.getId().substring(1));
						break;
					case 5:
						System.out.println("Enter new ID:");
						temp = m.getType() + sc.nextLine();
						do{
							previous = current;
							for(MenuItem n : menu){
								if(n.getName().equals(temp)){
									System.out.println("ID already exists, please enter a new ID");
									temp = m.getType() + sc.nextLine();
									current++;
								}
							}
						} while(previous != current);
						m.setId(temp);
						break;
					case 6:
						continue;
					default:
						System.out.println("Please enter a valid choice");
						break;
					}
				} while (choice != 6);
			}
		}	
	}
	
	/**
	 * Update PromoItem.
	 */
	public void updatePromoItem(){
		String input = "", temp = "";
		int choice = -1, choice2 = -1, current = 0, previous;
		Scanner sc = new Scanner(System.in);
		showPromo();
		System.out.println("Which item do you want to edit?");
		input = sc.nextLine();
		for (Object m : menu) {
			int i = 0;
			if (((MenuItem) m).getName().equals(input) || ((MenuItem) m).getId().equals(input.toUpperCase())){
				do{
					System.out.println("What do you want to edit?");
					System.out.println("1. Name");
					System.out.println("2. Description");
					System.out.println("3. Price");
					System.out.println("4. Type");
					System.out.println("5. ID");
					System.out.println("6. Promo items");
					System.out.println("7. Back");
					
					choice = sc.nextInt();
					sc.nextLine();
					switch (choice){
					case 1: 
						System.out.println("Enter new name:");
						temp = sc.nextLine();
						do{
							previous = current;
							for(MenuItem n : menu){
								if((n.getName().equals(temp))){
									System.out.println("Name already exists, please enter a new name");
									temp = sc.nextLine();
									current++;
								}
							}
						} while( previous != current);
						((MenuItem) m).setName(temp);
						break;
					case 2:
						System.out.println("Enter new description:");
						((MenuItem) m).setDescription(sc.nextLine());
						break;
					case 3:
						System.out.println("Enter new price:");
						((MenuItem) m).setPrice(sc.nextDouble());
						sc.nextLine();
						break;
					case 4:
						System.out.println("Enter new Type:");
						((MenuItem) m).setType(sc.nextLine().toUpperCase());
						break;
					case 5:
						System.out.println("Enter new ID:");
						temp = ((MenuItem) m).getType() + sc.nextLine();
						do{
							previous = current;
							for(MenuItem n : menu){
								if(n.getName().equals(temp)){
									System.out.println("ID already exists, please enter a new ID");
									temp = ((MenuItem) m).getType() + sc.nextLine();
									current++;
								}
							}
						} while(previous != current);
						((MenuItem) m).setId(temp);
						break;
					case 6:
						do{
							System.out.println("1. Add new item");
							System.out.println("2. Remove item");
							System.out.println("3. Back");
							choice2 = sc.nextInt();
							switch(choice2) {
							case 1: 
								((PromoPackage) m).addItem(this);
								break;
							case 2:
								((PromoPackage) m).removeItem(this);
								break;
							case 3:
								continue;
							default:
								System.out.println("Please enter a valid choice");
								break;
							}
						} while (choice2 != 3);
					case 7:
						continue;
					default:
						System.out.println("Please enter a valid choice");
						break;
					}
				} while (choice != 7);
				i++;
			} else i++;
		}	
	}
	
	/**
	 * Removes a menu item.
	 *
	 * @param name the name
	 */
	public void removeMenuItem(String name){
		MenuItem temp = getMenuItem(name);
		if (temp != null){
			menu.remove(temp);
			System.out.println(name + " succesfully removed");
		}
		else 
			System.out.println("Item does not exist");
	}
	
	/**
	 * Creates a PromoItem.
	 */
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
		sc.nextLine();
		System.out.println("ID of new MenuItem: ");
		id = sc.nextLine();
		PromoPackage tempMPromo = new PromoPackage(name, description, price, "P", true, "P" + id);
		check(tempMPromo);
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
	
	/**
	 * Gets the menu item.
	 *
	 * @param name the name
	 * @return the menu item
	 */
	public MenuItem getMenuItem(String name){
		for (MenuItem m : menu){
			if (m.getName().equals(name) || m.getId().equals(name.toUpperCase())){
				return m;
			}
		}
		return null;
	}
	
	/**
	 * Check if a MenuItem has the same name or ID 
	 * as an existing MenuItem.
	 *
	 * @param tempMItem the MenuItem to check
	 */
	public void check(MenuItem tempMItem) {
		checkName(tempMItem);
		checkID(tempMItem);
	}
	
	/**
	 * Check if a MenuItem has the same name
	 * as an existing MenuItem.
	 *
	 * @param tempMItem the MenuItem to check
	 */
	public void checkName(MenuItem tempMItem) {
		int current = 0, previous;
		Scanner sc = new Scanner(System.in);
		do{
			previous = current;
			for (MenuItem m : menu){
				if (m.getName().equals(tempMItem.getName())){
					System.out.println("Item name already exists, please enter a new name:");
					tempMItem.setName(sc.nextLine());
					current++;
				}
			}
		} while (previous != current);
	}
	
	/**
	 * Check if a MenuItem has the same ID
	 * as an existing MenuItem.
	 *
	 * @param tempMItem the MenuItem to check
	 */
	public void checkID(MenuItem tempMItem) {
		int current = 0, previous;
		Scanner sc = new Scanner(System.in);
		do{
			previous = current;
			for (MenuItem m : menu){
				if (m.getId().equals(tempMItem.getId())){
					System.out.println("Item ID already exists, please enter a new ID:");
					tempMItem.setId(tempMItem.getType() + sc.nextLine());
					current++;
				}
			}
		} while (previous != current);
	}
	
	/**
	 * Show the ala carte menu.
	 */
	public void showAlaCarte() {
		System.out.println("Ala Carte Menu:");
		System.out.println("---------------");
		System.out.printf("Name: %-20s ItemId:\n", " ");	
		for (MenuItem m : menu){
			if(m.isPromo() == false){
				System.out.printf("%-26s %s\n", m.getName(), m.getId());
			}
		}
	}
	
	/**
	 * Show the promo menu.
	 */
	public void showPromo() {
		System.out.println("Promotion Menu:");
		System.out.println("---------------");
		System.out.printf("Name: %-20s ItemId:\n", " ");	
		for (MenuItem m : menu){
			if(m.isPromo() == true){
				System.out.printf("%-26s %s\n", m.getName(), m.getId());
			}
		}
	}
	
	/**
	 * Show simplified menu with MenuItem names and IDs.
	 */
	public void showSimpleMenu() {
		showPromo();
		showAlaCarte();
	}

	/**
	 * Gets the current menu.
	 *
	 * @return the menu
	 */
	public List<MenuItem> getMenu() {
		return menu;
	}

	/**
	 * Sets the menu.
	 *
	 * @param menu the new menu
	 */
	public void setMenu(List<MenuItem> menu) {
		Menu.menu = menu;
	}
	
	/**
	 * Initializes the default menu.
	 */
	public void initDefaultMenu() {
		menu = new ArrayList<MenuItem>();
		
		menu.add(new MenuItem("Steamed Chicken Rice", "Hainanese Steamed Chicken with Rice", 4.50, "M", false, "M01"));
		menu.add(new MenuItem("Roasted Chicken Rice", "Roasted Chicken with Rice", 4.50, "M", false, "M02"));
		menu.add(new MenuItem("Char Siew Rice", "Honey Glazed Roasted Pork with Rice", 4.00, "M", false, "M03"));
		menu.add(new MenuItem("Roasted Pork Rice", "Crispy Roasted Pork with Rice", 4.00, "M", false, "M04"));
		menu.add(new MenuItem("Bak Kut Teh", "Pork Rib Soup with Rice", 5.00, "M", false, "M05"));
		
		menu.add(new MenuItem("Canned Soft Drink", "Assorted Canned Drinks", 2.00, "B", false, "B01"));
		menu.add(new MenuItem("Hot Tea", "Tea served with Sugar and Milk", 2.50, "B", false, "B02"));
		menu.add(new MenuItem("Hot Coffee", "Coffee served with Sugar and Milk", 2.50, "B", false, "B03"));
		menu.add(new MenuItem("Mango Juice", "Freshly Squeezed Manjo Juice", 3.00, "B", false, "B04"));
		menu.add(new MenuItem("Watermelon Juice", "Freshly Squeezed Watermelon Juice", 2.50, "B", false, "B05"));
		
		menu.add(new MenuItem("Ice Cream", "Assorted Ice Cream Flavours", 4.00, "D", false, "D01"));
		menu.add(new MenuItem("Mango Pudding", "Homemade Mango Pudding with Jelly", 4.50, "D", false, "D02"));
		menu.add(new MenuItem("Strawberry Shortcake", "Cream filled cake with Strawberry", 4.50, "D", false, "D03"));
		
		PromoPackage temp = new PromoPackage("Lunch Set A", "Steamed Chicken Rice with Drink", 6.00, "P", true, "P01");
		temp.addItem(getMenuItem("Steamed Chicken Rice"));
		temp.addItem(getMenuItem("Canned Soft Drink"));
		menu.add(temp);

		temp = new PromoPackage("Lunch Set B", "Bak Kut Teh with Drink", 6.50, "P", true, "P02");
		temp.addItem(getMenuItem("Bak Kut Teh"));
		temp.addItem(getMenuItem("Canned Soft Drink"));
		menu.add(temp);
		
		temp = new PromoPackage("Lunch Set C", "Char Siew Rice with Drink and Dessert", 8.00, "P", true, "P03");
		temp.addItem(getMenuItem("Char Siew Rice"));
		temp.addItem(getMenuItem("Canned Soft Drink"));
		temp.addItem(getMenuItem("Ice Cream"));
		menu.add(temp);
	}
}
