package rfk20MenuManager;

/**
 * Class MenuItem
 * author : Rye Keating
 * created: 11/20/2019
 */

public class MenuItem { 
	private String name;
	private String description;
	private int calories;
	private double price;

	public MenuItem (String cname, String cdesc, int ccal, double cprice) {
		name = cname;
		description = cdesc;
		calories = ccal;
		price = cprice;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public int getCalories() {
		return calories;
	}
	public void setCalories(int calories) {
		this.calories = calories;
	}

	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	// Override MenuItem toString.
	public String toString() {
		return name;
	}
}
