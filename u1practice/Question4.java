import java.lang.StringBuilder;
import java.lang.System;

import java.util.Scanner;

public class Question4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter a three digit number: ");
        StringBuilder builder = new StringBuilder();
        builder.append(scanner.nextLine());
        System.out.printf("Reversed: %s", builder.reverse().toString());
    }
}
