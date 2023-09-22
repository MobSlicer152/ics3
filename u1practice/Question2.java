import java.lang.System;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import java.util.Scanner;

public class Question2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
        LocalDate today = LocalDate.now();

        System.out.printf("Enter your birthday (in the form %s): ", formatter.format(today));
        String dateRaw = scanner.nextLine();

        LocalDateTime now = LocalDateTime.now();
        LocalDate date = LocalDate.parse(dateRaw, formatter);
        LocalDateTime birth = date.atStartOfDay();

        DateTimeFormatter outFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        Duration between = Duration.between(birth, now);
        System.out.printf("As of %s, your age is:\n%d years\n%d months\n%d days\n%d hours\n%d minutes\n%d seconds", outFormatter.format(now), 
                between.toDays() / 365,
                (int)(between.toDays() / 30.5), // average length of month
                between.toHours() / 24,
                between.toHours(),
                between.toMinutes(),
                between.getSeconds());
    }
}

