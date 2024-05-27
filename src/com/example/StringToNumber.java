package com.example;

import java.util.Scanner;

public class StringToNumber {

	public static void main(String[] args) {

		Scanner myObj = new Scanner(System.in); // Create a Scanner object
		System.out.println("Enter Number");
		String str = myObj.nextLine();
		stringToInteger(str);

	}

	private static void stringToInteger(String string) {

		char[] charArray = string.toCharArray();
		StringBuffer convertedString = new StringBuffer();

		for (int i = 0; i < charArray.length; i++) {
			if (Character.isDigit(charArray[i])) {
				convertedString.append(charArray[i]);
			} else
				continue;
		}

		if (convertedString.isEmpty())
			System.out.println("Input String does,t contain Integer value");
		else

			System.out.println("Converted value is " + Integer.parseInt(convertedString.toString()));

	}

}
