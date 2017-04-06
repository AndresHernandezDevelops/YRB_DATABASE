import java.sql.SQLException;
import java.util.*;
import java.io.*;

public class main {
	public static void main(String []args) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		int cID, quantity;
		Book book;
		Customer customer;
		String cat;
		MinPrice minPrice;
		char yesno;
		Database dbObj = new Database();
		Scanner input = new Scanner(System.in);
		System.out.print("WHAT IS YOUR ID > ");
		cID = input.nextInt();
		System.out.println();
		customer = dbObj.find_customer(cID);
		while(customer.getcID()==-1)
		{
			System.out.print("DOES NOT EXIST. TRY AGAIN > ");
			cID = input.nextInt();
			System.out.println();
			customer = dbObj.find_customer(cID);
		}
		cat = dbObj.fetch_categories();
		book = dbObj.find_book(cat);
		while(book.getYear()==-1)
		{
			System.out.println("DOES NOT EXIST. TRY AGAIN > ");
			cat = dbObj.fetch_categories();
			book = dbObj.find_book(cat);
		}
		minPrice = dbObj.min_price(book, cat, cID);
		System.out.println("THE CHEAPEST BOOK COSTS > " + minPrice.getPrice()+ "\n");
		System.out.println("ENTER QUANTITY > ");
		quantity = input.nextInt();
		System.out.println("YOUR TOTAL IS > " + quantity * minPrice.getPrice());
		System.out.println("DO YOU WANT TO PURCHASE THIS (Y/N) > ");
		yesno = input.next().charAt(0);
		while(yesno != 110 && yesno != 78 && yesno != 89 && yesno!= 121)
		{
			System.out.println("INVALID CHARACTER, TRY AGAIN > ");
			yesno = input.next().charAt(0);
		}
		if(yesno == 'y')
		{
			dbObj.insert_purchase(cID, book, quantity, minPrice);
		}
		System.out.println("THANKS. SEE YOU LATER!");
	}
}
