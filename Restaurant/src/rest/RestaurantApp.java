//This is the main app for running the programme

package rest;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is main code for the Restaurant Application.
 *
 * @version 1.0
 * @since 2016-10-28
 */
public class RestaurantApp {

	/** The menu. */
	public static Menu menu = new Menu();

	/** The order manager. */
	public static OrderMgr orderMgr = new OrderMgr();

	/** The table manager. */
	public static TableMgr tableMgr = new TableMgr();

	/** The reservation manager. */
	public static ReservationMgr resMgr = new ReservationMgr();

	/** The sales manager. */
	public static SalesMgr salesMgr = new SalesMgr();

	/** The user's choice. */
	static int choice = -1;

	/**
	 * The user's second choice. Used in the case of double nested do-while loop
	 */
	static int choice2 = -1;

	/** The User's string inputs. */
	static String input;

	/** The scanner. */
	static Scanner sc = new Scanner(System.in);

	/**
	 * The main method. Updates the corresponding function to the latest version
	 * whenever that function is called
	 * 
	 * @param args the arguments
	 */
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
				resMgr.addReservation(tableMgr.getTableList());
				// updateRes() is called within addReservation()
				updateTables();
				break;
			case 8:
				editReservation();
				// updateRes() is called within checkReservation() and
				// removeReservation() in ReservationMgr
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
				updateTables();
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

	/**
	 * Brings up the menu to edit the menu items.
	 */
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

	/**
	 * Brings up the menu to edit the promo items.
	 */
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

	/**
	 * Creates the order. Checks whether the customer is a 'walk-in' customer or
	 * someone who already has a reservation
	 */
	public static void createOrder() {

		System.out.println("Do you have a reservation? (1: yes, 2: no) ");

		int yesno;
		int tableId;
		boolean check;
		yesno = sc.nextInt();
		if (yesno == 1) {
			resMgr.activateReservation();
		}

		resMgr.updateRes();
		updateTables();

		System.out.print("Enter Pax: ");
		int temppax = sc.nextInt();
		tableId = tableMgr.nextAvailableTable(temppax);
	
		if (tableId == -1)
			System.out.println("No Table Available\nNo order created.");
		else {
			do{
			System.out.print("Enter OrderId: ");
			int temporderId = sc.nextInt();
			System.out.print("Enter Staff Id[1-5]: ");
			int staffid = sc.nextInt();
			check = orderMgr.createOrder(temporderId, staffid, tableId);
			if (check)
				System.out.println("Order Created!\nOrder " + temporderId + " is assigned to Table " + tableId);
			} while (!check);
		}
	}

	/**
	 * Gets the order.
	 *
	 * @return the order
	 */
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

	/**
	 * View order.
	 */
	public static void viewOrder() {
		Order order = getOrder();
		if (order == null)
			System.out.println("No Info to Show.");
		else
			orderMgr.print(order);
	}

	/**
	 * Prints the invoice.
	 */
	public static void printInvoice() {
		Order order = getOrder();
		if (order != null) {
			int temp = order.getTableId();
			salesMgr.updateSales(order);
			orderMgr.printInvoice(order);
		}

	}

