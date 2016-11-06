package rest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SalesMgr {
	
	private List<SalesLineItem> salesList = new ArrayList<SalesLineItem>();
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	
	public void loadMenu(){
		List list;
		salesList = new ArrayList<SalesLineItem>();
		try	{
			list = (ArrayList)SerializeDB.readSerializedObject("saleslist.dat");
			for (int i = 0 ; i < list.size() ; i++) {
				if (list.get(i) instanceof SalesLineItem){
					SalesLineItem m = (SalesLineItem)list.get(i);
					salesList.add(m);
				}
			}
		} catch ( Exception e ) {
			System.out.println( "Exception >> " + e.getMessage() );
		}
	}
	
	public void saveMenu(){
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
	
	public void updateSales(Order order){
		LocalDateTime dateTime = order.getdateTime().withHour(12).withMinute(0).withSecond(0).withNano(0);
		for(OrderLineItem o : order.getOrders()){
			int temp = searchList(o.getMenuItem(), dateTime);
			if(temp == -1){
				salesList.add(new SalesLineItem(o.getMenuItem(), o.getQuantity(), dateTime));
			}
			else{
				int initial = salesList.get(temp).getQuantity();
				int newQuantity = initial + o.getQuantity();
				salesList.get(temp).setQuantity(newQuantity);
			}
		}
	}
	
	public void printSales(LocalDateTime start, LocalDateTime end){
		
		
	}
	
	public String getdateTime(Order order) {
		return order.getdateTime().format(formatter);
	}

	public void setdateTime(Order order, String time) {
		order.setdateTime(LocalDateTime.parse(time, formatter));
	}
}
