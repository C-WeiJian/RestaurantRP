package rest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * The Class ReservationMgr.
 * Contains the list of reservations and handles any adding, removing, updating, activating of reservations
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
	 * Instantiates a new reservation manager.
	 */
	public ReservationMgr(){
		loadReservations();
	}
	
	/**
	 * Loads latest version of reservations.
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
	 * Adds a reservation, if there is a table available with the required capacity.
	 * It will check for number of people coming, the date, and the time of reservation.
	 * It will reject invalid timings such as timings in the past and timings that are out of the opening hours of the restaurant.
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
	 * Removes a reservation.
	 * Creates a temp ArrayList to check if user has more than one reservation made under the same contact number.
	 * Allows user to decide which reservation to remove if the user has more than one reservation.
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
	 * Creates a temp ArrayList to print out the reservations if user has more than one reservation made under the same contact number.
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
	 * Searches reservation list using contact number as an identifier
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
	 * Checks if there is a table available for the required number of people, and session (AM or PM).
	 * Returns a table number if an available table is found, otherwise returns -1.
	 * @param pax the number of people
	 * @param t the time of requested reservation
	 * @param AM true if the requested reservation is in the AM session, false if in the PM session
	 * @param tempTableList the list of tables in the restaurant
	 * @return the tableID of available table
	 * @return -1 if no tables are available
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
	 * Update reservation list.
	 * Checks and removes any reservations that are already 30 minutes past their indicated timing.
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
	 * Gets the reserved tables.
	 * Creates an ArrayList of Integers to store the tableIds of reserved tables.
	 * @return the list of tableIds of reserved tables
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
	 * Gets the list of reservations.
	 *
	 * @return the reservationsList
	 */
	public List<Reservation> getReservations() {
		return this.resList;
	}
	
	/**
	 * Resets the reservations List.
	 */
	public void resetRes() {
		resList = new ArrayList<Reservation>();
	}
	
	/**
	 * Gets the reservations already made by the customer based on the customer contact number.
	 *
	 * @param contno the customer's contact number
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
	 * Activate reservation when the customer arrives at the restaurant.
	 * Checks the reservation and converts it to become an order.
	 * Allows the 'reserved' table to become 'occupied'.
	 * Removes the reservation and updates the reservations list.
	 * @return the tableId of table that was reserved
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
	 * Checks if the reservation is for current session.
	 * @param r the reservation to be checked
	 * @return true, if it is for current session
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
	 * Initializes test case of full reservation of tables.
	 * @param temptablelist the list of tables in the restaurant
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
