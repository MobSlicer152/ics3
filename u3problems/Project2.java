import java.lang.System;

import java.util.Scanner;

public class Project2 {
    private static int getMonthNumber(String month) {
        // Basically, regionMatches checks if part of the string is equal to
        // part of another. Here, it checks if the first three characters are
        // equal. This means the user can be lazy and it should still work.

        if (month.regionMatches(true, 0, "jan", 0, 3)) {
            return 13;
        } else if (month.regionMatches(true, 0, "feb", 0, 3)) {
            return 14;
        } else if (month.regionMatches(true, 0, "mar", 0, 3)) {
            return 3;
        } else if (month.regionMatches(true, 0, "apr", 0, 3)) {
            return 4;
        } else if (month.regionMatches(true, 0, "may", 0, 3)) {
            return 5;
        } else if (month.regionMatches(true, 0, "jun", 0, 3)) {
            return 6;
        } else if (month.regionMatches(true, 0, "jul", 0, 3)) {
            return 7;
        } else if (month.regionMatches(true, 0, "aug", 0, 3)) {
            return 8;
        } else if (month.regionMatches(true, 0, "sep", 0, 3)) {
            return 9;
        } else if (month.regionMatches(true, 0, "oct", 0, 3)) {
            return 10;
        } else if (month.regionMatches(true, 0, "nov", 0, 3)) {
            return 11;
        } else if (month.regionMatches(true, 0, "dec", 0, 3)) {
            return 12;
        } else {
            return 0;
        }
    }

    private static String getWeekDay(int h) {
        switch (h) {
        case 0:
            return "Saturday";
        case 1:
            return "Sunday";
        case 2:
            return "Monday";
        case 3:
            return "Tuesday";
        case 4:
            return "Wednesday";
        case 5:
            return "Thursday";
        case 6:
            return "Friday";
        default:
            return "Unknown";
        }        
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the year: ");
        int year = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter the month: ");
        String month = scanner.nextLine();
        System.out.print("Enter the day of the month: ");
        int q = scanner.nextInt();
        scanner.nextLine();

        int m = getMonthNumber(month);
        int k = year % 100;
        int j = year / 100;

        System.out.printf("year %d m %d q %d k %d j %d\n", year, m, q, k, j);

        int h = (q + ((13 * (m + 1)) / 5) + k + (k / 4) + (j / 4) + (5 * j)) % 7;

        System.out.printf("%s %d, %d is/was a %s\n", month, q, year, getWeekDay(h));
    }
}

