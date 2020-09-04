package com.algos.pattern;
import java.util.Scanner;

public class PatternMatching {
	static int no_of_comparisons1 = 0;
	static int no_of_comparisons2 = 0;
	static int no_of_comparisons3 = 0;
	
	public static void main(String args[]) {
		
	    int position1;
		String Text, Pattern;
		long startTime1, endTime1;
		Scanner input = new Scanner(System.in);

		System.out.print("Enter the Text = ");
		Text = input.nextLine();

		System.out.print("Enter the Pattern = ");
		Pattern = input.nextLine();
		
		System.out.println("\n BruteForce algorithm");
		System.out.println("-----------------------------------------------------");
		
		startTime1 = System.nanoTime();
		position1 = BruteForce(Text, Pattern);

		if (position1 == -1)
		{
			System.out.print("\nNo match found for the pattern "+Pattern+" in text "+Text );
			endTime1 = System.nanoTime();
		} 
		else 
		{
			System.out.println("\nMatch has found at the position number " + position1);
			System.out.println();
			System.out.println(Text);
			for (int i = 0; i < position1; i++)
				System.out.print(" ");
			System.out.println(Pattern);
			System.out.println();
			endTime1 = System.nanoTime();
		}
		
		System.out.println("Total number of comparisons (BruteForce algorithm) = " + no_of_comparisons1);
		System.out.println();
		long timeDuration1 = endTime1-startTime1;
		System.out.println("Total time taken for execution (BruteForce algorithm) = " + timeDuration1 + "nanoseconds");
			
		///////////////////////////////////////////////////////////////////////////////////////////////////
		
		System.out.println("\n\nBoyerMooreHorspool");
		System.out.println("-----------------------------------------------------");
		
		int position2;
		long startTime2, endTime2;

		startTime2 = System.nanoTime();
		position2 = BoyerMooreHorspool(Text, Pattern);
		
		if (position2 == -1) 
		{
			System.out.print("No match found for the pattern "+Pattern+" in text "+Text );
			endTime2 = System.nanoTime();
		} 
		else
		{
			System.out.println("\nMatch has found at the position number " + position2);
			endTime2 = System.nanoTime();
		}
		
		System.out.println();
		System.out.println(Text);
		for (int j = 0; j < position2; j++)
			System.out.print(" ");
		System.out.println(Pattern);
	
		System.out.println("\nTotal number of comparisons (BoyerMooreHorspool algorithm) = " + no_of_comparisons2);
		System.out.println();
		long timeDuration2 = endTime2-startTime2;
		System.out.println("Total time taken for execution (BoyerMooreHorspool algorithm) = " + timeDuration2 + "nanoseconds");
		
		
		//////////////////////////////////////////////////////////////////////////////////////////////
		
		
		System.out.println("\n\nKnuthMorrisPratt");
		System.out.println("-----------------------------------------------------");
		
		int position3;
		long startTime3, endTime3;

		startTime3 = System.nanoTime();
        int[] failure = failureFunction(Pattern);
		position3 = KnuthMorrisPratt(Text, Pattern, failure);

		if (position3 == -1) 
		{
			System.out.println("No match found for the pattern "+Pattern+" in text "+Text);
			endTime3 = System.nanoTime();
		} 
		else 
		{
			System.out.println("\nMatch has found at the position number " + position3);
			endTime3 = System.nanoTime();
		}

		System.out.println();
		System.out.println(Text);
		for (int k = 0; k < position3; k++)
			System.out.print(" ");
		System.out.println(Pattern);

		System.out.println("\nTotal number of comparisons (KnuthMorrisPratt  Algorithm) = " + (no_of_comparisons3));
		System.out.println();
		long timeDuration3 = endTime3-startTime3;
		System.out.println("Total time taken for execution (KnuthMorrisPratt Algorithm) = " + timeDuration3 + "nanoseconds");

	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	//BruteForce Algorithm
	public static int BruteForce(String Text, String Pattern) {
		
		int n = Text.length();
		int m = Pattern.length();

		for (int i = 0; i <= n-m; i++) {
			int j;
			for (j = 0; j < m; j++) 
			{
				no_of_comparisons1++;
				if (Text.charAt(i + j) != Pattern.charAt(j))
					break;
			}
			if (j == m) 
			{
				return (i);
			}
		}
		return (-1);
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//BoyerMooreHorspool Algorithm
	public static int BoyerMooreHorspool(String Text, String Pattern) {
		
		int n = Text.length();
		int m = Pattern.length();
		
		int shiftValue[] = new int[256];
		for (int k = 0; k < 256; k++) 
		{
			shiftValue[k] = m;
		}

		for (int k = 0; k < m - 1; k++)
		{
			shiftValue[Pattern.charAt(k)] = m - 1 - k;
		}

		int i = 0, j = 0;
		
		while ((i + m) <= n)
		{
			j = m - 1;
			while (Text.charAt(i + j) == Pattern.charAt(j)) 
			
			{
				j -= 1;
				no_of_comparisons2++;
				if (j < 0) {
					return i;
				}
			}
			no_of_comparisons2++;
			i = i + shiftValue[Text.charAt(i + m - 1)];
		}
		return -1;
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//KnuthMorrisPratt Algorithm
			public static int KnuthMorrisPratt(String Text, String Pattern, int[] failure) {
				int i = 0, j = 0, k = 0;
				int n = Text.length();
				int m = Pattern.length();

				while (j < n) {

					if (Pattern.charAt(k) == Text.charAt(j)) 
					{
						j++;
						k++;
						no_of_comparisons3++;
						if (k == m) // if the pattern is complete return the position
							return (i);
					}
					else 
					{
						if (k == 0) 
						{
							i++; 
							j = i;
							no_of_comparisons3++;
						} 
						else 
						{
							int e = failure[k - 1];
							k = e;
							i = (j - k);
							no_of_comparisons3++;
						}	
					}
				}

				return (-1);
			}
	
		public static int[] failureFunction(String Pattern) {
			
			int m = Pattern.length();
			int k = 1, x = 0;
			int failureFunction[] = new int[m];
			failureFunction[0] = 0;
			while (k < m) {
				if (Pattern.charAt(k) == Pattern.charAt(x)) 
				{
					failureFunction[k] = x + 1;
					k++;
					x++;
				} 
				else 
				{
					if (x == 0) 
					{
						failureFunction[k] = 0;
						k++;
					} 
					else 
					{
						x = failureFunction[x - 1];
					}
				}
			}
			return (failureFunction);
		}	
}
