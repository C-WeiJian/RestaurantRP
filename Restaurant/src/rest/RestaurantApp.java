//This is the main app for running the programme

package rest;

import java.util.Scanner;

public class RestaurantApp {
	
	public static Menu menu = new Menu();
	public static OrderMgr ordermgr = new OrderMgr();
	static int choice =-1;
	static int choice2 =-1;
	static String input;
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//hi loser;
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
			
			System.out.println("10. Quit");
			System.out.println("--------------------------");
			
			choice = sc.nextInt();
			
			switch(choice){
			case 1:
				menu.showMenu();
				break;
			case 2:
				CreUpdRemMenItem();
				break;
			case 3:
				menu.createPromo();
			case 4:
				ordermgr.createOrder();
				break;
			case 10:
				System.out.println("Good Bye, See you again!");
				continue;
			}
			
		} while (choice != 10);

	}
	
	public static void CreUpdRemMenItem(){
		int choice2;
		System.out.println("Welcome to the restaurant!");
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

}
