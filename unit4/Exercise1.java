import java.lang.System;

import java.util.Scanner;

public class Exercise1
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        int a;
        int b;
	int distanceA;
	int distanceB;

        System.out.print("Enter A: ");
        a = scanner.nextInt();
        System.out.print("Enter B: ");
        b = scanner.nextInt();

	distanceA = a;
	distanceB = b;
        while ( distanceA != distanceB )
	{
	    if ( distanceA < distanceB )
	    {
	        distanceA += a;
	    }
	    else if ( distanceB < distanceA )
	    {
	        distanceB += b;
	    }
	}

	System.out.println("distanceA = distanceB at " + distanceA);
    }
}
