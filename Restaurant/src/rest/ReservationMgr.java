package rest;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class ReservationMgr {
	public ReservationMgr(){
	}
	private List<Reservation> resList;
	Scanner in = new Scanner(System.in);
	
	public List<Reservation> addReservation(){
		String dateTime;
		Reservation temp = null;
		int hour = 0, tableIndex, noPax;
		while(hour <= 0 && hour > 24){
		System.out.println("Please enter date and time of reservation: (yyyy-MM-dd HH:mm)");
		dateTime = in.next();
		
		temp.setdateTime(dateTime);
		hour = temp.getHour();	
		}
		if(hour < 12)
			temp.setAM(true);
		else
			temp.setPM(true);
		System.out.println("Please enter number of pax:");
		noPax = in.nextInt();
		temp.setPax(noPax);													// sets pax for reservation
		tableIndex = RestaurantApp.tableMgr.searchListWithPax(temp.getPax());						//finds index of available table in tableList
		temp.setTable(RestaurantApp.tableMgr.getTableList().get(tableIndex).getTableID());		//adds TableId to reservation
		RestaurantApp.tableMgr.getTableList().get(tableIndex).setStatus(false);				//Sets Table as reserved
		RestaurantApp.tableMgr.getTableList().get(tableIndex).setPax(noPax);					//Sets no of pax for table
		System.out.println("Please enter cust contact:");
		temp.setCustContact(in.nextLong());
		System.out.println("Reservation successfully added!");
		resList.add(temp);
		return resList;
	}
	
	public void removeReservation(){
		System.out.println("Please enter contact no:");
		long contno = in.nextLong();
		resList.remove(searchResList(contno));
		System.out.println("Reservation successfully removed!");
	}
	public void checkReservation(){
		System.out.println("Please enter contact no:");
		long contno = in.nextLong();
		
	}
	public int searchResList(long contno) {
		Iterator<Reservation> al = resList.listIterator();
		Reservation n = al.next();
		while (al.hasNext() || n.getCustContact() != contno) {
			n = al.next();
		}
		if(al.next() == null)
			System.out.println("Not found!");
		return resList.indexOf(n);
	}
}
