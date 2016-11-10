package rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * The Class TableMgr. This class manages and control the tables of the restaurant
 */
public class TableMgr {
	
	/** The table list. */
	private static ArrayList<Table> tableList = new ArrayList<Table>();

	/**
	 * Instantiates a new table manager.
	 */
	public TableMgr() {
		loadTable();
	}

	/**
	 * Loads latest version of tableList from DB.
	 */
	private void loadTable() {
		List list;
		tableList = new ArrayList<Table>();
		try {
			list = (ArrayList) SerializeDB.readSerializedObject("tablelist.dat");
			for (int i = 0; i < list.size(); i++) {
				Table m = (Table) list.get(i);
				tableList.add(m);
			}
		} catch (Exception e) {
			System.out.println("Exception >> " + e.getMessage());
		}
	}

	/**
	 * Save current version of tableList into DB.
	 */
	private void saveTable() {
		SerializeDB.writeSerializedObject("tablelist.dat", tableList);
	}

	/**
	 * Preset tables.
	 */
	public void presetTable() {
		tableList = new ArrayList<Table>();
		for (int i = 0; i < 10; i++) {
			tableList.add(new Table(i + 1, 2, 1));
		}
		for (int i = 0; i < 10; i++) {
			tableList.add(new Table(i + 11, 4, 1));
		}
		for (int i = 0; i < 5; i++) {
			tableList.add(new Table(i + 21, 8, 1));
		}
		for (int i = 0; i < 5; i++) {
			tableList.add(new Table(i + 26, 10, 1));
		}
		saveTable();
	}

	/** The scanner. */
	Scanner in = new Scanner(System.in);

	/**
	 * Adds a table.
	 */
	public void addTable() {
		System.out.println("Please enter table Id:");
		int tableId = in.nextInt();
		System.out.println("Please enter table capacity:");
		int capacity = in.nextInt();
		Table newTable = new Table(tableId, capacity, 1);
		tableList.add(newTable);
		System.out.println("Table successfully added!");
		saveTable();
	}

	/**
	 * Removes a table.
	 */
	public void removeTable() {
		System.out.println("Please enter tableId:");
		int index = searchList(in.nextInt());
		tableList.remove(index);
		System.out.println("Table successfully removed!");
		saveTable();
	}

	/**
	 * Show table.
	 * This is done by iterating through the tableList and retrieving the relevant information
	 */
	public void showTable() {
		System.out.print("TableId\t\tCapacity\tStatus\n");
		System.out.println("---------------------------------------");
		for (Table n : tableList) {
			System.out.print("Table" + n.getTableId() + "\t\t" + n.getCapacity() + " pax");
			if (n.getStatus() == 1)
				System.out.print("\t\tAvailable");
			else if (n.getStatus() == 0)
				System.out.print("\t\tReserved");
			else
				System.out.print("\t\tOccupied");
			System.out.println();

		}
	}

	/**
	 * Search tableList using tableID.
	 * 
	 * Iterates through the tableList and return the index of the table specified by the parameter
	 *
	 * @param tableId the table id
	 * @return the index of the desired table in tableList
	 * @return -1 if table not found
	 */
	public int searchList(int tableId) {
		Iterator<Table> al = tableList.listIterator();
		while (al.hasNext()) {
			Table n = al.next();
			if (n.getTableId() == tableId)
				return tableList.indexOf(n);
		}
		System.out.println("Table not found!");
		return -1;
	}

	/**
	 * Searches for next available table in tableList based on number of pax required.
	 *
	 * @param Pax the pax
	 * @return tableId of next available table
	 * @return -1 if table not found
	 */
	public int nextAvailableTable(int Pax) {
		Iterator<Table> al = tableList.listIterator();
		while (al.hasNext()) {
			Table n = al.next();
			if (n.getStatus() == 1 && n.getCapacity() >= Pax)
				return n.getTableId();
		}
		return -1;
	}

	/**
	 * Gets the table list.
	 *
	 * @return the table list
	 */
	public ArrayList<Table> getTableList() {
		return tableList;
	}

	/**
	 * Assign table.
	 *
	 * @param orderId the order id
	 * @param tableId the table id
	 * @return true, if successful
	 */
	public boolean assignTable(int orderId, int tableId) {
		int temp = searchList(tableId);
		Table temptable = tableList.get(temp);
		if (temptable.getStatus() == 1){
			temptable.setorderId(orderId);
			temptable.setStatus(-1); 
			saveTable();
			return true;
		}
		System.out.println("Error - Table not available");
		return false;
	}

	/**
	 * Gets the table.
	 *
	 * @param tableId the table id
	 * @return the table
	 */
	public Table getTable(int tableId) {
		int index = searchList(tableId);
		return getTableList().get(index);
	}

	/**
	 * Update table.
	 *
	 * @param tableId the table id
	 * @param status the status
	 * @param orderId the orderId
	 */
	public void updateTable(int tableId, int status, int orderId) {
		Table temp = getTable(tableId);
		temp.setStatus(status);
		temp.setorderId(orderId);
		saveTable();
	}
	
	
	/**
	 * Update overall tableList with the current reservations provided.
	 * Firstly, it resets tables marked as reserved(status == 0) as available.
	 * Subsequently, it marks the relevant tables as reserved(status == 0) as per ArrayList res.
	 *
	 * @param res List of Integer containing the tableId of reservation for current session
	 * @param ord List of Integer containing the tableId and orderId of current orders
	 */
	public void fullUpdate (ArrayList<Integer> res, ArrayList<Integer[]> ord){
		
		for (Table t: tableList){
			t.setStatus(1);
			t.setorderId(-1);
		}
		for (Integer t: res){
			updateTable(t, 0, -1);
		}
		for (Integer[] t: ord){
			updateTable(t[0], -1, t[1]);
		}
	}
}
