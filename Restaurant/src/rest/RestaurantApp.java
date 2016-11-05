//This is the main app for running the programme

package rest;

import java.util.Scanner;

public class RestaurantApp {
	
	public static Menu menu = new Menu();
	public static OrderMgr orderMgr = new OrderMgr();
	public static TableMgr tableMgr = new TableMgr();
	public static ReservationMgr resMgr = new ReservationMgr();
	static int choice =-1;
	static int choice2 =-1;
	static String input;
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//

		menu.loadMenu();
		
		System.out.println("Welcome to the restaurant!");
		do{
			System.out.println("--------------------------");
			System.out.println("1. View Menu");
			System.out.println("2. Create/Update/Remove Menu Item");
			System.out.println("3. Create/Update/Remove Promotion");
			System.out.println("4. Create Order");
			System.out.println("5. View Order");
			System.out.println("6. Add/Remove order items to/from order");
			System.out.println("7. Create reservation booking");
			System.out.println("8. Check/Remove reservation booking");
			System.out.println("9. Check table availability");
			System.out.println("10. Print order invoice");
			System.out.println("11. Print sales revenue report by period (day/month)");
			System.out.println("12. Exit");
			System.out.println("--------------------------");
			
			choice = sc.nextInt();
			
			switch(choice){
			case 1:
				menu.showMenu();
				break;
			case 2:
				editMenuItem();
				break;
			case 3:
				editPromo();
				break;
			case 4:
				System.out.print("Enter OrderId: ");
				int temporderId = sc.nextInt();
				orderMgr.createOrder(temporderId);
				break;
			case 5:
				System.out.println("Please enter order ID: ");
				int orderId = sc.nextInt();
				orderMgr.print(orderId);
				break;
			case 6:
				editOrder();
				break;
			case 7:
				//resMgr.checkReservation();
				break;
			case 8:
				//resMgr.addReservation();
				break;
			case 9:
				tableMgr.checkAvailability();
				break;
			case 12:
				System.out.println("Good Bye, See you again!");
				continue;
			}
			
		} while (choice != 10);

	}
	
	public static void editMenuItem(){
		int choice2;
		System.out.println("");
		do{
			System.out.println("--------------------------");
			System.out.println("1. Create Menu Item");
			System.out.println("2. Update Menu Item");
			System.out.println("3. Remove Menu Item");
			System.out.println("4. Back");
			System.out.println("--------------------------");
			
			choice2 = sc.nextInt();
			
			switch(choice2){
			case 1:
				menu.createMenuItem();
				menu.saveMenu();
				break;
			case 2:
				menu.updateMenuItem();
				menu.saveMenu();
				break;
			case 3:
				System.out.println("Which item do you want to remove?");
				sc.nextLine();
				input = sc.nextLine();
				menu.removeMenuItem(input);
				menu.saveMenu();
				break;
			case 4:
				System.out.println("Please Wait...");
				continue;
			}
			
		} while (choice2 != 4);
		
	}
	
	public static void editPromo(){
		int choice2;
		System.out.println("");
		do{
			System.out.println("--------------------------");
			System.out.println("1. Create Promo Item");
			System.out.println("2. Update Promo Item");
			System.out.println("3. Remove Promo Item");
			System.out.println("4. Back");
			System.out.println("--------------------------");
			
			choice2 = sc.nextInt();
			
			switch(choice2){
			case 1:
				menu.createPromo();
				menu.saveMenu();
				break;
			case 2:
				menu.updatePromoItem();
				menu.saveMenu();
				break;
			case 3:
				System.out.println("Which item do you want to remove?");
				sc.nextLine();
				input = sc.nextLine();
				menu.removeMenuItem(input);
				menu.saveMenu();
				break;
			case 4:
				System.out.println("Please Wait...");
				continue;
			}
			
		} while (choice2 != 4);
		
	}
	
	public static void editOrder(){
		int choice2, qty;
		MenuItem temp;
		String name;
		System.out.println("Please enter order id: ");
		int orderId = sc.nextInt();
		do{
			System.out.println("--------------------------");
			System.out.println("1. Add item to order");
			System.out.println("2. Remove item from order");
			System.out.println("3. Back");
			System.out.println("--------------------------");
			
			choice2 = sc.nextInt();
			sc.nextLine();
			
			switch(choice2){
			case 1:
				
				System.out.print("Enter Item Name or ID: ");
				name = sc.nextLine();
				System.out.print("Enter Quantity: ");
				qty = sc.nextInt();
				temp = menu.getMenuItem(name);
				orderMgr.getOrder(orderId).addMenuItem(temp, qty);
				break;
			case 2:
				orderMgr.print(orderId);
				System.out.print("Enter Item Name or ID: ");
				name = sc.nextLine();
				System.out.print("Enter Quantity: ");
				qty = sc.nextInt();
				temp = menu.getMenuItem(name);
				orderMgr.getOrder(orderId).removeMenuItem(temp, qty);
				break;
			case 3:
				System.out.println("Please Wait...");
				continue;
			}
			
		} while (choice2 != 3);
		
	}
	

}