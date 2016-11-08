package rest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class SalesMgr {

	private List<SalesLineItem> salesList = new ArrayList<SalesLineItem>();
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	public SalesMgr() {
		loadSales();
	}

	public void loadSales() {
		List list;
		salesList = new ArrayList<SalesLineItem>();
		try {
			list = (ArrayList) SerializeDB.readSerializedObject("saleslist.dat");
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) instanceof SalesLineItem) {
					SalesLineItem m = (SalesLineItem) list.get(i);
					salesList.add(m);
				}
			}
		} catch (Exception e) {
			System.out.println("Exception >> " + e.getMessage());
		}
	}

	public void saveSales() {
		SerializeDB.writeSerializedObject("saleslist.dat", salesList);
	}

	public int searchList(MenuItem menuitem, LocalDateTime dateTime) {
		Iterator<SalesLineItem> al = salesList.listIterator();
		while (al.hasNext()) {
			SalesLineItem s = al.next();

			if (s.getMenuItem().getName().equals(menuitem.getName()) && s.getDateTime().equals(dateTime))
				return salesList.indexOf(s);
		}
		return -1;
	}

	public void updateSales(Order order) {
		LocalDateTime dateTime = order.getdateTime().truncatedTo(ChronoUnit.DAYS);
		for (OrderLineItem o : order.getOrders()) {
			int temp = searchList(o.getMenuItem(), dateTime);
			if (temp == -1) {
				salesList.add(new SalesLineItem(o.getMenuItem(), o.getQuantity(), dateTime));
			} else {
				int initial = salesList.get(temp).getQuantity();
				int newQuantity = initial + o.getQuantity();
				salesList.get(temp).setQuantity(newQuantity);
			}
		}
		saveSales();
	}

	public void printSales(String startstr, String endstr) {

		LocalDateTime start = LocalDateTime.parse(startstr, formatter);
		LocalDateTime end = LocalDateTime.parse(endstr, formatter);
		
		double totalEarnings = 0;
		List<SalesLineItem> tempList = new ArrayList<SalesLineItem>();
		HashMap<MenuItem,Integer> list = new HashMap<MenuItem,Integer>();
		for (SalesLineItem s : salesList) {
			if (s.getDateTime().isBefore(end.plusDays(1)) && s.getDateTime().isAfter(start.minusDays(1))) {
				if (!list.containsKey(s.getMenuItem())) {
					list.put(s.getMenuItem(), s.getQuantity());
				}
				else {
					list.put(s.getMenuItem(), list.get(s.getMenuItem())+s.getQuantity());
				}
			}
		}
		// Print TempList
		if(tempList.isEmpty())
			System.out.println("I'm Sad and Empty :(");
		else
			System.out.println("I'm not Empty :)");
		System.out.println("===============================================");
		System.out.printf("%12s\n","Sales Report");
		System.out.println("-----------------------------------------------");
		System.out.println("From:\t" + start.format(formatter).substring(0,10));
		System.out.println("To:\t" + end.format(formatter).substring(0,10));
		System.out.println("-----------------------------------------------");
		System.out.printf("%s: %-25s %4s %2s\n", "ID", "Name:", "Quantity:", "Price:");
		for (Entry<MenuItem,Integer> e : list.entrySet()) {
			System.out.printf("%3s %-26s %-9d %.2f\n\n", e.getKey().getId(), e.getKey().getName(),
					e.getValue(),e.getKey().getPrice()*e.getValue());
			totalEarnings = totalEarnings + e.getKey().getPrice()*e.getValue();
		}
		System.out.println("===============================================");
		System.out.printf("Subtotal: %29s %.2f\n", " ", totalEarnings);
		System.out.printf("Service Charge: %23s %.2f\n", " ", totalEarnings/10);
		System.out.printf("GST: %34s %.2f\n", " ", totalEarnings*1.1*0.07);
		System.out.printf("Total Sales: %26s %.2f\n", " ", totalEarnings*1.177);
		System.out.println("===============================================");
		
	}
	
	public void resetSales(){
		salesList = new ArrayList<SalesLineItem>();
		saveSales();
	}
}
