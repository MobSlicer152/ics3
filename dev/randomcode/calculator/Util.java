package dev.randomcode.calculator;

import java.lang.Double;
import java.lang.Enum;
import java.lang.Long;
import java.lang.System;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Util {
    // Get an int in a range with a prompt
    public static int getIntInRange(Scanner scanner, String message, int min, int max, boolean addRange) {
        int result;
        do {
            // Whether to print e.g. " (1-6): " or not
            if (addRange) {
                System.out.printf("%s (%d-%d): ", message, min, max);
            } else {
                System.out.print(message);
            }

            // Prevent invalid input from killing the program
            try {
                result = scanner.nextInt();
            } catch (InputMismatchException e) {
                result = 0;
                scanner.nextLine();
                continue;
            }
            scanner.nextLine();
        } while (result < min || result > max);

        return result;
    }

    // Get a double
    public static double getValidDouble(Scanner scanner, String message) {
        double result = 0;
        boolean invalid = false;

        do {
            System.out.print(message);

            try {
                result = scanner.nextDouble();
                invalid = false;
            } catch (InputMismatchException e) {
                invalid = true;
                scanner.nextLine();
                continue;
            }
            scanner.nextLine();
        } while (invalid);

        return result;
    }

    // Display a menu of the values of any enum and let the user pick a valid constant, or null to go back
    public static <E extends Enum<E>> E displayMenu(Scanner scanner, Class<E> enumClass, String header) {
        int i = 1;
        E option;

        System.out.print(header);

        // Print the enum's constants
        for (E e : enumClass.getEnumConstants()) {
            System.out.printf("%d. %s\n", i++, e);
        }

        System.out.printf("0. Leave\n");

        System.out.println();
        i = Util.getIntInRange(scanner, "Enter the number of the option you want", 0, enumClass.getEnumConstants().length, true);

        // Get the value
        if (i == 0) {
            return null;
        } else {
            return enumClass.getEnumConstants()[i - 1];
        }
    }
}