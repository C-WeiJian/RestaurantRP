package rest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class OrderMgr {

	private List<Order> orderList;
	private List<Staff> staffList;
	Scanner in = new Scanner(System.in);
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // format // of
	// time

	public OrderMgr() {
		loadOrder();
		loadStaff();
	}

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

	public void saveOrder() {
		SerializeDB.writeSerializedObject("orderlist.dat", orderList);
	}

	private void saveStaff() {
		SerializeDB.writeSerializedObject("stafflist.dat", staffList);
	}

	public void presetStaff() {
		staffList = new ArrayList<Staff>();
		staffList.add(new Staff(1, "Steve Jobs", "Waiter", 'M'));
		staffList.add(new Staff(2, "Bill Gates", "Waiter", 'M'));
		staffList.add(new Staff(3, "Susan Wojcicki", "Chief Waiter", 'F'));
		staffList.add(new Staff(4, "Barack Obama", "Assistant Manager", 'M'));
		staffList.add(new Staff(5, "Michelle Obama", "Manager", 'F'));
		saveStaff();
	}

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

	public void removeOrder(Order order) {
		int index = searchList(order.getOrderId());
		orderList.remove(index);
		System.out.println("Order successfully removed!");
		saveOrder();
	}

	public void printInvoice(Order order) {
		print(order);
		removeOrder(order);
		saveOrder();
	}

	public void printReport() {
		// print from txt file
	}

	public int searchList(int orderId) {
		Iterator<Order> al = orderList.listIterator();
		while (al.hasNext()) {
			Order n = al.next();
			if (n.getOrderId() == orderId)
				return orderList.indexOf(n);
		}
		return -1;
	}

	public Staff getStaff(int staffId) {
		Iterator<Staff> al = staffList.listIterator();
		while (al.hasNext()) {
			Staff n = al.next();
			if (n.getStaffId() == staffId)
				return n;
		}
		return null;
	}

	public List<Order> getOrderList() {
		return orderList;
	}

	public Order getOrder(int orderId) {
		int index = searchList(orderId);
		if (index == -1)
			return null;
		return orderList.get(index);
	}

	public void print(Order order) { // need to clean up the printing method
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

	public String getdateTime(Order order) {
		return order.getdateTime().format(formatter);
	}

	public void setdateTime(Order order, String time) {
		order.setdateTime(LocalDateTime.parse(time, formatter));
	}

	public ArrayList<Integer> getUnavailableTables() {
		ArrayList<Integer> templist = new ArrayList<Integer>();
		for (Order o : orderList) {
			templist.add(o.getTableId());
		}
		return templist;
	}
	
	public void resetallOrder() {
		orderList = new ArrayList<Order>();
		saveOrder();
	}

}
