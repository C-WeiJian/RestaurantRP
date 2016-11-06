package rest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class ReservationMgr {
	
	private List<Reservation> resList;
	Scanner in = new Scanner(System.in);
	
	public ReservationMgr(){
		resList = new ArrayList<Reservation>();
	}
	
	public List<Reservation> addReservation() {
		updateRes();
		String dateTime;
		Reservation temp = new Reservation();
		boolean AM = false, PM = false, month = true;
		int tableIndex, pax, count = 0, tables, noRes;
		System.out.println("Please enter number of pax:");
		pax = in.nextInt();
		tables = searchTableList(pax);
		while (count == 0) {
			while(AM == false && PM == false) {
				while(month == false) {
					System.out.println("Please enter date and time of reservation: (yyyy-mm-dd HH:mm)");
					dateTime = in.next();
					temp.setDateTime(dateTime);
					month = LocalDateTime.parse(dateTime).isAfter(LocalDateTime.now().plusMonths(1));	//true means reservation not valid
					if (month = true){		//checks 1 month condition
						System.out.println("You cannot reserve a date later than one month from now!");
						break;
					}
				} 
				AM = temp.amSession(temp.getLocalDateTime());
				PM = temp.pmSession(temp.getLocalDateTime());
				if (AM == false && PM == false){		//checks opening hours
					System.out.println("Restaurant opening hours: 1100 - 1500 AND 1800 - 2200"); 
					break;
				}
			}
			noRes = searchResListWithDateTime(temp);
			count = tables - noRes;
			if(count == 0) {											// checks if there are available tables
				System.out.println("No Reservation available!");
				System.out.println("Press any key to continue or press -1 to exit reservation booking");
				if (in.nextInt() == -1)
					return resList;
			}
		}
		//from this line onwards, means that the reservation is ok
		temp.setAM(AM);
		temp.setPM(PM);
		temp.setPax(pax);													// sets pax for reservation
		tableIndex = RestaurantApp.tableMgr.nextAvailableTable(temp.getPax());						//finds index of available table in tableList
		temp.setTable(RestaurantApp.tableMgr.getTableList().get(tableIndex).getTableId());		//adds TableId to reservation
		RestaurantApp.tableMgr.getTableList().get(tableIndex).setStatus(false);				//Sets Table as reserved
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
		if (searchResList(contno) == -1)
			return;
		System.out.println("Reservation successfully removed!");
	}
	public void checkReservation(){
		updateRes();
		System.out.println("Please enter contact no:");
		long contno = in.nextLong();
		Reservation temp = resList.get(searchResList(contno));
		if (temp == null)
			return;
		System.out.println("Your reservation is on the " + temp.getDateTime());
		System.out.println("Reserved for " + temp.getPax() + " people");
		System.out.println("Table no: " + temp.getTable());
	}
	public int searchResList(long contno) {
		Iterator<Reservation> al = resList.listIterator();
		Reservation n = al.next();
		while (al.hasNext() || n.getCustContact() != contno) {
			n = al.next();
		}
		if(al.next() == null){
			System.out.println("Not found!");
			return -1;
			}
		return resList.indexOf(n);
	}
	public int searchTableList(int pax){
		Iterator<Table> al = RestaurantApp.tableMgr.getTableList().listIterator();
		int noTables = 0;
		while (al.hasNext()){
			Table n = al.next();
			if (n.getCapacity()>=pax)
				noTables++;
		}
		return noTables;
	}
	public int searchResListWithDateTime(Reservation temp){
		int noRes = 0;
		int hour = temp.getHour();
	
		for (Reservation n : resList) {	
			if(n.getPax() >= temp.getPax()){
				if(n.getMonth() == temp.getMonth())
					if(n.getDay() == temp.getDay()){
						if (11 <= hour && hour < 15){
							if(n.getAM() == true)
								{noRes++;}}
							else if (18 <= hour && hour < 22)
								if (n.getPM() == true)
									noRes++;
				}
			}
		}
		return noRes;
	}
	
	public void addResIfAvailable() {
		List<Table> tempTableList = RestaurantApp.tableMgr.getTableList();
		for (Reservation r : resList) {
			tempTableList.searchList
		}
	}
	
	public void updateRes() {
		for (Reservation r : resList) {
			LocalDateTime time = LocalDateTime.now();
			time.plusMinutes(30);
			if (LocalDateTime.parse(r.getDateTime()).isBefore(time)) {
				resList.remove(r);
				System.out.println("Reservation by customer with contact number " + r.getCustContact() + " has been automatically removed");
			}
		}
	}
}
