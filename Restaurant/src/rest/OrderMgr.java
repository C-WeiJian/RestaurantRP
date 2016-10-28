package rest;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

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
				newOrder.addMenuItem();
				break;
			case 2:
				newOrder.removeMenuItem();
				break;
			case 3:
				System.out.println("Changes Saved!");
				break;
			}
		}
		orderList.add(newOrder);
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
				orderList.get(index).addMenuItem();
				break;
			case 2:
				orderList.get(index).removeMenuItem();
				break;
			case 3:
				System.out.println("Changes Saved!");
				break;
			}
		}
	}
	public void removeOrder(){
		System.out.println("Please enter Order Id:");
		int orderId = in.nextInt();
		int index = searchList(orderId);
		orderList.remove(index);
		System.out.println("Order successfully removed!");
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
	public void editmenu(){
		System.out.println("Please enter your choice:");
		System.out.println("1. Add item");
		System.out.println("2. Remove item");
		System.out.println("3. Confirm changes");
	}
	
}
