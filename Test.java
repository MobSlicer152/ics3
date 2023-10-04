import java.lang.Math;
import java.lang.System;

import java.util.Scanner;

public class Test {
	public static void main(String[] args) {
		Scanner io = new Scanner(System.in);
		System.out.println("Enter the radius: ");
		double radius = io.nextDouble();

		double area = Math.PI * Math.pow(radius, 2);
		System.out.println("The area of the circle is " + area);
	}
}
