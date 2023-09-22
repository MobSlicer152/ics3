import java.lang.System;

import java.util.Scanner;

public class Question3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int number = scanner.nextInt();
        scanner.nextLine();

        System.out.printf("%d is 0b%s in binary\n", number, Integer.toBinaryString(number));
    }
}

