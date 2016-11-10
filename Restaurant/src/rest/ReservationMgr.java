package rest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

// TODO: Auto-generated Javadoc
/**
 * The Class ReservationMgr.
 * @version 1.0
 * @since 2016-10-28
 */
public class ReservationMgr {
	
	/** The reservation list. */
	private List<Reservation> resList;
	
	/** The formatter for date and time. */
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	
	/** The scanner. */
	Scanner in = new Scanner(System.in);
	
	/**
	 * Instantiates a new reservation mgr.
	 */
	public ReservationMgr(){
		loadReservations();
	}
	
	/**
	 * Load reservations.
	 */
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
	
	/**
	 * Save reservations as a reservationlist.dat file.
	 */
	private void saveReservations() {
		SerializeDB.writeSerializedObject("reservationlist.dat", resList);
	}
	
	/**
	 * Adds the reservation.
	 * 
	 * @param temptablelist the list of tables
	 */
	public void addReservation(ArrayList<Table> temptablelist) {
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
		tableId = checkAvailability(pax, t, AM, temptablelist); // checkAvailability returns tableId if available table can be found
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
	
	
	/**
	 * Removes the reservation.
	 */
	public void removeReservation(){
		updateRes();
		if (resList.size() == 0) {
			System.out.println("No reservations have been made yet.");
			return;
		}
		System.out.println("Please enter contact no:");
		long contno = in.nextLong();
		ArrayList<Reservation> reservations = new ArrayList<Reservation>();
		reservations = searchResList(contno);
		if (reservations.size() == 0) {
			System.out.println("Error - reservation not found.");
			return;
		}
		else {
			if (reservations.size() > 1) {
				System.out.println("You have "+reservations.size()+" reservations.");
				int count = 1;
				for (Reservation r : reservations) {
					System.out.println("Reservation "+count+":");
					System.out.println("Time of reservation: " + r.getDateTime());
					System.out.println("Reserved for: " + r.getPax() + " people");
					System.out.println("Table no: " + r.getTableId());
					System.out.println("--------------------------");
					count++;
				}
				System.out.println("Which reservation do you want to remove? Key in 0 to remove all reservations.");
				int choice = in.nextInt();
				if (choice > 0) {
					Reservation toRemove = reservations.get(choice-1);
					resList.remove(toRemove);
					saveReservations();
					System.out.println("Reservation successfully removed!");
				}
				else if (choice == 0) {
					for (Reservation r : reservations) {
						resList.remove(r);
					}
					System.out.println("All your reservations have been removed!");
				}
			}
			else {
				Reservation toRemove = reservations.get(0);
				resList.remove(toRemove);
				saveReservations();
				System.out.println("Reservation successfully removed!");
			}
		}
	}
	
	/**
	 * Check reservation.
	 */
	public void checkReservation(){
		updateRes();
		if (resList.size() == 0) {
			System.out.println("No reservations have been made yet.");
			return;
		}
		System.out.println("Please enter contact no:");
		long contno = in.nextLong();
		ArrayList<Reservation> reservations = searchResList(contno);
		if (reservations.size() == 0) {
			System.out.println("Error - reservation not found.");
			return;
		}
		else {
			System.out.println("You have "+reservations.size()+" reservation(s):");
			int count = 1;
			for (Reservation r : reservations) {
				System.out.println("Reservation "+count+":");
				System.out.println("Time of reservation: " + r.getDateTime());
				System.out.println("Reserved for: " + r.getPax() + " people");
				System.out.println("Table no: " + r.getTableId());
				System.out.println("--------------------------");
				count++;
			}
		}
	}
	
	/**
	 * Search reservation list.
	 *
	 * @param contno the contact number
	 * @return the array list of reservation indices in resList
	 */
	public ArrayList<Reservation> searchResList(long contno) { //returns ArrayList of reservation indices in case the same person has more than one reservation
		ArrayList<Reservation> indexList = new ArrayList<Reservation>();
		for (Reservation r : resList) {
			if (r.getCustContact() == contno) {
				indexList.add(r);
			}
		}
		return indexList;
	}
	
	/**
	 * Check availability.
	 *
	 * @param pax the pax
	 * @param t the time
	 * @param AM the boolean identifying whether it is AM or PM
	 * @param tempTableList the list of table
	 * @return the tableID of available table
	 * -1 if no tables are available
	 */
	public int checkAvailability(int pax, LocalDateTime t, boolean AM, ArrayList<Table> tempTableList) { 
		//List<Table> tempTableList = templist;
		LocalDateTime now = LocalDateTime.now();
		boolean AMnow;
		if (now.getHour() < 12) AMnow = true;
		else AMnow = false;
		
		if (t.truncatedTo(ChronoUnit.DAYS).isAfter(now) || AM!=AMnow) { //if reservation is not for the current session
			for (Table table : tempTableList) {
				table.setStatus(1);
			}
		}
		
		for (Reservation r : resList) { //set status of tables correctly based on reservations
			if (LocalDateTime.parse(r.getDateTime(),formatter).truncatedTo(ChronoUnit.DAYS).equals(t.truncatedTo(ChronoUnit.DAYS)) && r.getAM() == AM) {
				for (Table table : tempTableList) {
					if (table.getTableId() == r.getTableId()) {
						table.setStatus(0);
						break;
					}
				}
			}
		}
		
		for (Table table : tempTableList) {
			if (table.getStatus()==1 && table.getCapacity() >= pax) {
				return table.getTableId();
			}
		}
		return -1;
	}
	
	/**
	 * Update reservation List.
	 */
	public void updateRes() {
		LocalDateTime now = LocalDateTime.now();
		ArrayList<Reservation> expired= new ArrayList<Reservation>();
		for (Reservation r : resList) {
			if (LocalDateTime.parse(r.getDateTime(),formatter).isBefore(now.minusMinutes(30))) {
				expired.add(r);
			}
		}
		for (Reservation r : expired){
			long cont = r.getCustContact();
			resList.remove(r);
			System.out.println("Reservation by customer with contact number " + cont + " has been automatically removed(Reason: Expired after 30mins).");
		}
		saveReservations();
	}
	
	/**
	 * Gets the unavailable tables.
	 *
	 * @return the IDs of unavailable tables
	 */
	public ArrayList<Integer> getUnavailableTables() { 
		ArrayList<Integer> reserved = new ArrayList<Integer>();
		for (Reservation r : resList) {
			if (isCurrentSession(r)) {
				reserved.add(r.getTableId());
			}
		}
		return reserved;
	}
	
	/**
	 * Gets the reservations.
	 *
	 * @return the reservations
	 */
	public List<Reservation> getReservations() {
		return this.resList;
	}
	
	/**
	 * Reset reservations List.
	 */
	public void resetRes() {
		resList = new ArrayList<Reservation>();
	}
	
	/**
	 * Gets the reservations made by the customer.
	 *
	 * @param contno the contno
	 * @return the reservations by the customer
	 */
	public ArrayList<Reservation> getCustReservations(long contno) {
		ArrayList<Reservation> reservations = new ArrayList<Reservation>();
		for (Reservation r : resList) {
			if (r.getCustContact() == contno && isCurrentSession(r)) {
				reservations.add(r);
			}
		}
		return reservations;
	}
	
	/**
	 * Activate reservation.
	 *
	 * @return the int
	 */
	public int activateReservation() {
		int tableId = -1;
		System.out.println("Enter contact number: ");
		long custCont = in.nextInt();
		ArrayList<Reservation> reservations = getCustReservations(custCont);
		if (reservations.size() == 1) {
			System.out.println("You have a reservation today for "+reservations.get(0).getPax()+" people.");
			tableId = reservations.get(0).getTableId();
			resList.remove(reservations.get(0));
		}
		else if (reservations.size() ==0) {
			System.out.println("You do not have any reservations for today.");
		}
		else {
			System.out.println("You have "+reservations.size()+" reservations for today. Choose one to create an order for.");
			int count = 1;
			for (Reservation r : reservations) {
				System.out.println("Reservation "+count+":");
				System.out.println("Time of reservation: " + r.getDateTime());
				System.out.println("Reserved for: " + r.getPax() + " people");
				System.out.println("Table no: " + r.getTableId());
				System.out.println("--------------------------");
				count++;
			}
			System.out.println("Which reservation do you want to create an order for?");
			int choice = in.nextInt();
			Reservation toRemove = reservations.get(choice-1);
			tableId = toRemove.getTableId();
			resList.remove(toRemove);
			saveReservations();
		}
		return tableId;
	}
	
	/**
	 * Checks if is current session.
	 *
	 * @param r the reservation to be checked
	 * @return true, if is current session
	 */
	public boolean isCurrentSession(Reservation r) {
		LocalDateTime now = LocalDateTime.now();
		boolean AM;
		if (now.getHour() < 16) {AM = true;}
		else {AM = false;}
		if (LocalDateTime.parse(r.getDateTime(),formatter).truncatedTo(ChronoUnit.DAYS).equals(now.truncatedTo(ChronoUnit.DAYS)) && r.getAM() == AM) {
			return true;
		}
		else return false;
	}
	
	/**
	 * Prints all the reservations.
	 */
	public void printAllReservations() {
		if (resList.size() == 0) System.out.println("No reservations...");
		int count = 1;
		for (Reservation r : resList) {
			System.out.println("Reservation "+count+":");
			System.out.println("Customer contact number: " + r.getCustContact());
			System.out.println("Time of reservation: " + r.getDateTime());
			System.out.println("Reserved for: " + r.getPax() + " people");
			System.out.println("Table no: " + r.getTableId());
			System.out.println("--------------------------");
			count++;
		}
	}
	
	/**
	 * Initialize full reservation of tables.
	 * @param temptablelist the list of tables
	 */
	public void initializeFullReservation (ArrayList<Table> temptablelist){
		boolean AM = false;
		LocalDateTime now = LocalDateTime.now();
		if (now.getHour() < 16){
			AM = true;
		}
		int tableId = checkAvailability(2, now, AM, temptablelist);
		long i = 1;
		while (tableId!=-1){
			resList.add(new Reservation(2, now.format(formatter), AM, tableId, 123));
			i++;
			tableId = checkAvailability(2, now, AM, temptablelist);
		}
		System.out.println("Tables fully reserved for " + now.format(formatter));
	}
}
