//This is the main app for running the programme

package rest;

import java.util.Scanner;

public class RestaurantApp {

	public static Menu menu = new Menu();
	public static OrderMgr orderMgr = new OrderMgr();
	public static TableMgr tableMgr = new TableMgr();
	public static ReservationMgr resMgr = new ReservationMgr();
	static int choice = -1;
	static int choice2 = -1;
	static String input;
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		menu.loadMenu();

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
			System.out.println("11. Print sales revenue report by period (day/month)");
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
				createOrder();
				break;
			case 5:
				resMgr.updateRes();
				viewOrder();
				break;
			case 6:
				resMgr.updateRes();
				editOrder();
				break;
			case 7:
				resMgr.checkReservation();
				//updateRes() is called within checkReservation()
				break;
			case 8:
				resMgr.addReservation();
				// updateRes() is called within addReservation()
				break;
			case 9:
				tableMgr.showTable(); // let status of table check the presence
										// of order
				break;
			case 10:
				printInvoice();
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
		if (order != null)
			orderMgr.printInvoice(order);
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
				System.out.println("3. Delete/Remove Order");
				System.out.println("4. Back");
				System.out.println("--------------------------");
				System.out.print("Enter choice: ");
				choice = sc.nextInt();
				sc.nextLine();

				switch (choice) {
				case 1:
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
				case 4:
					System.out.println("Please Wait...");
					orderMgr.saveOrder();
					continue;
				}

			} while (choice != 4);
		}
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

			switch (choice) {
			case 1:
				tableMgr.presetTable();
				orderMgr.presetStaff();
				menu.initDefaultMenu();
				menu.saveMenu();
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

}
