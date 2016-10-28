package rest;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class TableMgr {
	private List<Table> tableList;

	TableMgr() {
		//tableList = loadTables();		//to initialize right at the start?
	}

	Scanner in = new Scanner(System.in);

	public void save() {
		// save into txt file
		System.out.println("Table successfully saved!");
	}

	public List<Table> load() {
		// Load txt file into tableList
		System.out.println("Table successfully loaded!");
		return tableList;
	}
	
	// This is the only function you need to call in main app
	public void checkAvailability() {	
		System.out.println("Please enter table ID:");
		int tableId = in.nextInt();
		int index = searchList(tableId);
		Table result = tableList.get(index);
		if (result.getStatus()) {
			System.out.println("Table " + tableId + "is available for " + result.getCapacity());
		} else
			System.out.println("Table " + tableId + "is not available");
	}
	//addTable and removeTable are used for 'admin' purposes when setting up the restaurant
	public List<Table> addTable() {
		System.out.println("Please enter table ID:");
		int tableId = in.nextInt();
		System.out.println("Please enter table capacity:");
		int capacity = in.nextInt();
		System.out.println("Please enter table status:");
		boolean status = in.nextBoolean();
		Table newTable = new Table(tableId, capacity, status);
		tableList.add(newTable);
		System.out.println("Table successfully added!");
		return tableList;
	}

	public void removeTable() {
		System.out.println("Please enter tableID:");
		int index = searchList(in.nextInt());
		tableList.remove(index);
		System.out.println("Table successfully removed!");
	}

	public int searchList(int TableId) {
		Iterator<Table> al = tableList.listIterator();
		Table n = al.next();
		while (al.hasNext() || n.getTableID() != TableId) {
			n = al.next();
		}
		return tableList.indexOf(n);
	}

	public List<Table> getTableList() {
		return tableList;
	}
}
