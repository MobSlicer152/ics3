import java.lang.Math;
import java.lang.System;

import java.util.Scanner;

public class Arrow {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < 5; i++) {
            System.out.println("*".repeat(i));
        }

        for (int i = 5; i > 0; i--) {
            System.out.println("*".repeat(i));
        }
    }
}
