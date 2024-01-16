package calculator;

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
                scanner.next(); // flush the scanner
                continue;
            }
        } while (result < min || result > max);

        return result;
    }

    // Display a menu of the values of any enum and let the user pick a valid constant
    public static <E extends Enum<E>> E displayMenu(Scanner scanner, Class<E> enumClass, String header) {
        int i = 1;
        E option;

        System.out.print(header);

        // Print the enum's constants
        for (E e : enumClass.getEnumConstants()) {
            System.out.printf("%d. %s\n", i++, e);
        }

        System.out.println();
        i = Util.getIntInRange(scanner, "Choose", 1, enumClass.getEnumConstants().length, true);

        // Get the value
        return enumClass.getEnumConstants()[i - 1];
    }

    // Get a string with the number represented in the given base
    public static String getNumberInBase(double number, Base base) {
        switch (base) {
        case Base.BINARY:
            return String.format("0b%s", Long.toBinaryString((long)number));
        case Base.OCTAL:
            return String.format("0o%o", (long)number);
        case Base.DECIMAL:
            // from https://stackoverflow.com/questions/703396/how-to-nicely-format-floating-numbers-to-string-without-unnecessary-decimal-0s
            if (number == (long)number) {
                return String.format("%d", (long)number);
            } else {
                return String.format("%s (%g)", number, number); // %s uses as many decimal places as necessary, instead of a fixed number like %f?
            }
        case Base.HEXADECIMAL:
            return String.format("0x%X", (long)number);
        }

        return "";
    }
}