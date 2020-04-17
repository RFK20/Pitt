package rfk20MenuManager;

/**
 * Class FileManager
 * author : Rye Keating
 * created: 11/20/2019
 */

import java.util.ArrayList; // import ArrayList class
import java.io.BufferedWriter;
import java.io.File; // Import File class
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner; // Import Scanner class to read files

public class FileManager {
	public static ArrayList<MenuItem> readItems(String fileName) {
		String fName = "";
		String fType = "";
		String fDescription = "";
		int fCalories = 0;
		double fPrice = 0;
		int linePosition = 0;
		int linePositionNextParm = 0;
		ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>(); // create ArrayList object

		File fileDishes = new File(fileName); // Specify the filename
		
		Scanner myReader = null;
		try {
			myReader = new Scanner(fileDishes);
		}
		catch (FileNotFoundException e) {
		    System.out.println("FileNotFoundException on file " + fileName);
		    System.exit(102);	
		}
		while (myReader.hasNextLine()) {
			String data = myReader.nextLine();

			linePosition = data.indexOf("@@", 0);
			// Was separator found?
			if (linePosition == -1) {
				System.out.println("Dishes file invalid syntax line, name = " + data);
				System.exit(103);
			} else {
				fName = data.substring(0, linePosition); // name
			}

			linePositionNextParm = linePosition + 2;
			linePosition = data.indexOf("@@", linePositionNextParm);
			// Was separator found?
			if (linePosition == -1) {
				System.out.println("Dishes file invalid syntax line, error 1, dish type = " + data);
				System.exit(106);
			} else {
				fType = data.substring(linePositionNextParm, linePosition); // dish type
			}

			linePositionNextParm = linePosition + 2;
			linePosition = data.indexOf("@@", linePositionNextParm);
			// Was separator found?
			if (linePosition == -1) {
				System.out.println("Dishes file invalid syntax line, description = " + data);
				System.exit(107);
			} else {
				fDescription = data.substring(linePositionNextParm, linePosition); // description
			}

			linePositionNextParm = linePosition + 2;
			linePosition = data.indexOf("@@", linePositionNextParm);
			// Was separator found?
			if (linePosition == -1) {
				System.out.println("Dishes file invalid syntax line, calories = " + data);
				System.exit(108);
			} else {
				fCalories = Integer.parseInt(data.substring(linePositionNextParm, linePosition)); // calories
			}

			linePositionNextParm = linePosition + 2;
			fPrice = Double.parseDouble(data.substring(linePositionNextParm)); // price

			if (fType.equals("salad")) {
				Salad salad_Read = new Salad(fName, fDescription, fCalories, fPrice);
				menuItems.add(salad_Read);
			}
			else if (fType.equals("entree")) {
				Entree entreeRead = new Entree(fName, fDescription, fCalories, fPrice);
				menuItems.add(entreeRead);
			}
			else if (fType.equals("side")) {
				Side sideRead = new Side(fName, fDescription, fCalories, fPrice);
				menuItems.add(sideRead);
			}
			else if (fType.equals("dessert")) {
				Dessert dessert_Read = new Dessert(fName, fDescription, fCalories, fPrice);
				menuItems.add(dessert_Read);
			}
			else {
				System.out.println("Dishes file invalid syntax line, error 2, dish type = " + data);
				System.exit(101);
			}
		}
		myReader.close();

		return menuItems;
	} // readItems

	public static void writeMenu(String fileName, ArrayList<Menu> menus) {
		int totalCalories = 0;
		double totalPrice = 0;
		File fileMenus = new File(fileName); 
		FileWriter fw = null;
		try {
			// if file does not exist, create it
			if (!fileMenus.exists()) {
				fileMenus.createNewFile();
			}
			fw = new FileWriter(fileMenus.getAbsoluteFile());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(140);
		}
		BufferedWriter bw = new BufferedWriter(fw);

		int index = 0;
		for (index = 0; index < menus.size(); index++) {
			try {
				bw.write(menus.get(index).getName());		// menu name
				totalCalories = 0;
				totalPrice = 0;
				
				bw.newLine();
				bw.write(menus.get(index).getSalad().getName()        				 + "--" +
						 menus.get(index).getSalad().getDescription() 				 + "--" +
						 Integer.toString(menus.get(index).getSalad().getCalories()) + "--" +
						 Double.toString(menus.get(index).getSalad().getPrice()));
				totalCalories += menus.get(index).getSalad().getCalories();
				totalPrice += menus.get(index).getSalad().getPrice();
				bw.newLine();
				bw.write(menus.get(index).getEntree().getName()        				  + "--" +
						 menus.get(index).getEntree().getDescription() 				  + "--" +
						 Integer.toString(menus.get(index).getEntree().getCalories()) + "--" +
						 Double.toString(menus.get(index).getEntree().getPrice()));
				totalCalories += menus.get(index).getEntree().getCalories();
				totalPrice += menus.get(index).getEntree().getPrice();
				bw.newLine();
				bw.write(menus.get(index).getSide().getName()        				 + "--" +
						 menus.get(index).getSide().getDescription() 				 + "--" +
						 Integer.toString(menus.get(index).getSide().getCalories())  + "--" +
						 Double.toString(menus.get(index).getSide().getPrice()));
				totalCalories += menus.get(index).getSide().getCalories();
				totalPrice += menus.get(index).getSide().getPrice();
				bw.newLine();
				bw.write(menus.get(index).getDessert().getName()        				 + "--" +
						 menus.get(index).getDessert().getDescription() 				 + "--" +
						 Integer.toString(menus.get(index).getDessert().getCalories())  + "--" +
						 Double.toString(menus.get(index).getDessert().getPrice()));
				totalCalories += menus.get(index).getDessert().getCalories();
				totalPrice += menus.get(index).getDessert().getPrice();
				bw.newLine();
				bw.write("    TOTALS Calories: " + Integer.toString(totalCalories) + 
						 "           Price: " + totalPrice);

				bw.newLine();		// insert blank line between menus
				bw.newLine();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(142);
			}
		}
		try {
			bw.close();
		}
		catch (IOException e) {
			e.printStackTrace();
			System.exit(143);
		}
	}
}
