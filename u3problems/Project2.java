import java.lang.System;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Scanner;

public class Project2 {
    private static int getMonthNumber(String month) {
        // Basically, regionMatches checks if part of the string is equal to
        // part of another. Here, it checks if the first three characters are
        // equal. This means the user can be lazy and it should still work.

        if (month.regionMatches(true, 0, "jan", 0, 3)) {
            return Calendar.JANUARY;
        } else if (month.regionMatches(true, 0, "feb", 0, 3)) {
            return Calendar.FEBRUARY;
        } else if (month.regionMatches(true, 0, "mar", 0, 3)) {
            return Calendar.MARCH;
        } else if (month.regionMatches(true, 0, "apr", 0, 3)) {
            return Calendar.APRIL;
        } else if (month.regionMatches(true, 0, "may", 0, 3)) {
            return Calendar.MAY;
        } else if (month.regionMatches(true, 0, "jun", 0, 3)) {
            return Calendar.JUNE;
        } else if (month.regionMatches(true, 0, "jul", 0, 3)) {
            return Calendar.JULY;
        } else if (month.regionMatches(true, 0, "aug", 0, 3)) {
            return Calendar.AUGUST;
        } else if (month.regionMatches(true, 0, "sep", 0, 3)) {
            return Calendar.SEPTEMBER;
        } else if (month.regionMatches(true, 0, "oct", 0, 3)) {
            return Calendar.OCTOBER;
        } else if (month.regionMatches(true, 0, "nov", 0, 3)) {
            return Calendar.NOVEMBER;
        } else if (month.regionMatches(true, 0, "dec", 0, 3)) {
            return Calendar.DECEMBER;
        } else {
            return 0;
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
        int mday = scanner.nextInt();
        scanner.nextLine();

        int nmonth = getMonthNumber(month);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, nmonth, mday);

        System.out.println(new SimpleDateFormat("EEEE").format(calendar.getTime()));
    }
}

