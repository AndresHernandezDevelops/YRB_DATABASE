
public class Book {
	private String title, language;
	private int year, weight;
	
	public Book(String tit, int yr, String lg, int wg){
		title = tit;
		year = yr;
		language = lg;
		weight = wg;
	}
	public String getTitle()
	{
		return title;
	}
	public String getLanguage()
	{
		return language;
	}
	public int getYear()
	{
		return year;
	}
	public int getWeight()
	{
		return weight;
	}
}

