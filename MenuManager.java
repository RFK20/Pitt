package rfk20MenuManager;

/**
 * Class MenuManager
 * author : Rye Keating
 * created: 11/20/2019
 */

import java.util.ArrayList;
import java.util.Random;

public class MenuManager {
	private ArrayList<Salad> saladItems = new ArrayList<Salad>();
	private ArrayList<Entree> entreeItems = new ArrayList<Entree>();
	private ArrayList<Side> sideItems = new ArrayList<Side>();
	private ArrayList<Dessert> dessertItems = new ArrayList<Dessert>(); 
	private ArrayList<Menu> createdMenus;
	
	public MenuManager (String dishesFile) {
		ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
		menuItems = FileManager.readItems(dishesFile);

		separateDishes(menuItems);
		
		Menu menu1 = randomMenu("INFSCI 17-1st Random Menu");
		createdMenus = new ArrayList<Menu>();
		createdMenus.add(menu1);
		
		Menu menu2 = randomMenu("INFSCI 17-2nd Random Menu");
		createdMenus.add(menu2);

		Menu min_menu3 = minCaloriesMenu();
		createdMenus.add(min_menu3);
		
		Menu max_menu4 = maxCaloriesMenu();
		createdMenus.add(max_menu4);

		FileManager.writeMenu("data/Menus.txt", createdMenus);
	}
	
	private void separateDishes(ArrayList<MenuItem> menuItems) {
		int index = 0;
		for (index = 0; index < menuItems.size(); index++) {
			if (menuItems.get(index) instanceof Salad) {
				saladItems.add((Salad) menuItems.get(index));
			} else if (menuItems.get(index) instanceof Entree) {
				entreeItems.add((Entree) menuItems.get(index));
			} else if (menuItems.get(index) instanceof Side) {
				sideItems.add((Side) menuItems.get(index));
			} else if (menuItems.get(index) instanceof Dessert) {
				dessertItems.add((Dessert) menuItems.get(index));
			}  
			else {
				System.out.println("Dish type ERROR for menu item = " + menuItems.get(index).getName());
				System.exit(31);
			}
		}
	}
	
	public Menu randomMenu(String menuName) {
		Random r = new Random();
		Menu randMenu = new Menu(menuName);
		
		// Select random salad.
		int randomInt = r.nextInt(saladItems.size());
		randMenu.setSalad(saladItems.get(randomInt));
		
		// Select random entree.
		randomInt = r.nextInt(entreeItems.size());
		randMenu.setEntree(entreeItems.get(randomInt));
		
		// Select random side.
		randomInt = r.nextInt(sideItems.size());
		randMenu.setSide(sideItems.get(randomInt));
		
		// Select random dessert.
		randomInt = r.nextInt(dessertItems.size());
		randMenu.setDessert(dessertItems.get(randomInt));
		
		return randMenu;
	}

	public Menu minCaloriesMenu() {
		Menu minMenu = new Menu("INFSCI 17-Minimum Calorie Menu");
		int index = 0;
		int minIndex = 0;
		
		for (index = 0; index < saladItems.size(); index++) {
			if (saladItems.get(index).getCalories() < saladItems.get(minIndex).getCalories()) {
				minIndex = index;
			}
		}
		minMenu.setSalad(saladItems.get(minIndex));
		
		for (index = 0; index < entreeItems.size(); index++) {
			if (entreeItems.get(index).getCalories() < entreeItems.get(minIndex).getCalories()) {
				minIndex = index;
			}
		}
		minMenu.setEntree(entreeItems.get(minIndex));
		
		for (index = 0; index < sideItems.size(); index++) {
			if (sideItems.get(index).getCalories() < sideItems.get(minIndex).getCalories()) {
				minIndex = index;
			}
		}
		minMenu.setSide(sideItems.get(minIndex));
		
		for (index = 0; index < dessertItems.size(); index++) {
			if (dessertItems.get(index).getCalories() < dessertItems.get(minIndex).getCalories()) {
				minIndex = index;
			}
		}
		minMenu.setDessert(dessertItems.get(minIndex));
		
		return minMenu;
	}

	public Menu maxCaloriesMenu() {
		Menu maxMenu = new Menu("INFSCI 17-Maximum Calorie Menu");
		int index = 0;
		int maxIndex = 0;
		
		for (index = 0; index < saladItems.size(); index++) {
			if (saladItems.get(index).getCalories() > saladItems.get(maxIndex).getCalories()) {
				maxIndex = index;
			}
		}
		maxMenu.setSalad(saladItems.get(maxIndex));
		
		for (index = 0; index < entreeItems.size(); index++) {
			if (entreeItems.get(index).getCalories() > entreeItems.get(maxIndex).getCalories()) {
				maxIndex = index;
			}
		}
		maxMenu.setEntree(entreeItems.get(maxIndex));
		
		for (index = 0; index < sideItems.size(); index++) {
			if (sideItems.get(index).getCalories() > sideItems.get(maxIndex).getCalories()) {
				maxIndex = index;
			}
		}
		maxMenu.setSide(sideItems.get(maxIndex));
		
		for (index = 0; index < dessertItems.size(); index++) {
			if (dessertItems.get(index).getCalories() > dessertItems.get(maxIndex).getCalories()) {
				maxIndex = index;
			}
		}
		maxMenu.setDessert(dessertItems.get(maxIndex));
		
		return maxMenu;
	}
	
	public ArrayList<Salad> getSaladItems() {
		return saladItems;
	}
	
	public ArrayList<Entree> getEntreeItems() {
		return entreeItems;
	}
	
	public ArrayList<Side> getSideItems() {
		return sideItems;
	}
	
	public ArrayList<Dessert> getDessertItems() {
		return dessertItems;
	}

	public ArrayList<Menu> getMenus() {
		return createdMenus;
	}
}
