package rest;

import java.util.Scanner;

public class TestMgr {

	public static void main(String[] args) {
		System.out.println("Please enter the date which you wish to book: (dd-mm)");
		Scanner in = new Scanner(System.in);
				in.useDelimiter("-");
				in.useDelimiter("\\n");
		int day = in.nextInt();
		int month = in.nextInt();
		
		System.out.println("day is " + day + "month is " + month);
		in.close();
	}

}
