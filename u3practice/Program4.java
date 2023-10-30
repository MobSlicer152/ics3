import java.lang.StringBuilder;
import java.lang.System;

import java.util.Random;
import java.util.Scanner;

public class Program4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int lottoNumber = random.nextInt(10, 99);
        System.out.print("Enter a two-digit number: ");
        int input = scanner.nextInt();

        if (input < 10 || input > 99) {
            System.out.printf("%d can't be the number (which was %d), because it's not two digits\n", input, lottoNumber);
        } else if (input == lottoNumber) {
            System.out.printf("You win $10000, %d is correct\n", input);
        } else {
            String inputStr = Integer.toString(input);
            String lottoStr = Integer.toString(lottoNumber);
            String reversedInput = new StringBuilder(inputStr).reverse().toString();

            if (reversedInput.compareTo(lottoStr) == 0) {
                System.out.printf("You win $2000, %d is the reverse of %d\n", input, lottoNumber);
            } else if (lottoStr.indexOf(inputStr.charAt(0)) != -1 ||
                       lottoStr.indexOf(inputStr.charAt(1)) != -1) {
                System.out.printf("You win $500, you got one digit of %d\n", lottoNumber);
            } else {
                System.out.printf("You lose, the number was %d\n", lottoNumber);
            }
        }
    }
}

