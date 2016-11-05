package rest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Map.Entry;

public class OrderMgr {

	private List<Order> orderList = new ArrayList<Order>();
	Scanner in = new Scanner(System.in);
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); //format of time
	
	public OrderMgr(){
		loadOrder();
		//orderList = new ArrayList<Order>();
	}
	
	public void loadOrder(){
		List list;
		orderList = new ArrayList<Order>();
		try	{
			// read from serialized file the list of professors
			list = (ArrayList)SerializeDB.readSerializedObject("orderlist.dat");
			for (int i = 0 ; i < list.size() ; i++) {
				Order m = (Order)list.get(i);
				orderList.add(m);
			}
		} catch ( Exception e ) {
			System.out.println( "Exception >> " + e.getMessage() );
		}
	}
	
	public void saveOrder(){
		SerializeDB.writeSerializedObject("orderlist.dat", orderList);
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
	
/*	public void printInvoice(){
		System.out.println("Enter Order Id:");
		int orderId = in.nextInt();
		int index = searchList(orderId);
		print(index);
		save();
	}*/
	
	public void printReport(){
		save();
		//print from txt file
	}
	public int searchList(int orderId) {
		Iterator<Order> al = orderList.listIterator();
		while (al.hasNext()) {
			Order n = al.next();
			if(n.getOrderId() == orderId)
				return orderList.indexOf(n);
		}
		System.out.println("Order not found!");
		return -1;
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
	
	public void print(Order order){ //need to clean up the printing method
		System.out.printf("====================");
		System.out.format("%n"+"%20s", "REstaurant");
		System.out.format("%n%n");
		System.out.format("%n%10s", "Order Id: " + order.getOrderId());
		System.out.format("%n%20s",order.getdateTime()); 
//		System.out.format("%nWaiter: %12s", order.getStaff());
//		System.out.format("%nTable Id: %10d", order.getTableId());
		System.out.format("%n%n");
		System.out.printf("--------------------");
		
		for (OrderLineItem i : order.getOrders()) {
			System.out.println(i.getQuantity() + " " + i.getMenuItem().getName() + ": " + i.getMenuItem().getPrice()*i.getQuantity());
		}
		
		System.out.println("Total price: " + order.callBill());
		
		
//		for (Entry<MenuItem, Integer> entry : getOrder(orderId).getOrders().getOrder().entrySet()) {
//			System.out.format("%s", entry.getValue());
//			System.out.printf("%s", entry.getKey().getName());
//			System.out.printf("%s", (entry.getKey().getPrice())*entry.getValue() + "%n");
//		}
		System.out.println("--------------------");
		//orderList.remove(index);
	}
	
/*	public void print(int orderId){
		System.out.printf("====================");
		System.out.format("%n"+"%20s", "REstaurant");
		System.out.format("%n%n");
		System.out.format("%n%10s", "Order Id: " + orderId);
		System.out.format("%n%20s",getOrder(orderId).getdateTime()); 
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
	}*/
	
	public String getdateTime(Order order) {
		return order.getdateTime().format(formatter);
	}
	
	public void setdateTime(Order order, String time) {
		order.setdateTime(LocalDateTime.parse(time, formatter));
	}
	
}
