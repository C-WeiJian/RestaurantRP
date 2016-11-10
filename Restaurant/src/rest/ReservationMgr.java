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
	
	public void addReservation() {
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
		while (a<11 || a>21 || (a>14 && a<18)) { //getting correct time
			System.out.println("Opening hours are 1100-1500 and 1800-2200.");
			System.out.println("Please enter new timing (HH:MM): ");
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
			Reservation res = new Reservation(pax, t.format(formatter), AM, tableId, custCont);
			resList.add(res);
			saveReservations();
			System.out.println("Reservation successfully added for "+pax+" people on "+t.format(formatter)+" at table "+tableId+"!");
		}
		else {
			System.out.println("No vacancies, sorry.");
		}
	}
	
	
	public void removeReservation(){
		updateRes();
		if (resList.size() == 0) {
			System.out.println("No reservations have been made yet.");
			return;
		}
		System.out.println("Please enter contact no:");
		long contno = in.nextLong();
		ArrayList<Integer> indexList = new ArrayList<Integer>();
		indexList = searchResList(contno);
		if (indexList.size() == 0) {
			System.out.println("Error - reservation not found.");
			return;
		}
		else {
			if (indexList.size() > 1) {
				System.out.println("You have "+indexList.size()+" reservations.");
				int count = 1;
				for (Integer i : indexList) {
					Reservation temp = resList.get(i);
					System.out.println("Reservation "+count+":");
					System.out.println("Time of reservation: " + temp.getDateTime());
					System.out.println("Reserved for: " + temp.getPax() + " people");
					System.out.println("Table no: " + temp.getTableId());
					System.out.println("--------------------------");
					count++;
				}
				System.out.println("Which reservation do you want to remove?");
				int choice = in.nextInt();
				int index = indexList.get(choice-1);
				resList.remove(index);
				saveReservations();
				System.out.println("Reservation successfully removed!");
			}
			else {
				int index = indexList.get(0);
				resList.remove(index);
				saveReservations();
				System.out.println("Reservation successfully removed!");
			}
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
		ArrayList<Integer> indexList = searchResList(contno);
		if (indexList.size() == 0) {
			System.out.println("Error - reservation not found.");
			return;
		}
		else {
			System.out.println("You have "+indexList.size()+" reservation(s):");
			for (Integer i : indexList) {
				Reservation temp = resList.get(i);
				System.out.println("Time of reservation: " + temp.getDateTime());
				System.out.println("Reserved for: " + temp.getPax() + " people");
				System.out.println("Table no: " + temp.getTableId());
				System.out.println("--------------------------");
			}
		}
	}
	
	public ArrayList<Integer> searchResList(long contno) { //returns ArrayList of reservation indices in case the same person has more than one reservation
		ArrayList<Integer> indexList = new ArrayList<Integer>();
		for (Reservation r : resList) {
			if (r.getCustContact() == contno) {
				indexList.add(resList.indexOf(r));
			}
		}
		return indexList;
	}
	
	public int checkAvailability(int pax, LocalDateTime t, boolean AM) { //returns tableId if available
		List<Table> tempTableList = RestaurantApp.tableMgr.getTableList();
		LocalDateTime now = LocalDateTime.now();
		boolean AMnow;
		if (now.getHour() < 12) AMnow = true;
		else AMnow = false;
		
		if (t.truncatedTo(ChronoUnit.DAYS).isAfter(now) || AM!=AMnow) { //if reservation is not for the current session
			for (Table table : tempTableList) {
				table.setStatus(true);
			}
		}
		
		for (Reservation r : resList) { //set status of tables correctly based on reservations
			if (LocalDateTime.parse(r.getDateTime(),formatter).truncatedTo(ChronoUnit.DAYS).equals(t.truncatedTo(ChronoUnit.DAYS)) && r.getAM() == AM) {
				for (Table table : tempTableList) {
					if (table.getTableId() == r.getTableId()) {
						table.setStatus(false);
						break;
					}
				}
			}
		}
		
		for (Table table : tempTableList) {
			if (table.getStatus() && table.getCapacity() >= pax) {
				return table.getTableId();
			}
		}
		return -1;
	}
	
	public void updateRes() {
		LocalDateTime now = LocalDateTime.now();		
		for (Reservation r : resList) {
			if (LocalDateTime.parse(r.getDateTime(),formatter).isBefore(now.minusMinutes(30))) {
				resList.remove(r);
				System.out.println("Reservation by customer with contact number " + r.getCustContact() + " has been automatically removed");
			}
		}
		saveReservations();
	}
	
	public ArrayList<Integer> getUnavailableTables() { //returns tableIds of reserved tables for the session
		ArrayList<Integer> reserved = new ArrayList<Integer>();
		LocalDateTime now = LocalDateTime.now();
		boolean AM;
		if (now.getHour() < 12) AM = true;
		else AM = false;
		for (Reservation r : resList) {
			if (LocalDateTime.parse(r.getDateTime(),formatter).truncatedTo(ChronoUnit.DAYS).equals(now.truncatedTo(ChronoUnit.DAYS)) && r.getAM() == AM) {
				reserved.add(r.getTableId());
			}
		}
		return reserved;
	}
	
	public List<Reservation> getReservations() {
		return this.resList;
	}
	
	public void resetRes(){
		resList = new ArrayList<Reservation>();
	}
}
