package test;

public class HelloWorld {

	public static void main(String[] args) {
		System.out.print("hello world!!! adna here");
		
		System.out.print("Meow Meow Meow");
		printrand();
		
		for(int i = 0; i<10;i++)
			System.out.print("Meow Meow Meow");

	}
	public static void printrand(){
		double a = Math.random();
		System.out.print(a);
	}
}
