package rfk20MenuManager;

/**
 * Class Menu
 * author : Rye Keating
 * created: 11/20/2019
 */

public class Menu {
	private String name;
	private Entree entree = null;
	private Side side = null;
	private Dessert dessert = null;
	private Salad salad = null;
	private int totalCals=0;

	public Menu(String cName) {
		//menu name
		name = cName;
		salad = null;
		entree = null;
		side = null;
		dessert = null;
	}
	
	public Menu(String cName, Entree cEntree, Side cSide) {
		name = cName;
		salad = null;
		entree = cEntree;
		totalCals = totalCals + entree.getCalories();
		side = cSide;
		totalCals = totalCals + side.getCalories();
		dessert = null;
	}
	
	public Menu(String cName, Entree cEntree, Side cSide, Salad cSalad, Dessert cDessert) {
		name = cName;
		salad = cSalad;
		totalCals = totalCals + salad.getCalories();
		entree = cEntree;
		totalCals = totalCals + entree.getCalories();
		side = cSide;
		totalCals = totalCals + side.getCalories();
		dessert = cDessert;
		totalCals = totalCals + dessert.getCalories();
	}
	
	//Setters and Getters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int totalCalories() {
		int sum=0;
		if(this.salad!=null){sum+=this.salad.getCalories();}
		if(this.entree!=null){sum+=this.entree.getCalories();}
		if(this.side!=null){sum+=this.side.getCalories();}
		if(this.dessert!=null){sum+=this.dessert.getCalories();}
		return sum;
	}

	public Entree getEntree() {
		return entree;
	}

	public void setEntree(Entree entree) {
		this.entree = entree;
	}

	public Side getSide() {
		return side;
	}

	public void setSide(Side side) {
		this.side = side;
	}

	public Dessert getDessert() {
		return dessert;
	}

	public void setDessert(Dessert dessert) {
		this.dessert = dessert;
	}

	public Salad getSalad() {
		return salad;
	}

	public void setSalad(Salad salad) {
		this.salad = salad;
	}
		
	public String getDescription(){
		String description="";
		
		if(this.salad!=null){description+="Salad: "+this.salad.getName()+". "+this.salad.getDescription()+".\n";}
		else{description+="Salad: N/A\n";}
		
		if(this.entree!=null){description+="Entree: "+this.entree.getName()+". "+this.entree.getDescription()+".\n";}
		else{description+="Entree: N/A\n";}
		
		if(this.side!=null){description+="Side: "+this.side.getName()+". "+this.side.getDescription()+".\n";}
		else{description+="Side: N/A\n";}
		
		if(this.dessert!=null){description+="Dessert: "+this.dessert.getName()+". "+this.dessert.getDescription()+".\n";}
		else{description+="Dessert: N/A\n";}

		return description;
	}
	
	// Override MenuItem toString.
	public String toString() {
		return name;
	}
}
