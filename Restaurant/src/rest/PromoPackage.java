package rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PromoPackage extends MenuItem {
	private List<MenuItem> pItems = new ArrayList<MenuItem>();
	
	public PromoPackage(String name, String description, double price, String type, boolean isPromo) {
		super(name, description, price, type, isPromo);
	}
	
	public void addItems(Menu menu){
		Scanner sc = new Scanner(System.in);
		int ala = 1;
		String menuId;
		System.out.println("Ala Carte Menu:");
		System.out.println("---------------");
		System.out.println("Name: ");		
		for (MenuItem m : menu.getMenu()){
			if(m.isPromo() == false){
				System.out.println(ala + ") " + m.getName() + "\t\t\t\t");
				ala++;
			}
		}
		System.out.println("Choose MenuItem: ");
		menuId = sc.nextLine();
	}
	
}
