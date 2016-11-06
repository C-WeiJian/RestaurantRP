package rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class TableMgr {
	private static List<Table> tableList = new ArrayList<Table>();

	public TableMgr() {
		loadTable();
	}

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

	private void saveTable() {
		SerializeDB.writeSerializedObject("tablelist.dat", tableList);
	}

	public void presetTable() {
		tableList = new ArrayList<Table>();
		for (int i = 0; i < 10; i++) {
			tableList.add(new Table(i + 1, 2, true));
		}
		for (int i = 0; i < 10; i++) {
			tableList.add(new Table(i + 11, 4, true));
		}
		for (int i = 0; i < 5; i++) {
			tableList.add(new Table(i + 21, 8, true));
		}
		for (int i = 0; i < 5; i++) {
			tableList.add(new Table(i + 26, 10, true));
		}
		saveTable();
	}

	Scanner in = new Scanner(System.in);

	// This is the only function you need to call in main app
	// public void checkAvailability() {
	// System.out.println("Please enter table Id:");
	// int tableId = in.nextInt();
	// int index = searchList(tableId);
	// Table result = tableList.get(index);
	// if (result.getStatus()) {
	// System.out.println("Table " + tableId + "is available for " +
	// result.getCapacity());
	// } else
	// System.out.println("Table " + tableId + "is not available");
	// }
	// addTable and removeTable are used for 'admin' purposes when setting up
	// the restaurant

	public void addTable() {
		System.out.println("Please enter table Id:");
		int tableId = in.nextInt();
		System.out.println("Please enter table capacity:");
		int capacity = in.nextInt();
		// System.out.println("Please enter table status:");
		// boolean status = in.nextBoolean();
		Table newTable = new Table(tableId, capacity, true);
		tableList.add(newTable);
		System.out.println("Table successfully added!");
		saveTable();
	}

	public void removeTable() {
		System.out.println("Please enter tableId:");
		int index = searchList(in.nextInt());
		tableList.remove(index);
		System.out.println("Table successfully removed!");
		saveTable();
	}

	public void showTable() {
		System.out.print("TableId\t\tCapacity\tStatus\n");
		for (Table n : tableList) {
			System.out.print("Table" + n.getTableId() + "\t\t" + n.getCapacity() + " pax");
			if (n.getStatus())
				System.out.print("\t\tAvailable");
			else
				System.out.print("\t\tUnavailable");
			System.out.println();

		}
	}

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

	public int nextAvailableTable(int Pax) {
		Iterator<Table> al = tableList.listIterator();
		while (al.hasNext()) {
			Table n = al.next();
			if (n.getStatus() == true && n.getCapacity() >= Pax)
				return tableList.indexOf(n);
		}
		return -1;
	}

	public List<Table> getTableList() {
		return tableList;
	}

	public int assignTable(int orderId, int pax) {
		int temp = nextAvailableTable(pax);
		if (temp == -1)
			return -1;
		Table temptable = tableList.get(temp);
		temptable.setorderId(orderId);
		temptable.setStatus(false);
		saveTable();
		return temptable.getTableId();
	}

	public Table getTable(int tableId) {
		int index = searchList(tableId);
		return getTableList().get(index);
	}

	public void updateTable(int tableId, boolean status) {
		getTable(tableId).setStatus(status);
		saveTable();
	}
}
