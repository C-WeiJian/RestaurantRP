//package rest;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.time.month;
//
//public class testtest {
//	
//	public static void main(String[] arg0){
//		String str = "1986-04-08 12:30";
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//		
//		LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
//		
//		String formattedDateTime = dateTime.format(formatter);
//		System.out.println(formattedDateTime);
//		
//		LocalDateTime now = LocalDateTime.now();
//		
//		String nowstr = now.format(formatter);
//		System.out.println(nowstr);
//		
//		System.out.println(now.getYear());
//		System.out.println(now.getMonth());
//		
//		if((int)now.getMonth() == 10)System.out.println("ok");
//		
//	}
//}
