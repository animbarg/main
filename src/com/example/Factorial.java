package com.example;

import java.util.Scanner;

public class Factorial {

	public static void main(String[] args) {
		
		Scanner myObj = new Scanner(System.in);  // Create a Scanner object
	    System.out.println("Enter Number");

	    Integer n = myObj.nextInt();
		
		factorial1(n);

	}

	static void factorial1(int n) {
		int res[] = new int[158];

		res[0] = 1;
		int res_size = 1;


		// n! = 1 * 2 * 3 * 4...*n
		for (int x = 2; x <= n; x++)
			res_size = multiply(x, res, res_size);

		System.out.println("First 10 digit of Factorial of given number is ");

		if (res_size < 10)
			for (int i = res_size - 1; i >= 0; i--)
				System.out.print(res[i]);
		else if (res_size >= 10)
			for (int i = res_size - 1; i >= res_size - 10; i--)
				System.out.print(res[i]);
	}



	private static int multiply(int x, int[] res, int res_size) {
		int carry = 0;
		for (int i = 0; i < res_size; i++) {
			int prod = res[i] * x + carry;
			res[i] = prod % 10;
			carry = prod / 10;
		}

		while (carry != 0) {
			res[res_size] = carry % 10;
			carry = carry / 10;
			res_size++;
		}
		return res_size;
	}

}
