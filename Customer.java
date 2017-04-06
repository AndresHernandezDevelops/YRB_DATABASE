public class Customer {
	private int cID;
	private String name, city;
	public Customer(int ID, String cname, String ccity){
		cID = ID;
		name = cname;
		city = ccity;
	}
	public int getcID(){
		return cID;
	}
	public String getName(){
		return name;
	}
	public String getCity(){
		return city;
	}
}
