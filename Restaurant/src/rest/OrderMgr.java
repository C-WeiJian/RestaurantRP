package rest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

// TODO: Auto-generated Javadoc
/**
 * The Class OrderMgr. Manages all the orders made
 * 
 * @version 1.0
 * @since 2016-10-28
 */
public class OrderMgr {

	/** The list of Orders. */
	private List<Order> orderList;

	/** The list of staffs. */
	private List<Staff> staffList;

	/** Scanner to scan user input. */
	Scanner in = new Scanner(System.in);

	/** The formatter to format Date and Time. */
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // format
																							// //
																							// of
	// time

	/**
	 * Instantiates a new order manager. loads the saved data of Order and Staff
	 * details
	 */
	public OrderMgr() {
		loadOrder();
		loadStaff();
	}

	/**
	 * Loads order from orderlist.dat file to the program
	 */
	public void loadOrder() {
		List list;
		orderList = new ArrayList<Order>();
		try {
			list = (ArrayList) SerializeDB.readSerializedObject("orderlist.dat");
			for (int i = 0; i < list.size(); i++) {
				Order m = (Order) list.get(i);
				orderList.add(m);
			}
		} catch (Exception e) {
			System.out.println("Exception >> " + e.getMessage());
		}
	}

	/**
	 * Loads staff list from stafflist.dat file to the program
	 */
	private void loadStaff() {
		List list;
		staffList = new ArrayList<Staff>();
		try {
			// read from serialized file the list of professors
			list = (ArrayList) SerializeDB.readSerializedObject("stafflist.dat");
			for (int i = 0; i < list.size(); i++) {
				Staff m = (Staff) list.get(i);
				staffList.add(m);
			}
		} catch (Exception e) {
			System.out.println("Exception >> " + e.getMessage());
		}
	}

	/**
	 * Save order lists as orderlist.dat file
	 */
	public void saveOrder() {
		SerializeDB.writeSerializedObject("orderlist.dat", orderList);
	}

	/**
	 * Save staff lists as stafflist.dat file
	 */
	private void saveStaff() {
		SerializeDB.writeSerializedObject("stafflist.dat", staffList);
	}

	/**
	 * Preset stafflist
	 */
	public void presetStaff() {
		staffList = new ArrayList<Staff>();
		staffList.add(new Staff(1, "Hillary Clinton", "Waiter", 'F'));
		staffList.add(new Staff(2, "George Bush", "Waiter", 'M'));
		staffList.add(new Staff(3, "Donald Trump", "The Greatest Waiter", 'M'));
		staffList.add(new Staff(4, "Barack Obama", "Assistant Manager", 'M'));
		staffList.add(new Staff(5, "Michelle Obama", "Manager", 'F'));
		saveStaff();
	}

	/**
	 * Menu for creating a new order Saves the Order to the orderlist.dat file
	 * when it is created
	 *
	 * @param orderId
	 *            the order id
	 * @param staffId
	 *            the staff id of the server
	 * @param tableId
	 *            the table id
	 * @return true, if successful
	 */
	public boolean createOrder(int orderId, int staffId, int tableId) {
		Staff staff = getStaff(staffId);
		if (staff == null) {
			System.out.println("ERROR - Invalid Staff Id");
			return false;
		}
		int check = searchList(orderId);
		if (check != -1) {
			System.out.println("ERROR - Duplicate Order Id");
			return false;
		}
		Order newOrder = new Order(orderId, staff, tableId);
		orderList.add(newOrder);
		saveOrder();
		return true;
	}

	/**
	 * Removes the order. Removes the order and saves the change to
	 * orderlist.datfile
	 *
	 * @param order
	 *            the order
	 */
	public void removeOrder(Order order) {
		int index = searchList(order.getOrderId());
		orderList.remove(index);
		System.out.println("Order successfully removed!");
		saveOrder();
	}

	/**
	 * Prints the order invoice.
	 *
	 * @param order
	 *            the order
	 */
	public void printInvoice(Order order) {
		print(order);
		removeOrder(order);
		saveOrder();
	}

	/**
	 * Prints the report.
	 */
	public void printReport() {
		// print from txt file
	}

	/**
	 * Search method to search for the order based on orderId.
	 *
	 * @param orderId
	 *            the order id
	 * @return the int
	 */
	public int searchList(int orderId) {
		Iterator<Order> al = orderList.listIterator();
		while (al.hasNext()) {
			Order n = al.next();
			if (n.getOrderId() == orderId)
				return orderList.indexOf(n);
		}
		return -1;
	}

