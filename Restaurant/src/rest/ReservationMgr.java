package rest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class ReservationMgr {
	
	private List<Reservation> resList;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	Scanner in = new Scanner(System.in);
	
	public ReservationMgr(){
		loadReservations();
	}
	
	private void loadReservations() {
		List list;
		resList = new ArrayList<Reservation>();
		try {
			list = (ArrayList) SerializeDB.readSerializedObject("reservationlist.dat");
			for (int i = 0; i < list.size(); i++) {
				Reservation m = (Reservation) list.get(i);
				resList.add(m);
			}
		} catch (Exception e) {
			System.out.println("Exception >> " + e.getMessage());
		}
	}
	
	private void saveReservations() {
		SerializeDB.writeSerializedObject("reservationlist.dat", resList);
	}
	
	public List<Reservation> addReservation() {
		updateRes(); //to clear any expired reservations first
		String dateTime;
		LocalDateTime t;
		boolean AM = false;
		int pax = 11, tableId;
		
		while (pax>10 || pax<1) { //getting correct pax number
			System.out.println("Please enter number of pax:");
			pax = in.nextInt();
			if (pax<=10 && pax>=1) break;
			System.out.println("Max capacity is 10. Try again.");
		}
		in.nextLine(); //for clearing buffer
		
		while (true) { //getting correct date
			System.out.println("Enter date and time of reservation (YYYY-MM-DD HH:MM): ");
			dateTime = in.nextLine();
			t = LocalDateTime.parse(dateTime, formatter);
			if (t.isAfter(LocalDateTime.now().plusMonths(1))) {
				System.out.println("Reservations cannot be made more than one month in advance. Try again.");
			}
			else if (t.isBefore(LocalDateTime.now())) {
				System.out.println("You are trying to reserve a time in the past. Try again.");
			}
			else break;
			
		}

		int a = t.getHour();
		while (!(a >= 11 && a < 15) || (a >= 18 && a < 22)) { //getting correct time
			System.out.println("Opening hours are 1100-1500 and 1800-2200.");
			System.out.println("Please enter new timing: ");
			String time = in.nextLine();
			String[] separated = time.split(":");
			a = Integer.parseInt(separated[0]);
			t.withHour(a);
			t.withMinute(Integer.parseInt(separated[1]));
		}
		
		if (a<16) AM = true;
		tableId = checkAvailability(pax, t, AM); // checkAvailability returns tableId if available table can be found
		if (tableId != -1) {
			System.out.println("Please enter cust contact:");
			long custCont = in.nextLong();
			Reservation res = new Reservation(pax, t, AM, tableId, custCont);
			resList.add(res);
			saveReservations();
			System.out.println("Reservation successfully added for "+pax+" people on "+t.format(formatter)+" at table "+tableId+"!");
			return resList;
		}
		else {
			System.out.println("No vacancies, sorry.");
			return null;
		}
		
		
		//PREVIOUS CODE
		/*
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
		*/
		//from this line onwards, means that the reservation is ok
//		temp.setAM(AM);
//		temp.setPM(PM);
//		temp.setPax(pax);													// sets pax for reservation
//		tableIndex = RestaurantApp.tableMgr.nextAvailableTable(temp.getPax());						//finds index of available table in tableList
//		temp.setTable(RestaurantApp.tableMgr.getTableList().get(tableIndex).getTableId());		//adds TableId to reservation
//		RestaurantApp.tableMgr.getTableList().get(tableIndex).setStatus(false);				//Sets Table as reserved
//		System.out.println("Please enter cust contact:");
//		temp.setCustContact(in.nextLong());
//		System.out.println("Reservation successfully added!");
//		resList.add(temp);
//		return resList;
	}
	
	
	public void removeReservation(){
		System.out.println("Please enter contact no:");
		long contno = in.nextLong();
		int index = searchResList(contno);
		if (index == -1) {
			System.out.println("Error - reservation not found.");
			return;
		}
		else {
			resList.remove(index);
			saveReservations();
			System.out.println("Reservation successfully removed!");
		}
	}
	
	public void checkReservation(){
		updateRes();
		if (resList.size() == 0) {
			System.out.println("No reservations have been made yet.");
			return;
		}
		System.out.println("Please enter contact no:");
		long contno = in.nextLong();
		int index = searchResList(contno);
		if (index == -1) {
			System.out.println("Error - reservation not found.");
			return;
		}
		else {
			Reservation temp = resList.get(index);
			System.out.println("Your reservation is on the " + temp.getDateTime());
			System.out.println("Reserved for " + temp.getPax() + " people");
			System.out.println("Table no: " + temp.getTable());
		}
	}
	
	public int searchResList(long contno) {
		for (Reservation r : resList) {
			if (r.getCustContact() == contno) {
				return resList.indexOf(r);
			}
		}
		return -1;
	}
	
//	public int searchTableList(int pax){
//		Iterator<Table> al = RestaurantApp.tableMgr.getTableList().listIterator();
//		int noTables = 0;
//		while (al.hasNext()){
//			Table n = al.next();
//			if (n.getCapacity()>=pax)
//				noTables++;
//		}
//		return noTables;
//	}
	
//	public int searchResListWithDateTime(Reservation temp){
//		int noRes = 0;
//		int hour = temp.getHour();
//	
//		for (Reservation n : resList) {	
//			if(n.getPax() >= temp.getPax()){
//				if(n.getMonth() == temp.getMonth())
//					if(n.getDay() == temp.getDay()){
//						if (11 <= hour && hour < 15){
//							if(n.getAM() == true)
//								{noRes++;}}
//							else if (18 <= hour && hour < 22)
//								if (n.getPM() == true)
//									noRes++;
//				}
//			}
//		}
//		return noRes;
//	}
	
	public int checkAvailability(int pax, LocalDateTime t, boolean AM) { //returns tableId if available
		List<Table> tempTableList = RestaurantApp.tableMgr.getTableList();
		for (Table table : tempTableList) {
			table.setStatus(false);
		}
		for (Reservation r : resList) {
			if (r.getLocalDateTime().truncatedTo(ChronoUnit.DAYS) == t.truncatedTo(ChronoUnit.DAYS) && r.getAM() == AM) {
				for (Table table : tempTableList) {
					if (table.getTableId() == r.getTable()) {
						table.setStatus(true);
						break;
					}
				}
			}
		}
		for (Table table : tempTableList) {
			if (!table.getStatus() && table.getCapacity() >= pax) {
				return table.getTableId();
			}
		}
		return -1;
	}
	
	public void updateRes() {
		for (Reservation r : resList) {
			if (r.getLocalDateTime().isBefore(LocalDateTime.now().plusMinutes(30))) {
				resList.remove(r);
				System.out.println("Reservation by customer with contact number " + r.getCustContact() + " has been automatically removed");
			}
		}
	}
}
