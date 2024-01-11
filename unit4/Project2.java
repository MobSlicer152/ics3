import java.lang.System;

import java.util.Scanner;

public class Project2 {
    private static final double KILOMETRES_PER_MILE = 1.609;
    private static final int FEET_PER_MILE = 5280;

    private static void printRow(int n) {
        int miles1 = n;
        int feet1 = miles1 * FEET_PER_MILE;
        double kilometres1 = miles1 * KILOMETRES_PER_MILE;
        int kilometres2 = n;
        double miles2 = kilometres2 / KILOMETRES_PER_MILE;
        double feet2 = miles2 * FEET_PER_MILE;

        // - means pad right
        System.out.printf("%-7d%-7d%-11.3f |   %-9d  %6.3f   %7.3f\n", feet1, miles1, kilometres1, kilometres2, miles2, feet2);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of rows in the conversion table (minimum 6): ");
        int rows = scanner.nextInt();
        if (rows < 6) {
            rows = 5;
        }

        System.out.println("feet   miles  kilometres  |   kilometres  miles   feet");
        for (int i = 1; i <= rows; i++) {
            printRow(i);
        }
    }
}
