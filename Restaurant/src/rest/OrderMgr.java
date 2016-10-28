package rest;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Map.Entry;

public class OrderMgr {
	
	private List<Order> orderList;
	Scanner in = new Scanner(System.in);
	
	public void createOrder(){
		Order newOrder = new Order();
		int choice = 0;
		while(choice != 3){
			editmenu();
			choice = in.nextInt();
			switch(choice){
			case 1:
//				newOrder.addMenuItem();
				break;
			case 2:
//				newOrder.removeMenuItem();
				break;
			case 3:
				System.out.println("Changes Saved!");
				break;
			}
		}
		orderList.add(newOrder);
		save();
	}
	
	public void editOrder(){
		System.out.println("Please enter Order Id:");
		int orderId = in.nextInt();
		int index = searchList(orderId);
		int choice = 0;
		while(choice != 3){
			editmenu();
			choice = in.nextInt();
			switch(choice){
			case 1:
//				orderList.get(index).addMenuItem();
				break;
			case 2:
//				orderList.get(index).removeMenuItem();
				break;
			case 3:
				System.out.println("Changes Saved!");
				break;
			}
		}
		save();
	}
	public void removeOrder(){
		System.out.println("Please enter Order Id:");
		int orderId = in.nextInt();
		int index = searchList(orderId);
		orderList.remove(index);
		System.out.println("Order successfully removed!");
		save();
	}
	public void printInvoice(){
		System.out.println("Enter Order ID:");
		int orderId = in.nextInt();
		int index = searchList(orderId);
		print(index);
		save();
	}
	public void printReport(){
		save();
		//print from txt file
	}
	public int searchList(int orderId) {
		Iterator<Order> al = orderList.listIterator();
		Order n = al.next();
		while (al.hasNext() || n.getOrderId() != orderId) {
			n = al.next();
		}
		return orderList.indexOf(n);
	}
	public List<Order> getOrderList() {
		return orderList;
	}
	public void load(){
		//load txt file
	}
	public void save(){
		//save into txt file
	}
	public void print(int index){
		System.out.printf("====================");
		System.out.format("%n"+"%20s", "REstaurant");
		System.out.format("%n%n");
		System.out.format("%n%10s", "Order Id: " + orderList.get(index).getOrderId());
		System.out.format("%n%20d",orderList.get(index).getdateTime()); //Ask Weijian for code to print out date of system
		System.out.format("%nWaiter: %12s", orderList.get(index).getStaff());
		System.out.format("%nTable Id: %10d", orderList.get(index).getTableId());
		System.out.format("%n%n");
		System.out.printf("--------------------");
		for (Entry<MenuItem, Integer> entry : orderList.get(index).getOrders().getOrder().entrySet()) {
			System.out.format("%s", entry.getValue());
			System.out.printf("%s", entry.getKey().getName());
			System.out.printf("%d", (entry.getKey().getPrice())*entry.getValue() + "%n");
		}
		System.out.println("--------------------");
		orderList.remove(index);
	}
	public void editmenu(){
		System.out.println("Please enter your choice:");
		System.out.println("1. Add item");
		System.out.println("2. Remove item");
		System.out.println("3. Confirm changes");
	}
	
}
