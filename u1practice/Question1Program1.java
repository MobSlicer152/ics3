import java.lang.System;

import java.util.Scanner;

public class Question1Program1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.printf("Hi %s, welcome to ICS3U!\n", name);
    }
}

