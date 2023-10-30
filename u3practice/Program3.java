import java.lang.System;

import java.util.Scanner;

public class Program3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[] address = {0, 0, 0, 0};
        for (int i = 0; i < address.length; i++) {
            System.out.printf("Enter octet %d/%d: ", i + 1, address.length);
            address[i] = scanner.nextInt();
        }

        boolean valid = true;
        for (int i = 0; i < address.length; i++) {
            if (address[i] < 0 || address[i] > 255) {
                System.out.printf("Octet %d/%d (%d) is invalid\n", i + 1, address.length, address[i]);
                valid = false;
            }
        }

        if (valid) {
            System.out.printf("IP address: %d.%d.%d.%d\n", address[0], address[1], address[2], address[3]);
        }
    }
}

