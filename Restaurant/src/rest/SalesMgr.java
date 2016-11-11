package rest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

/**
 * Represents the Manager class for the SalesLineItem
 * @version 1.0
 * @since 2016-10-28
 */
public class SalesMgr {

	/** The sales list. */
	private List<SalesLineItem> salesList = new ArrayList<SalesLineItem>();
	
	/** The Date and Time formatter. */
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	/**
	 * Instantiates a new sales manager.
	 */
	public SalesMgr() {
		loadSales();
	}

	/**
	 * Loads the list of sales made from saved file.
	 */
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

	/**
	 * Save the list of sales made.
	 */
	public void saveSales() {
		SerializeDB.writeSerializedObject("saleslist.dat", salesList);
	}

	/**
	 * Search list.
	 *
	 * @param menuitem the menuitem
	 * @param dateTime the date time
	 * @return the int
	 */
	public int searchList(MenuItem menuitem, LocalDateTime dateTime) {
		Iterator<SalesLineItem> al = salesList.listIterator();
		while (al.hasNext()) {
			SalesLineItem s = al.next();

			if (s.getMenuItem().getName().equals(menuitem.getName()) && s.getDateTime().equals(dateTime))
				return salesList.indexOf(s);
		}
		return -1;
	}

	/**
	 * Update sales when an Order Invoice is completed.
	 *
	 * @param order the order
	 */
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

	/**
	 * Prints the sales made within a specified period.
	 * the time period is defined by the user
	 *
	 * @param startstr the beginning of the time period
	 * @param endstr the ending of the time period
	 */
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
		// Print list of sales made in the specified time period
		System.out.println("===============================================");
		System.out.printf("%12s\n","Sales Report");
		System.out.println("-----------------------------------------------");
		System.out.println("From:\t" + start.format(formatter).substring(0,10));
		System.out.println("To:\t" + end.format(formatter).substring(0,10));
		System.out.println("-----------------------------------------------");
		System.out.printf("%s: %-25s %4s %2s\n", "ID", "Name:", "Quantity:", "Price:");
		for (Entry<MenuItem,Integer> e : list.entrySet()) {
			double subtotal = calculatePrice(e.getKey().getPrice(), e.getValue());
			System.out.printf("%3s %-26s %-9d %.2f\n\n", e.getKey().getId(), e.getKey().getName(),
					e.getValue(),subtotal);
			totalEarnings = totalEarnings + subtotal;
		}
		System.out.println("===============================================");
		System.out.printf("Subtotal: %29s %.2f\n", " ", totalEarnings);
		System.out.printf("Service Charge: %23s %.2f\n", " ", totalEarnings/10);
		System.out.printf("GST: %34s %.2f\n", " ", totalEarnings*1.1*0.07);
		System.out.printf("Total Sales: %26s %.2f\n", " ", totalEarnings*1.177);
		System.out.println("===============================================");
		
	}
	
	
	/**
	 * Calculate Price
	 * This is done by multiplying the two inputs it receives
	 * 
	 * @param price the price
	 * @param quantity the quantity
	 * @return the subtotal price
	 */
	public double calculatePrice(double price, int quantity){
		return price*quantity;
	}
	
	/**
	 * Reset sales.
	 * Creates a new SalesLineItem list
	 */
	public void resetSales(){
		salesList = new ArrayList<SalesLineItem>();
		saveSales();
	}
}
