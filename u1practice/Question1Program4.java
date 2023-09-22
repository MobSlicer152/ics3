import java.lang.System;

import java.util.Scanner;

public class Question1Program4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a number of seconds: ");
        int seconds = scanner.nextInt();

        int minutes = seconds / 60;
        int hours = minutes / 60;
        seconds -= minutes * 60;
        minutes -= hours * 60;

        System.out.printf("%d hour(s), %d minute(s), %d second(s)\n", hours, minutes, seconds);
    }
}

