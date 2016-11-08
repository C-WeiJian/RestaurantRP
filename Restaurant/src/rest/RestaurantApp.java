//This is the main app for running the programme

package rest;

import java.util.ArrayList;
import java.util.Scanner;

public class RestaurantApp {

	public static Menu menu = new Menu();
	public static OrderMgr orderMgr = new OrderMgr();
	public static TableMgr tableMgr = new TableMgr();
	public static ReservationMgr resMgr = new ReservationMgr();
	public static SalesMgr salesMgr = new SalesMgr();
	static int choice = -1;
	static int choice2 = -1;
	static String input;
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		// Initialize menu
		menu.loadMenu();

		// Print main menu
		System.out.println();
		System.out.println("Welcome to the restaurant!");
		do {
			System.out.println("---------------------------------------");
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
			System.out.println("11. Print sales revenue report by period");
			System.out.println("12. Admin Setup Features");
			System.out.println("13. Exit");
			System.out.println("---------------------------------------");
			System.out.print("Enter choice: ");
			choice = sc.nextInt();

			switch (choice) {
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
				resMgr.updateRes();
				updateTables();
				createOrder();
				break;
			case 5:
				resMgr.updateRes();
				updateTables();
				viewOrder();
				break;
			case 6:
				resMgr.updateRes();
				updateTables();
				editOrder();
				break;
			case 7:
				resMgr.addReservation();
				// updateRes() is called within addReservation()
				updateTables();
				break;
			case 8:
				editReservation();
				// updateRes() is called within checkReservation() and removeReservation() in ReservationMgr
				updateTables();
				break;
			case 9:
				resMgr.updateRes();
				updateTables();
				tableMgr.showTable(); // let status of table check the presence
				break;
			case 10:
				sc.nextLine();
				printInvoice();
				break;
			case 11:
				sc.nextLine();
				printSales();
				break;
			case 12:
				admin();
				break;
			case 13:
				System.out.println("Good Bye, See you again!");
				continue;
			}

		} while (choice != 13);

	}

	public static void editMenuItem() {
		int choice2;
		System.out.println("");
		do {
			System.out.println("--------------------------");
			System.out.println("1. Create Menu Item");
			System.out.println("2. Update Menu Item");
			System.out.println("3. Remove Menu Item");
			System.out.println("4. Back");
			System.out.println("--------------------------");

			choice2 = sc.nextInt();

			switch (choice2) {
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

	public static void editPromo() {
		int choice;
		System.out.println("");
		do {
			System.out.println("--------------------------");
			System.out.println("1. Create Promo Item");
			System.out.println("2. Update Promo Item");
			System.out.println("3. Remove Promo Item");
			System.out.println("4. Back");
			System.out.println("--------------------------");
			System.out.print("Enter choice: ");
			choice = sc.nextInt();

			switch (choice) {
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

		} while (choice != 4);

	}

	public static void createOrder() {
		resMgr.updateRes();
		System.out.print("Enter OrderId: ");
		int temporderId = sc.nextInt();
		System.out.print("Enter Pax: ");
		int temppax = sc.nextInt();
		int temptableid = tableMgr.assignTable(temporderId, temppax);
		if (temptableid == -1)
			System.out.println("No Table Available\nNo order created.");
		else {
			System.out.print("Enter Staff Id: ");
			int staffid = sc.nextInt();
			boolean check = orderMgr.createOrder(temporderId, staffid, temptableid);
			if (check == true)
				System.out.println("Order Created!\nOrder " + temporderId + " is assigned to Table " + temptableid);
			else tableMgr.updateTable(temptableid, true);
		}
	}

	public static Order getOrder() {
		int choice;
		Order temporder = null;
		do {
			System.out.println("--------------------------");
			System.out.println("Choose Retrieval Method");
			System.out.println("1. By Order ID");
			System.out.println("2. By Table ID");
			System.out.println("--------------------------");
			System.out.print("Enter choice: ");
			choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {
			case 1:
				System.out.print("Please enter Order ID: ");
				int orderId = sc.nextInt();
				temporder = orderMgr.getOrder(orderId);
				break;
			case 2:
				System.out.print("Please enter Table ID: ");
				int tableId = sc.nextInt();
				temporder = orderMgr.getOrder(tableMgr.getTable(tableId).getorderId());
				break;
			}
			if (temporder == null) {
				System.out.println("Order not Found");
				return null;
			}
			return temporder;
		} while (true);

	}

	public static void viewOrder() {
		Order order = getOrder();
		if (order == null)
			System.out.println("No Info to Show.");
		else
			orderMgr.print(order);
	}

	public static void printInvoice() {
		Order order = getOrder();
		if (order != null) {
			int temp = order.getTableId();
			salesMgr.updateSales(order);
			orderMgr.printInvoice(order);
			tableMgr.updateTable(temp, true);
		}

	}

	public static void editOrder() {
		int choice, qty;
		MenuItem temp;
		String name;

		Order order = getOrder();
		if (order == null)
			System.out.println("No Info to Show.");
		else {
			do {
				System.out.println("--------------------------");
				System.out.println("1. Add item to order");
				System.out.println("2. Remove item from order");
				System.out.println("3. Delete/Remove entire Order");
				System.out.println("4. Back");
				System.out.println("--------------------------");
				System.out.print("Enter choice: ");
				choice = sc.nextInt();
				sc.nextLine();

				switch (choice) {
				case 1:
					menu.showSimpleMenu();
					System.out.print("Enter Item Name or ID: ");
					name = sc.nextLine();
					System.out.print("Enter Quantity: ");
					qty = sc.nextInt();
					temp = menu.getMenuItem(name);
					order.addMenuItem(temp, qty);
					break;
				case 2:
					orderMgr.print(order);
					System.out.print("Enter Item Name or ID: ");
					name = sc.nextLine();
					System.out.print("Enter Quantity: ");
					qty = sc.nextInt();
					temp = menu.getMenuItem(name);
					order.removeMenuItem(temp, qty);
					break;
				case 3:
					orderMgr.removeOrder(order);
					int temp2 = order.getTableId();
					tableMgr.updateTable(temp2, true);
					break;
				case 4:
					System.out.println("Please Wait...");
					orderMgr.saveOrder();
					continue;
				}

			} while (choice != 4);
		}
	}
	
	public static void editReservation() {
		int choice;
		do {
			System.out.println("--------------------------");
			System.out.println("1: Check reservation");
			System.out.println("2: Remove reservation");
			System.out.println("3: Back");
			System.out.println("--------------------------");
			
			choice = sc.nextInt();
			
			switch(choice) {
			case 1:
				resMgr.checkReservation();
				break;
			case 2:
				resMgr.removeReservation();
				break;
			case 3:
				break;
			}
		} while (choice !=3);
	}

	public static void admin() {
		int choice;
		do {
			System.out.println("--------------------------");
			System.out.println("For Setup Purposes");
			System.out.println("1. Full Preset");
			System.out.println("2. Preset Menu as required by Assignment");
			System.out.println("3. Preset Table as required by Assignment");
			System.out.println("4. Preset Staff as required by Assignment");
			System.out.println("5. Add Table");
			System.out.println("10. Back");
			System.out.println("--------------------------");
			System.out.print("Enter choice: ");
			choice = sc.nextInt();
			sc.nextLine();

			/*test cases of full reservation, releasing of table/s upon payment, removing
			of reservation/s upon ‘period expiry’and generating of bill invoice.
			maybe one to load multiple completed orders*/
			
			switch (choice) {
			case 1:
				tableMgr.presetTable();
				orderMgr.presetStaff();
				orderMgr.resetallOrder();
				menu.initDefaultMenu();
				menu.saveMenu();
				resMgr.resetRes();
				salesMgr.resetSales();
				break;
			case 2:
				menu.initDefaultMenu();
				menu.saveMenu();
				break;
			case 3:
				tableMgr.presetTable();
				break;
			case 4:
				orderMgr.presetStaff();
				break;
			case 5:
				tableMgr.addTable();
				break;
			case 10:
				break;
			}
		} while (choice != 10);
	}

	public static void updateTables() {
		ArrayList<Integer> temp = resMgr.getUnavailableTables(); // get tableId
		tableMgr.updateRes(temp);
	}

	public static void printSales() {

		System.out.print("Please enter start date (yyyy-MM-dd): ");
		String start = sc.nextLine().substring(0, 10) + " 00:00";
		System.out.print("Please enter end date (yyyy-MM-dd): ");
		String end = sc.nextLine().substring(0, 10) + " 23:59";
		salesMgr.printSales(start, end);
	}
	

}
