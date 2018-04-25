package math;

import utilities.Arrays;
import java.util.Scanner;

public class Primes {
	
	public static void main(String[] args) {
		int number = getNumber();
		int[] primes = getPrimes(number);
		for(int i = 0; i < primes.length; i++) {
			if(i != 0) {
				System.out.print(" ");
			}
			System.out.print(primes[i]);
		}
	}
	
	public static int getNumber() {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter a number: ");
		int number = input.nextInt();
		input.close();
		return number;
	}
	
	public static int[] getPrimes(int number) {
		int[] primes = {};
		for(int i = 2; i <= number; i++) {
			boolean isPrime = true;
			for(int k = 0; k < primes.length; k++) {
				if(i % primes[k] == 0) {
					isPrime = false;
				}
			}
			if(isPrime) {
				primes = Arrays.append(primes, i);
			}
		}
		return primes;
	}
	
}