	/**
	 * Gets the staff. Iterates through the stafflist to search for the staff
	 * based on their id
	 *
	 * @param staffId
	 *            the staff id
	 * @return the staff
	 */
	public Staff getStaff(int staffId) {
		Iterator<Staff> al = staffList.listIterator();
		while (al.hasNext()) {
			Staff n = al.next();
			if (n.getStaffId() == staffId)
				return n;
		}
		return null;
	}

	/**
	 * Gets the order list.
	 *
	 * @return the order list
	 */
	public List<Order> getOrderList() {
		return orderList;
	}

	/**
	 * Gets the order.
	 *
	 * @param orderId
	 *            the order id
	 * @return the order
	 */
	public Order getOrder(int orderId) {
		int index = searchList(orderId);
		if (index == -1)
			return null;
		return orderList.get(index);
	}

	/**
	 * Prints the order invoice.
	 *
	 * @param order
	 *            the order
	 */
	public void print(Order order) {
		System.out.printf("===============================\n");
		System.out.printf("%21s\n", "REstaurant");
		System.out.printf("===============================\n");
		System.out.printf("\n");
		System.out.printf("%21s\n", "Order Id: " + order.getOrderId());
		System.out.printf("%21s\n", "Table Id: " + order.getTableId());
		System.out.format("%24s", getdateTime(order));
		System.out.printf("\n\n");
		System.out.printf("%21s\n", "Served By: " + order.getStaff().getName());
		System.out.printf("-------------------------------\n");
		for (OrderLineItem i : order.getOrders()) {
			if (i.getMenuItem().isPromo() == false) {
				System.out.printf("%2d %-21s: %.2f", i.getQuantity(), i.getMenuItem().getName(),
						i.getMenuItem().getPrice() * i.getQuantity());
				System.out.printf("\n");
			} else {
				int n = 1;
				System.out.printf("%2d %-21s: %.2f\n", i.getQuantity(), i.getMenuItem().getName(),
						i.getMenuItem().getPrice() * i.getQuantity());
				for (MenuItem m : ((PromoPackage) i.getMenuItem()).getpItems()) {
					System.out.printf("%4s (%d) %-20s", " ", n, m.getName());
					System.out.printf("\n");
					n++;
				}

			}

		}
		System.out.printf("-------------------------------\n");
		System.out.printf("   %-21s: %.2f\n", "Sub Total ", order.getSubtotal());
		System.out.printf("   %-21s: %.2f\n", "10% Service Charge ", order.getSvcharge());
		System.out.printf("   %-21s: %.2f\n", "7% GST ", order.getGst());
		System.out.printf("   %-21s: %.2f\n", "Total ", order.getTotal());
		System.out.printf("-------------------------------\n");

	}

	/**
	 * Gets the date time the order was made.
	 *
	 * @param order
	 *            the order
	 * @return the date time
	 */
	public String getdateTime(Order order) {
		return order.getdateTime().format(formatter);
	}

	/**
	 * Set the date time the order was made.
	 *
	 * @param order
	 *            the order
	 * @param time
	 *            the time
	 */
	public void setdateTime(Order order, String time) {
		order.setdateTime(LocalDateTime.parse(time, formatter));
	}

	/**
	 * Gets the unavailable tables.
	 *
	 * @return the unavailable tables
	 */
	public ArrayList<Integer[]> getUnavailableTables() {
		ArrayList<Integer[]> occupied = new ArrayList<Integer[]>();
		for (Order o : orderList) {
			Integer[] pair = new Integer[2];
			pair[0] = o.getTableId();
			pair[1] = o.getOrderId();
			occupied.add(pair);
		}
		return occupied;
	}

	/**
	 * Reset all order. Creates a new empty orderlist.dat file and replaces the
	 * existing one
	 */
	public void resetallOrder() {
		orderList = new ArrayList<Order>();
		saveOrder();
	}

	public Order quicksetOrder(MenuItem item1, MenuItem item2, MenuItem item3, MenuItem item4, int i) {
		Order temp = new Order(i, staffList.get(2), 30, LocalDateTime.parse("2016-01-01 12:00", formatter).withDayOfYear(i));
		temp.addMenuItem(item1, (int) (10 * Math.random() + 1));
		temp.addMenuItem(item2, (int) (10 * Math.random() + 1));
		temp.addMenuItem(item3, (int) (10 * Math.random() + 1));
		temp.addMenuItem(item4, (int) (10 * Math.random() + 1));
		return temp;
	}

}
