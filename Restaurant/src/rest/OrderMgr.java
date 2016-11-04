package rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Map.Entry;

public class OrderMgr {

	private List<Order> orderList;
	Scanner in = new Scanner(System.in);
	
	public OrderMgr(){
		orderList = new ArrayList<Order>();
	}
	
	public void createOrder(int orderId){
		Order newOrder = new Order(orderId);
//		System.out.println("Please enter Table Id");
//		int tableId = in.nextInt();
//		newOrder.setTableId(tableId);
//		//add orders to table
		orderList.add(newOrder);
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
//		if(al.next() == null)
//			System.out.println("Not found!");
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
	
	public Order getOrder(int orderId) {
		int index = searchList(orderId);
		return orderList.get(index);
	}
	
	public void print(int orderId){
		System.out.printf("====================");
		System.out.format("%n"+"%20s", "REstaurant");
		System.out.format("%n%n");
		System.out.format("%n%10s", "Order Id: " + orderId);
		System.out.format("%n%20s",getOrder(orderId).getdateTime()); //Ask Weijian for code to print out date of system
		System.out.format("%nWaiter: %12s", getOrder(orderId).getStaff());
		System.out.format("%nTable Id: %10d", getOrder(orderId).getTableId());
		System.out.format("%n%n");
		System.out.printf("--------------------");
		
		for (OrderLineItem i : getOrder(orderId).getOrders()) {
			System.out.println(i.getQuantity() + " " + i.getMenuItem().getName() + ": " + i.getMenuItem().getPrice()*i.getQuantity());
		}
		
		System.out.println("Total price: " + getOrder(orderId).callBill());
		
		
//		for (Entry<MenuItem, Integer> entry : getOrder(orderId).getOrders().getOrder().entrySet()) {
//			System.out.format("%s", entry.getValue());
//			System.out.printf("%s", entry.getKey().getName());
//			System.out.printf("%s", (entry.getKey().getPrice())*entry.getValue() + "%n");
//		}
		System.out.println("--------------------");
		//orderList.remove(index);
	}
}
