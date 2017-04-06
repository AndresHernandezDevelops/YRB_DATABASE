
public class MinPrice {
	private float price;
	private String club;
	public MinPrice(float pprice, String cclub)
	{
		price = pprice;
		club = cclub;
	}
	public String getClub()
	{
		return club;
	}
	public float getPrice()
	{
		return price;
	}
	public void setPrice(float pprice)
	{
		price = pprice;
	}
}
