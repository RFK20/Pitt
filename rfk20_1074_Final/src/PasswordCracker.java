import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class PasswordCracker {
	private static Scanner input;
	private static File dictFile = new File("data/dict.txt");
	private static Scanner dictReader = null;
	private static StringBuilder passwordRegisteredMD5 = new StringBuilder();
	private static ArrayList<String> strPrefixesSuffixes = new ArrayList<String>();
	private static String dictWordConstructed = "";
	/*
	 * This password cracker uses two techniques:
	 *   1. An MD5 message digest is computed for each word in the dictionary. If a digest corresponds to the
	 *      credentials.txt MD5, the password is regarded as weak. 
	 *   2. If no dictionary word corresponds to the credentials.txt MD5, a set of Type 2 passwords are constructed for each dictionary word.
	 *      A Type 2 password consists of the dictionary word with a prefix of one to four characters &/or a suffix of one to four characters.
	 *      The prefix & suffix are chosen from this set of 15 characters: {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, @, #, $, %, &}. 
	 *      And there are at most two numerical characters and at most two special characters in the password string.
	 *      For the word 'authentic', for example, some of the passwords constructed are:
	 * 		authentic#@
	 *      $authentic%N	N ranges from 0 through 9
	 * 		N&authentic		N ranges from 0 through 9
	 * 		authenticN		N ranges from 0 through 9
	 * 		If the digest of a Type 2 password corresponds to the credentials.txt MD5, the password is regarded as moderate. 
	 */
	public static void main(String[] args) {
		String user = null;
		long elapsedTimeCrackProcess = 0;

		input = new Scanner(System.in);
    	System.out.println(" Password Cracker. Type 'quit' when done. ");

		for (;;) {		// user name loop
			for (;;) {
				System.out.printf(" Username: ");
				user = input.next();
				if (user.equals("quit"))
			    	System.exit(0);
				passwordRegisteredMD5.setLength(0);
				// Retrieve this registered user's MD5 password. 
				if (Registration.userRegistered(user, passwordRegisteredMD5)) {
				    System.out.println("This user is registered. The program will attempt to crack the password.");
				    break;
				}
				else {
				    System.out.println("This user is NOT registered. Enter a valid user name or register this user.");
				    continue;
				}
			}
		
			elapsedTimeCrackProcess = 0;
			long startTimeCrackProcess = System.currentTimeMillis();
			
			if (type1PasswordFound()) {
				System.out.println("  The strength of this password is weak.");
				elapsedTimeCrackProcess = System.currentTimeMillis() - startTimeCrackProcess;
				System.out.println("  Seconds spent cracking the password = " + ((double) elapsedTimeCrackProcess / (double) 1000));
	
			} else {
				// On the first check of a Type 2 password, construct an array of strings that will be used as prefixes & suffixes for each dictionary word.
				if (strPrefixesSuffixes.size() == 0) {
					constructPrefixesSuffixes();
				}
				if (type2PasswordFound()) {
					System.out.println("  The strength of this password is moderate.");
					elapsedTimeCrackProcess = System.currentTimeMillis() - startTimeCrackProcess;
					System.out.println("  Seconds spent trying to crack the password = " + ((double) elapsedTimeCrackProcess / (double) 1000));
				}
				else {
					System.out.println("NOT Cracked: The MD5 password Message Digest could not be determined.");
					System.out.println("  The strength of this password is strong.");
					elapsedTimeCrackProcess = System.currentTimeMillis() - startTimeCrackProcess;
					System.out.println("  Seconds spent trying to crack the password = " + ((double) elapsedTimeCrackProcess / (double) 1000));
				}
			}
		}
	} // main
			
	
	public static boolean type1PasswordFound() {
		Scanner dictReader = null;
		String dictWord = "";
		String md5 = null;
		Boolean result = false;

		try {
			dictReader = new Scanner(dictFile);
		}
		catch (FileNotFoundException e) {
			System.err.println("dictionary file not found" + e);
			System.exit(500);
		}

		while (dictReader.hasNextLine()) {
			dictWord = dictReader.nextLine();

			// Try Type 1 (dictionary word).
			// Convert dictionary word to MD5 message digest.
			try {
				// Create MessageDigest object for MD5. 
				MessageDigest digest = MessageDigest.getInstance("MD5");
				//Update input string in message digest
				digest.update(dictWord.getBytes(), 0, dictWord.length());
				//Converts message digest value in base 16 (hex)
				md5 = new BigInteger(1, digest.digest()).toString(16);
			} catch (NoSuchAlgorithmException e) {
				System.err.println("ERROR 508: " + e);
		    	System.exit(508);
			}
			if (md5.equals(passwordRegisteredMD5.toString())) {
				System.out.println("Cracked: MD5 password Message Digest corresponds to this string: " + dictWord);
				result = true;
				break;
			}	
		}
		try {
			dictReader.close();
		}
		catch (Exception e) {
			System.err.println("ERROR 506: " + e);
	    	System.exit(506);
		}

		return result;
	} // type1PasswordFound
					
					
	public static boolean type2PasswordFound() {
		String dictWord = "";
		String md5 = null;
		MessageDigest md = null;
		byte[] bmsgDigest = null;		
		Boolean result = false;
		char savedictWord = ' ';
		int countType2Strings = 0;
		int nMode = 0;

		try {
			// Create MessageDigest object for MD5.
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			System.err.println("ERROR 608: " + e);
	    	System.exit(608);
		}
		
		try {
			dictReader = new Scanner(dictFile);
		}
		catch (FileNotFoundException e) {
			System.err.println("dictionary file not found" + e);
			System.exit(600);
		}

		while (dictReader.hasNextLine()) {
			dictWord = dictReader.nextLine();
			nMode = 0;
			
			// Show progress message since processing may take a large amount of time.
			if (savedictWord != Character.toUpperCase(dictWord.charAt(0))) {
				savedictWord = Character.toUpperCase(dictWord.charAt(0));
				System.out.println("Processing Type 2 passwords based on dictionary words beginning with: " + savedictWord);
			}

			// Construct Type 2 using numeric & special characters prefixed to dictionary word. For example, '8authentic'.
			for (int index = 0; index < strPrefixesSuffixes.size(); index++) {
				dictWordConstructed = strPrefixesSuffixes.get(index) + dictWord;
				if ((++countType2Strings)%10000000 == 0) {
					System.out.println("Strings processed for Type 2 passwords = " + countType2Strings);
				}
				
				// Convert dictionary word to MD5 message digest.
				// Update input string in message digest.
				bmsgDigest = md.digest(dictWordConstructed.getBytes());
				// Converts message digest value in base 16 (hex).
				md5 = new BigInteger(1, bmsgDigest).toString(16);
				if (md5.equals(passwordRegisteredMD5.toString())) {
					System.out.println("Cracked: MD5 password Message Digest corresponds to this string: " + dictWordConstructed);
					result = true;
					break;
				}	
			}
			if (result == true)
				break;
			
			// Construct Type 2 using numeric & special characters suffixed to dictionary word. For example, 'authentic8'.
			for (int index = 0; index < strPrefixesSuffixes.size(); index++) {
				dictWordConstructed = dictWord + strPrefixesSuffixes.get(index);
				if ((++countType2Strings)%10000000 == 0) {
					System.out.println("Strings processed for Type 2 passwords = " + countType2Strings);
				}
				
				// Convert dictionary word to MD5 message digest.
				// Update input string in message digest.
				bmsgDigest = md.digest(dictWordConstructed.getBytes());
				// Converts message digest value in base 16 (hex).
				md5 = new BigInteger(1, bmsgDigest).toString(16);
				if (md5.equals(passwordRegisteredMD5.toString())) {
					System.out.println("Cracked: MD5 password Message Digest corresponds to this string: " + dictWordConstructed);
					result = true;
					break;
				}	
				
				// Construct Type 2 using numeric & special characters surrounding a dictionary word. For example, '$authentic%4'.
				// Is Type 2 length less than or equal to 1? If so, it has already been checked.
				if (strPrefixesSuffixes.get(index).length() <= 1)
					continue;
				for (;;) {
					dictWordConstructed = "" + strPrefixesSuffixes.get(index).charAt(0);
					switch (nMode) {
						case 0:		
							// Covers the case '$authentic%'.
							dictWordConstructed += dictWord + strPrefixesSuffixes.get(index).charAt(1);
							if (strPrefixesSuffixes.get(index).length() == 2) {
								break;
							}
							// Covers the case '$%authentic4'.
							dictWordConstructed += strPrefixesSuffixes.get(index).charAt(1) + dictWord + strPrefixesSuffixes.get(index).charAt(2);
							if (strPrefixesSuffixes.get(index).length() == 4) {
								dictWordConstructed += strPrefixesSuffixes.get(index).charAt(3);		// Covers the case '$%authentic45'.
							}
							break;
						case 1:		// Covers the case '$authentic%4'.
							dictWordConstructed += dictWord + strPrefixesSuffixes.get(index).charAt(1) + strPrefixesSuffixes.get(index).charAt(2);
							if (strPrefixesSuffixes.get(index).length() == 4) {
								dictWordConstructed += strPrefixesSuffixes.get(index).charAt(3);		// Covers the case '$authentic%45'.
							}
							break;
						case 2:
							// Covers the case '$%4authentic5'.
							dictWordConstructed += strPrefixesSuffixes.get(index).charAt(1) + strPrefixesSuffixes.get(index).charAt(2) + dictWord + strPrefixesSuffixes.get(index).charAt(3);
							break;
					}
					if ((++countType2Strings)%10000000 == 0) {
						System.out.println("Strings processed for Type 2 passwords = " + countType2Strings);
					}
					
					// Convert dictionary word to MD5 message digest.
					// Update input string in message digest.
					bmsgDigest = md.digest(dictWordConstructed.getBytes());
					// Converts message digest value in base 16 (hex).
					md5 = new BigInteger(1, bmsgDigest).toString(16);
					if (md5.equals(passwordRegisteredMD5.toString())) {
						System.out.println("Cracked: MD5 password Message Digest corresponds to this string: " + dictWordConstructed);
						result = true;
						break;
					}	
					if (strPrefixesSuffixes.get(index).length() == 2) {
						break;
					}
					if ((nMode == 1) && (strPrefixesSuffixes.get(index).length() == 3)) {
						break;
					}
					if (nMode == 2) {
						break;
					}
					nMode++;
				}
				if (result == true) {
					break;
				}
			}
		}
		try {
			dictReader.close();
		}
		catch (Exception e) {
			System.err.println("ERROR 506: " + e);
	    	System.exit(506);
		}

		return result;
	} // type2PasswordFound
	
	
	public static void constructPrefixesSuffixes() {
		String[] strType2Chars = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "@", "#", "$", "%", "&"};
		int firstCharIndex = 0;
		int secondCharIndex = 0;
		int thirdCharIndex = 0;
		int fourthCharIndex = 0;
		int totalPrefixesSuffixes = 0;
		
		// ONE CHARACTER PREFIXES/SUFFIXES--Initialize strings consisting of:
		//  one of the 15 Type 2 characters. 
		//  (15 strings)
		for (firstCharIndex = 0; firstCharIndex < strType2Chars.length; firstCharIndex++) {
			strPrefixesSuffixes.add(strType2Chars[firstCharIndex]);	
			totalPrefixesSuffixes++;
		}
		/* *********************************************************************************** */
		
		// TWO CHARACTER PREFIXES/SUFFIXES--Initialize strings consisting of:
		//  one of the 15 Type 2 characters followed by
		//  Type 2 character.
		//  (225 strings) 
		for (firstCharIndex = 0; firstCharIndex < strType2Chars.length; firstCharIndex++ ) {
			for (secondCharIndex = 0; secondCharIndex < strType2Chars.length; secondCharIndex++) {
				strPrefixesSuffixes.add(strType2Chars[firstCharIndex] + strType2Chars[secondCharIndex]);	
				totalPrefixesSuffixes++;
			}
			
		}
		/* *********************************************************************************** */

		// 3 CHARACTER STRINGS.
		// THREE CHARACTER PREFIXES/SUFFIXES--Initialize strings consisting of:					1
		//  numeric character followed by 
		//  numeric character followed by 
		//  non-numeric character. 
		//  (500 strings) 
		for (firstCharIndex = 0; firstCharIndex < 10; firstCharIndex++ ) {
			for (secondCharIndex = 0; secondCharIndex < 10; secondCharIndex++) {
				for (thirdCharIndex = 10; thirdCharIndex < strType2Chars.length; thirdCharIndex++) {
					strPrefixesSuffixes.add(strType2Chars[firstCharIndex] + strType2Chars[secondCharIndex] + strType2Chars[thirdCharIndex]);	
					totalPrefixesSuffixes++;
				}
			}
		}
		/* *********************************************************************************** */

		// THREE CHARACTER PREFIXES/SUFFIXES--Initialize strings consisting of:					2
		//  numeric character followed by 
		//  non-numeric character followed by 
		//  non-numeric character. 
		//  (250 strings) 
		for (firstCharIndex = 0; firstCharIndex < 10; firstCharIndex++ ) {
			for (secondCharIndex = 10; secondCharIndex < strType2Chars.length; secondCharIndex++) {
				for (thirdCharIndex = 10; thirdCharIndex < strType2Chars.length; thirdCharIndex++) {
					strPrefixesSuffixes.add(strType2Chars[firstCharIndex] + strType2Chars[secondCharIndex] + strType2Chars[thirdCharIndex]);	
					totalPrefixesSuffixes++;
				}
			}
		}
		/* *********************************************************************************** */

		// THREE CHARACTER PREFIXES/SUFFIXES--Initialize strings consisting of:					3
		//  non-numeric character followed by 
		//  non-numeric character followed by 
		//  numeric character. 
		//  (250 strings) 
		for (firstCharIndex = 10; firstCharIndex < strType2Chars.length; firstCharIndex++ ) {
			for (secondCharIndex = 10; secondCharIndex < strType2Chars.length; secondCharIndex++) {
				for (thirdCharIndex = 0; thirdCharIndex < 10; thirdCharIndex++) {
					strPrefixesSuffixes.add(strType2Chars[firstCharIndex] + strType2Chars[secondCharIndex] + strType2Chars[thirdCharIndex]);	
					totalPrefixesSuffixes++;
				}
			}
		}
		/* *********************************************************************************** */
		
		// THREE CHARACTER PREFIXES/SUFFIXES--Initialize strings consisting of:					4
		//  non-numeric character followed by 
		//  numeric character followed by 
		//  numeric character. 
		//  (500 strings) 
		for (firstCharIndex = 10; firstCharIndex < strType2Chars.length; firstCharIndex++ ) {
			for (secondCharIndex = 0; secondCharIndex < 10; secondCharIndex++) {
				for (thirdCharIndex = 0; thirdCharIndex < 10; thirdCharIndex++) {
					strPrefixesSuffixes.add(strType2Chars[firstCharIndex] + strType2Chars[secondCharIndex] + strType2Chars[thirdCharIndex]);	
					totalPrefixesSuffixes++;
				}
			}
		}
		/* *********************************************************************************** */
		
		// THREE CHARACTER PREFIXES/SUFFIXES--Initialize strings consisting of:					5
		//  non-numeric character followed by 
		//  numeric character followed by 
		//  non-numeric character. 
		//  (250 strings) 
		for (firstCharIndex = 10; firstCharIndex < strType2Chars.length; firstCharIndex++ ) {
			for (secondCharIndex = 0; secondCharIndex < 10; secondCharIndex++) {
				for (thirdCharIndex = 10; thirdCharIndex < strType2Chars.length; thirdCharIndex++) {
					strPrefixesSuffixes.add(strType2Chars[firstCharIndex] + strType2Chars[secondCharIndex] + strType2Chars[thirdCharIndex]);	
					totalPrefixesSuffixes++;
				}
			}
		}
		/* *********************************************************************************** */

		// THREE CHARACTER PREFIXES/SUFFIXES--Initialize strings consisting of:					6
		//  numeric character followed by 
		//  non-numeric character followed by 
		//  numeric character. 
		//  (500 strings) 
		for (firstCharIndex = 0; firstCharIndex < 10; firstCharIndex++ ) {
			for (secondCharIndex = 10; secondCharIndex < strType2Chars.length; secondCharIndex++) {
				for (thirdCharIndex = 0; thirdCharIndex < 10; thirdCharIndex++) {
					strPrefixesSuffixes.add(strType2Chars[firstCharIndex] + strType2Chars[secondCharIndex] + strType2Chars[thirdCharIndex]);	
					totalPrefixesSuffixes++;
				}
			}
		}
		/* *********************************************************************************** */

		// 4 CHARACTER STRINGS.
		// FOUR CHARACTER PREFIXES/SUFFIXES--Initialize strings consisting of:					1
		//  numeric character followed by 
		//  numeric character followed by 
		//  non-numeric character followed by 
		//  non-numeric character. 
		//  (2500 strings) 
		for (firstCharIndex = 0; firstCharIndex < 10; firstCharIndex++ ) {
			for (secondCharIndex = 0; secondCharIndex < 10; secondCharIndex++) {
				for (thirdCharIndex = 10; thirdCharIndex < strType2Chars.length; thirdCharIndex++) {
					for (fourthCharIndex = 10; fourthCharIndex < strType2Chars.length; fourthCharIndex++) {
						strPrefixesSuffixes.add(strType2Chars[firstCharIndex] + strType2Chars[secondCharIndex] + strType2Chars[thirdCharIndex] + strType2Chars[fourthCharIndex]);	
						totalPrefixesSuffixes++;
					}
				}
			}
		}
		/* *********************************************************************************** */

		// FOUR CHARACTER PREFIXES/SUFFIXES--Initialize strings consisting of:					2
		//  numeric character followed by 
		//  non-numeric character followed by 
		//  non-numeric character followed by 
		//  numeric character.
		//  (2500 strings) 
		for (firstCharIndex = 0; firstCharIndex < 10; firstCharIndex++ ) {
			for (secondCharIndex = 10; secondCharIndex < strType2Chars.length; secondCharIndex++) {
				for (thirdCharIndex = 10; thirdCharIndex < strType2Chars.length; thirdCharIndex++) {
					for (fourthCharIndex = 0; fourthCharIndex < 10; fourthCharIndex++) {
						strPrefixesSuffixes.add(strType2Chars[firstCharIndex] + strType2Chars[secondCharIndex] + strType2Chars[thirdCharIndex] + strType2Chars[fourthCharIndex]);	
						totalPrefixesSuffixes++;
					}
				}
			}
		}
		/* *********************************************************************************** */

		// FOUR CHARACTER PREFIXES/SUFFIXES--Initialize strings consisting of:					3
		//  non-numeric character followed by 
		//  non-numeric character followed by 
		//  numeric character followed by 
		//  numeric character.
		//  (2500 strings) 
		for (firstCharIndex = 10; firstCharIndex < strType2Chars.length; firstCharIndex++ ) {
			for (secondCharIndex = 10; secondCharIndex < strType2Chars.length; secondCharIndex++) {
				for (thirdCharIndex = 0; thirdCharIndex < 10; thirdCharIndex++) {
					for (fourthCharIndex = 0; fourthCharIndex < 10; fourthCharIndex++) {
						strPrefixesSuffixes.add(strType2Chars[firstCharIndex] + strType2Chars[secondCharIndex] + strType2Chars[thirdCharIndex] + strType2Chars[fourthCharIndex]);	
						totalPrefixesSuffixes++;
					}
				}
			}
		}
		/* *********************************************************************************** */

		// FOUR CHARACTER PREFIXES/SUFFIXES--Initialize strings consisting of:					4
		//  non-numeric character followed by 
		//  numeric character followed by 
		//  numeric character followed by 
		//  non-numeric character.
		//  (2500 strings) 
		for (firstCharIndex = 10; firstCharIndex < strType2Chars.length; firstCharIndex++ ) {
			for (secondCharIndex = 0; secondCharIndex < 10; secondCharIndex++) {
				for (thirdCharIndex = 0; thirdCharIndex < 10; thirdCharIndex++) {
					for (fourthCharIndex = 10; fourthCharIndex < strType2Chars.length; fourthCharIndex++) {
						strPrefixesSuffixes.add(strType2Chars[firstCharIndex] + strType2Chars[secondCharIndex] + strType2Chars[thirdCharIndex] + strType2Chars[fourthCharIndex]);	
						totalPrefixesSuffixes++;
					}
				}
			}
		}
		/* *********************************************************************************** */

		// FOUR CHARACTER PREFIXES/SUFFIXES--Initialize strings consisting of:					5
		//  non-numeric character followed by 
		//  numeric character followed by 
		//  non-numeric character followed by 
		//  numeric character.
		//  (2500 strings) 
		for (firstCharIndex = 10; firstCharIndex < strType2Chars.length; firstCharIndex++ ) {
			for (secondCharIndex = 0; secondCharIndex < 10; secondCharIndex++) {
				for (thirdCharIndex = 10; thirdCharIndex < strType2Chars.length; thirdCharIndex++) {
					for (fourthCharIndex = 0; fourthCharIndex < 10; fourthCharIndex++) {
						strPrefixesSuffixes.add(strType2Chars[firstCharIndex] + strType2Chars[secondCharIndex] + strType2Chars[thirdCharIndex] + strType2Chars[fourthCharIndex]);	
						totalPrefixesSuffixes++;
					}
				}
			}
		}
		/* *********************************************************************************** */

		// FOUR CHARACTER PREFIXES/SUFFIXES--Initialize strings consisting of:					6
		//  numeric character followed by 
		//  non-numeric character followed by 
		//  numeric character followed by 
		//  non-numeric character. 
		//  (2500 strings) 
		for (firstCharIndex = 0; firstCharIndex < 10; firstCharIndex++ ) {
			for (secondCharIndex = 10; secondCharIndex < strType2Chars.length; secondCharIndex++) {
				for (thirdCharIndex = 0; thirdCharIndex < 10; thirdCharIndex++) {
					for (fourthCharIndex = 10; fourthCharIndex < strType2Chars.length; fourthCharIndex++) {
						strPrefixesSuffixes.add(strType2Chars[firstCharIndex] + strType2Chars[secondCharIndex] + strType2Chars[thirdCharIndex] + strType2Chars[fourthCharIndex]);	
						totalPrefixesSuffixes++;
					}
				}
			}
		}
		/* *********************************************************************************** */

		System.out.println("Prefix/Suffix string list length = " + totalPrefixesSuffixes);
	} // constructPrefixesSuffixes
	
}
