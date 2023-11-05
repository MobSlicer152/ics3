import java.lang.Math;
import java.lang.System;

import java.util.Random;
import java.util.Scanner;

enum Spinner {
    Red("red"),
    Blue("blue"),
    Yellow("yellow"),
    Green("green");

    private String name;
    private Spinner(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

public class Project1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
    
        boolean coin = random.nextInt(0, 1) == 1;
        int die = random.nextInt(1, 6);
        Spinner spinner = Spinner.values()[random.nextInt(0, 3)];

        System.out.printf("%s %d %s\n", coin ? "true" : "false", die, spinner.toString());

        System.out.print("Guess the coin (heads or tails): ");
        String coinGuess = scanner.next();
        scanner.nextLine();
        System.out.print("Guess the die (1-6): ");
        int dieGuess = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Guess the spinner (red, blue, yellow, green): ");
        String spinnerGuess = scanner.next();
        scanner.nextLine();

        System.out.printf("%s %d %s\n", coinGuess, dieGuess, spinnerGuess);
        
        boolean coinOutcome = coin == (coinGuess.compareToIgnoreCase("heads") == 0);
        System.out.printf("Coin was %s, you %s\n",
                coin ? "heads" : "tails",
                coinOutcome ? "win" : "lose");
        boolean dieOutcome = die == dieGuess;
        System.out.printf("Die was %d, you %s\n", die, dieOutcome ? "win" : "lose");
        boolean spinnerOutcome = spinner.toString().compareToIgnoreCase(spinnerGuess) == 0;
        System.out.printf("Spinner was %s, you %s\n", spinner.toString(), spinnerOutcome ? "win" : "lose");

        if (coinOutcome && dieOutcome && spinnerOutcome) {
            System.out.println("You got everything right");
        } else if (coinOutcome && dieOutcome && !spinnerOutcome) {
            System.out.println("You got the coin and the die correct");
        } else if (coinOutcome && !dieOutcome && spinnerOutcome) {
            System.out.println("You got the coin and the spinner correct");
        } else if (!coinOutcome && dieOutcome && spinnerOutcome) {
            System.out.println("You got the die and the spinner correct");
        } else if (coinOutcome && !dieOutcome && !spinnerOutcome) {
            System.out.println("You got the coin correct");
        } else if (!coinOutcome && dieOutcome && !spinnerOutcome) {
            System.out.println("You got the die correct");
        } else if (!coinOutcome && !dieOutcome && spinnerOutcome) {
            System.out.println("You got the spinner correct");
        } else {
            System.out.println("You lose");
        }
    }
}
