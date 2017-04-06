
import java.net.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
public class Database {
	private Connection conDB;   // Connection to the database system.
    private String url;         // URL: Which database?
    private String custName;
    
    
	public Database() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
		url = "jdbc:db2:c3421m";    // URL: This database.
        conDB = DriverManager.getConnection(url);
	}
	
	public Customer find_customer(int cID) throws SQLException
	{
		Customer ccustomer = new Customer(-1, "-1", "-1");
		String name, city; 
		String            queryText = "";     // The SQL text.
	    PreparedStatement querySt   = null;   // The query handle.
	    ResultSet         answers   = null;   // A cursor.
		queryText = "select * from yrb_customer where cid=" +cID;

		// Prepare the query.
		try {			
			querySt = conDB.prepareStatement(queryText);
			} catch (SQLException e) {
				System.out.println("SQL#4 failed in prepare");
				System.out.println(e.toString());
				System.exit(0);
			}
		
		// Execute the query.
		try {
			answers = querySt.executeQuery();
		} catch (SQLException e) {
			System.out.println("SQL#4 failed in execute");
			System.out.println(e.toString());
			System.exit(0);
		}
		
		try{	
			if(answers.next()){
				name = answers.getString("name");
				city = answers.getString("city");
				ccustomer = new Customer(cID, name, city);
				System.out.println("customer ID: " + cID + ", Name: " + name + ", city: " + city);
			}
		}catch(SQLException e)
		{
			System.out.println("error obtaining the customer through ID.");
		}
		try {
			answers.close();
		} catch (SQLException e) {
			System.out.print("SQL#4 failed closing cursor.\n");
			System.out.println(e.toString());
			System.exit(0);
		}
		try {
			querySt.close();
		} catch (SQLException e) {
			System.out.print("SQL#4 failed closing the handle.\n");
			System.out.println(e.toString());
			System.exit(0);
		}
		return ccustomer;
	}
	
	public String fetch_categories() throws SQLException
	{
		int index=0, choice;
		List <String> categories = new ArrayList<String>(); 
		String title;
		Scanner input = new Scanner(System.in);
		String            queryText = "";     // The SQL text.
	    PreparedStatement querySt   = null;   // The query handle.
	    ResultSet         answers   = null;   // A cursor.
		queryText = "select distinct * from yrb_category";
		// Prepare the query.
		try {			
			querySt = conDB.prepareStatement(queryText);
			} catch (SQLException e) {
				System.out.println("SQL#4 failed in prepare");
				System.out.println(e.toString());
				System.exit(0);
			}
		
		// Execute the query.
		try {
			answers = querySt.executeQuery();
		} catch (SQLException e) {
			System.out.println("SQL#4 failed in execute");
			System.out.println(e.toString());
			System.exit(0);
		}
		try{	
			System.out.println("\nPICK CATEGORY: \n");
			while(answers.next()){
				categories.add(answers.getString("cat"));
				System.out.println(index+1 + " " + categories.get(index));
				index++;
			}
		}catch (SQLException e)
		{
			System.out.println("error obtaining the category");
		}
		try {
			answers.close();
		} catch (SQLException e) {
			System.out.print("SQL#4 failed closing cursor.\n");
			System.out.println(e.toString());
			System.exit(0);
		}
		try {
			querySt.close();
		} catch (SQLException e) {
			System.out.print("SQL#4 failed closing the handle.\n");
			System.out.println(e.toString());
			System.exit(0);
		}
		System.out.println();
		choice = input.nextInt();
		while(choice<=0 || choice>categories.size())
		{
			System.out.print("WRONG CHOICE PAL, TRY AGAIN > ");
			choice = input.nextInt();
		}
		//System.out.println("You picked: " + choice + " : " + categories.get(choice-1));
		return categories.get(choice-1);
	}
	
	public Book find_book(String cat) throws SQLException
	{
		String title;
		int choice, index=0;
		Scanner input = new Scanner(System.in);
		String            queryText = "";     // The SQL text.
	    PreparedStatement querySt   = null;   // The query handle.
	    ResultSet         answers   = null;   // A cursor.
		List<Book> bookList = new ArrayList<Book>(); //creating a book list for the books we return with that title and category
		Book temp1 = new Book("", -1, "", -1);
		System.out.println("\nENTER A TITLE > \n");
		title = input.next();		
		queryText = "select title, year, language, weight from yrb_book where title = '" + title + "' and cat = '"+ cat + "'";
		
		try {			
			querySt = conDB.prepareStatement(queryText);
			} catch (SQLException e) {
				System.out.println("SQL#4 failed in prepare");
				System.out.println(e.toString());
				System.exit(0);
			}
		
		// Execute the query.
		try {
			answers = querySt.executeQuery();
		} catch (SQLException e) {
			System.out.println("SQL#4 failed in execute");
			System.out.println(e.toString());
			System.exit(0);
		}
		
		try{
			while(answers.next()){
				Book temp = new Book(answers.getString("title"), Integer.parseInt(answers.getString("year")), answers.getString("language"), Integer.parseInt(answers.getString("weight")));
				bookList.add(temp);
				//System.out.println(index+1 + ")  Title: " + answers.getString("title") + ", Year: " + temp.getYear() + ", Language:  " + answers.getString("language") + ", Weight: " + answers.getString("weight"));
				index++;
			}
		}catch (SQLException e)
		{
			System.out.println("error finding the book.");
		}
		try {
			answers.close();
		} catch (SQLException e) {
			System.out.print("SQL#4 failed closing cursor.\n");
			System.out.println(e.toString());
			System.exit(0);
		}
		try {
			querySt.close();
		} catch (SQLException e) {
			System.out.print("SQL#4 failed closing the handle.\n");
			System.out.println(e.toString());
			System.exit(0);
		}
		if(bookList.isEmpty()==false)
		{
			int index2=0;
			System.out.println("\nMAKE A CHOICE > \n");
			while(index2<index)
			{
				System.out.println(index2+1 + ") title: " + bookList.get(index2).getTitle() + ", year: "+bookList.get(index2).getYear()+", language: "+bookList.get(index2).getLanguage() +", weight: "+bookList.get(index2).getWeight()); 
				index2++;
			}
			System.out.println();
			choice = input.nextInt();
			while(choice<=0 || choice>bookList.size())
			{
				System.out.print("WRONG CHOICE PAL, TRY AGAIN > \n");
				choice = input.nextInt();
			}
			System.out.println("YOU PICKED: " + choice + " : " + bookList.get(choice-1).getTitle() + "\n");
			temp1 = bookList.get(choice-1);
		
		}
		return temp1;
	}
	
	public MinPrice min_price(Book book, String cat, int cID) throws SQLException
	{
		float price = 99999999;
		String club = "";
		String            queryText = "";     // The SQL text.
	    PreparedStatement querySt   = null;   // The query handle.
	    ResultSet         answers   = null;   // A cursor.
		queryText = "select title, club, min(price) as minprice from (select title, club, price from yrb_offer where title = '"+book.getTitle()+"' and year = '"+book.getYear()+"' and club in (select club from yrb_member where cid = '"+cID+"')) group by title, club";
		try {			
			querySt = conDB.prepareStatement(queryText);
			} catch (SQLException e) {
				System.out.println("SQL#4 failed in prepare");
				System.out.println(e.toString());
				System.exit(0);
		}
		
		try {
			answers = querySt.executeQuery();
		} catch (SQLException e) {
			System.out.println("SQL#4 failed in execute");
			System.out.println(e.toString());
			System.exit(0);
		}
		try{
			while(answers.next()){
				if(Float.parseFloat(answers.getString("minprice")) < price)
				{
					price = Float.parseFloat(answers.getString("minprice"));
					club = answers.getString("club");
				}
			}
		}catch (SQLException e)
		{
			System.out.println("error");
		}
		
		try {
			answers.close();
		} catch (SQLException e) {
			System.out.print("SQL#4 failed closing cursor.\n");
			System.out.println(e.toString());
			System.exit(0);
		}
		try {
			querySt.close();
		} catch (SQLException e) {
			System.out.print("SQL#4 failed closing the handle.\n");
			System.out.println(e.toString());
			System.exit(0);
		}
		return new MinPrice(price, club);
	}
	
	public void insert_purchase(int cID, Book book, int quantity, MinPrice minPrice) throws SQLException
	{
		String            queryText = "";     // The SQL text.
	    PreparedStatement querySt   = null;   // The query handle.
	    ResultSet         answers   = null;   // A cursor.
	    Timestamp time = new Timestamp(System.currentTimeMillis());
		DateFormat date = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss"); //current time and date
		String currentDateAndTime = date.format(time);
		queryText = "INSERT INTO yrb_purchase VALUES (?,?,?,?,?,?)";
		try {			
			querySt = conDB.prepareStatement(queryText);
			} catch (SQLException e) {
				System.out.println("SQL#5 failed in prepare");
				System.out.println(e.toString());
				System.exit(0);
		}
		
        // Execute the query.
		try {
			
			querySt.setInt(1, cID);
			querySt.setString(2, minPrice.getClub());
			querySt.setString(3, book.getTitle());
			querySt.setInt(4, book.getYear());
			querySt.setString(5, currentDateAndTime);
			querySt.setInt(6, quantity);	
			querySt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL#1 failed in update");
			System.out.println(e.toString());
			System.exit(0);
		}
		
		// We're done with the handle.
		try {
			querySt.close();
		} catch (SQLException e) {
			System.out.print("SQL#5 failed closing the handle.\n");
			System.out.println(e.toString());
			System.exit(0);
		}
		conDB.commit();
		
		
	}
	//conDB.close(); 
}