	/**
	 * Edits the order.
	 */
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
				System.out.println("You are currently editing Order " + order.getOrderId());
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
					/*
					 * int temp2 = order.getTableId(); //consider removing
					 * tableMgr.updateTable(temp2, 1);
					 */
					break;
				case 4:
					System.out.println("Please Wait...");
					orderMgr.saveOrder();
					continue;
				}

			} while (choice != 4);
		}
	}

	/**
	 * Edits the reservation.
	 */
	public static void editReservation() {
		int choice;
		do {
			System.out.println("--------------------------");
			System.out.println("1: Check reservation");
			System.out.println("2: Remove reservation");
			System.out.println("3: Back");
			System.out.println("--------------------------");

			choice = sc.nextInt();

			switch (choice) {
			case 1:
				resMgr.checkReservation();
				break;
			case 2:
				resMgr.removeReservation();
				break;
			case 3:
				break;
			}
		} while (choice != 3);
	}

	/**
	 * Admin menu functions. 
	 * This is used to automatically set up the required situations and conditions to realize the test cases specified by the assignment.
	 * This function is also essential in initializing the default settings of the restaurant. 
	 */
	public static void admin() {
		int choice;
		do {
			System.out.println("--------------------------");
			System.out.println("For Setup Purposes");
			System.out.println("1. Full Reset");
			System.out.println("2. Test Case - Full Reservation");
			System.out.println("3. Test Case - Load Multiple Completed and Active Orders");
			System.out.println("4. Preset Default Menu");
			System.out.println("5. Preset Default Tables of 30 ");
			System.out.println("6. Add a Single Table");
			System.out.println("7. See All Reservations");
			System.out.println("7. Reset and Remove All Orders");
			System.out.println("10. Back");
			System.out.println("--------------------------");
			System.out.print("Enter choice: ");
			choice = sc.nextInt();
			sc.nextLine();

			/*
			 * test cases of full reservation, releasing of table/s upon
			 * payment, removing of reservation/s upon ‘period expiry’and
			 * generating of bill invoice. maybe one to load multiple completed
			 * orders
			 */

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
				tableMgr.presetTable();
				orderMgr.presetStaff();
				orderMgr.resetallOrder();
				menu.initDefaultMenu();
				menu.saveMenu();
				resMgr.resetRes();
				salesMgr.resetSales();
				resMgr.initializeFullReservation(tableMgr.getTableList());
				break;
			case 3:
				presetOrders();
				break;
			case 4:
				menu.initDefaultMenu();
				menu.saveMenu();
				break;
			case 5:
				tableMgr.presetTable();
				break;
			case 6:
				tableMgr.addTable();
				break;
			case 7:
				resMgr.printAllReservations();
				break;
			case 8:
				orderMgr.resetallOrder();
			case 10:
				break;
			}
		} while (choice != 10);
	}

	/**
	 * Update tables.
	 */
	public static void updateTables() {
		ArrayList<Integer> temp1 = resMgr.getUnavailableTables(); // get tableId
		ArrayList<Integer[]> temp2 = orderMgr.getUnavailableTables();
		tableMgr.fullUpdate(temp1, temp2);
	}

	/**
	 * Prints the sales.
	 */
	public static void printSales() {

		System.out.print("Please enter start date (yyyy-MM-dd): ");
		String start = sc.nextLine().substring(0, 10) + " 00:00";
		System.out.print("Please enter end date (yyyy-MM-dd): ");
		String end = sc.nextLine().substring(0, 10) + " 23:59";
		salesMgr.printSales(start, end);
	}

	/**
	 * Preset orders. This executes the full reset and generate information
	 * needed for test cases. This generates random sales information. The first
	 * loop adds sends in random order information across the year into
	 * saleslist. This also generates active orders from table 1-29. With random
	 * order items loaded.
	 */
	public static void presetOrders() {
		MenuItem item1, item2, item3, item4;

		tableMgr.presetTable();
		orderMgr.presetStaff();
		orderMgr.resetallOrder();
		menu.initDefaultMenu();
		menu.saveMenu();
		resMgr.resetRes();
		salesMgr.resetSales();

		for (int i = 1; i < 300; i++) {
			item1 = menu.getMenuItem("M0" + (int) (5 * Math.random() + 1));
			item2 = menu.getMenuItem("D0" + (int) (3 * Math.random() + 1));
			item3 = menu.getMenuItem("P0" + (int) (3 * Math.random() + 1));
			item4 = menu.getMenuItem("B0" + (int) (5 * Math.random() + 1));
			salesMgr.updateSales(orderMgr.quicksetOrder(item1, item2, item3, item4, i));
		}
		for (int i = 1; i < 30; i++) {
			item1 = menu.getMenuItem("M0" + (int) (5 * Math.random() + 1));
			item2 = menu.getMenuItem("D0" + (int) (3 * Math.random() + 1));
			item3 = menu.getMenuItem("P0" + (int) (3 * Math.random() + 1));
			item4 = menu.getMenuItem("B0" + (int) (5 * Math.random() + 1));
			boolean check = orderMgr.createOrder(i, 3, i);
			if (!check)
				System.out.println("Error - cannot create");
			orderMgr.getOrder(i).addMenuItem(item1, (int) (10 * Math.random() + 1));
			orderMgr.getOrder(i).addMenuItem(item2, (int) (10 * Math.random() + 1));
			orderMgr.getOrder(i).addMenuItem(item3, (int) (10 * Math.random() + 1));
			orderMgr.getOrder(i).addMenuItem(item4, (int) (10 * Math.random() + 1));
		}
	}

}
