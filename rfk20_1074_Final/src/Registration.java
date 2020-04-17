import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;


/**
 * Class Registration
 * author : Rye Keating
 * created: 11/01/19
 */

public class Registration {
	private static Scanner input;
	private static String confirm;

	public static void main(String[] args) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		String user = null;
		String md5 = null;
		byte[] bmsgDigest = null;		
		String passwordEntered = null;
		String confirm1 = null;
		boolean uRegistered = false;
		StringBuilder passwordRegisteredMD5 = new StringBuilder();
		
		input = new Scanner(System.in);
    	System.out.println(" User Registration & Validation. Type 'quit' when done. ");

		File credFile = new File("data/credentials.txt");
		
		// If necessary, create credentials file.
		try {
			if (!credFile.exists()) {
				credFile.createNewFile();
			}
		} 
		catch (Exception e) {
	    	System.err.println("ERROR 1: " + e);
	    	System.exit(1);
		}
		
		/*
		 * 1. Get user name & password.
		 * 2. If user name is not registered, prompt user to add, then add it.
		 * 3. If user name is registered, validate the MD5 password.
		 * 4. Repeat 3 previous steps until user enters'quit'. 		
		 */
		
		for (;;) {
			System.out.printf(" Username: ");
			user = input.next();
			if (user.equals("quit"))
				break;
			passwordRegisteredMD5.setLength(0);
			if (uRegistered = userRegistered(user, passwordRegisteredMD5)) {
			    System.out.println("This user is registered. What is the password?");
			}
			else {
			    System.out.println("This user is NOT registered. Enter a password.");
			}
			
			System.out.printf(" Password: ");
			passwordEntered = input.next();
			
			if (!uRegistered) {
				// New registration.
				System.out.printf(" Confirm Password: ");
				setConfirm(input.next());
		
				confirm1 = getConfirm();
			
				if (passwordEntered.length() < 6) {
					System.out.println("Password Too short, password must be 6 characters or more");
					continue;
				}
			}

			// Convert entered password to MD5 message digest.
			try {
				//Create MessageDigest object for MD5
				MessageDigest md = MessageDigest.getInstance("MD5");
				//Update input string in message digest
				bmsgDigest = md.digest(passwordEntered.getBytes());
				//Converts message digest value in base 16 (hex)
				md5 = new BigInteger(1, bmsgDigest).toString(16);
			} catch (NoSuchAlgorithmException e) {
				System.err.println("ERROR 8: " + e);
		    	System.exit(8);
			}
			
			if (uRegistered) {
				if (md5.equals(passwordRegisteredMD5.toString())) {
					System.out.println("Success: Entered password Message Digest matches registered Digest.");
				}		
				else {
					System.out.println("Failure: Entered password Message Digest does not match registered Digest.");
				}
				continue;
			}
			
			if (passwordEntered.equals(confirm1)) {
				try {
					fw = new FileWriter(credFile, true);	// true designates append content to file
					bw = new BufferedWriter(fw);
					bw.write(user + " ");
					bw.write(" " + md5);
					bw.newLine();
					bw.close();
					System.out.println("You registered succesfully");
				}
				catch (Exception e) {
					System.err.println("ERROR 4: " + e);
			    	System.exit(4);
				}
			} else {
				System.out.println("Password and confirmed password do not match.");
			}
		}
	}

	
	public static boolean userRegistered(String enteredName, StringBuilder pWordMD5) {
		/*
		 * The credentials file has the format shown below.
		 * To determine if a user name has already been registered, the file is read line by line.
		 * The entered user name is compared with the user names in the file.
		 * 
		 * If the user name is registered, the password is returned in MD5 format.
		 * 
		 * The first field in each credentials line is the user name.
		 * The second field is the MD5 message digest of the password string. 
		 *   Sample credentials.txt file:
		 *     Tom		c51db16c6d1082e3b309f9307a74b42f
		 *     Harry	7fd9a35dfa69c58a7ef0ecb4d53a1651
		 */
		File fileCredentials = new File("data/credentials.txt"); 
		int linePosition = 0;
		int index = 0;
		String registeredName = "";
		boolean userAlreadyRegistered = false;

		Scanner myReader = null;
		try {
			myReader = new Scanner(fileCredentials);
		}
		catch (FileNotFoundException e) {
		    return userAlreadyRegistered;	
		}
		
		while (myReader.hasNextLine()) {
			String data = myReader.nextLine();

			// First space marks end of user name.
			linePosition = data.indexOf(" ", 0);
			// Was separator found?
			if (linePosition == -1) {
				System.err.println("credentials file invalid syntax line = " + data);
				System.exit(103);
			} else {
				registeredName = data.substring(0, linePosition); // registered user name
			}

			if (enteredName.equals(registeredName)) {
				userAlreadyRegistered = true;
				// Retrieve MD5 password. It starts with the first non-blank character following the user name.
				for (index = linePosition; ; index++) {
					if (index > data.length()) {
						System.err.println("credentials file missing MD5 password = " + data);
						System.exit(105);
					}
					if (data.charAt(index) != ' ') {
						pWordMD5.append(data.substring(index));
						break;
					}
				}
				break;
			}
		}
		myReader.close();
		return userAlreadyRegistered;
	}
	
	
	public static String getConfirm() {
		return confirm;
	}

	public static void setConfirm(String confirm) {
		Registration.confirm = confirm;
	}
}
