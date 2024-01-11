import java.lang.Enum;
import java.lang.System;

import java.util.Random;
import java.util.Scanner;

// Menu options, have a name so Game.displayMenu can print them
enum MenuOption {
    PLAY("Play the game"),
    DEPOSIT("Deposit money"),
    QUIT("Quit");

    String text;
    private MenuOption(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
};

// Spinner values, so the user can choose one with Game.displayMenu
enum Spinner {
    RED("red"),
    YELLOW("yellow"),
    BLUE("blue"),
    GREEN("green");

    String text;
    private Spinner(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
};

class Game {
    Scanner scanner;
    Random random;
    double money; // How much money the user has
    boolean running; // Whether the user has quit

    // Get an int in a range with a prompt
    private int getIntInRange(String message, int min, int max, boolean addRange) {
        int result;
        do {
            // Whether to print (1-6): or not
            if (addRange) {
                System.out.printf("%s (%d-%d): ", message, min, max);
            } else {
                System.out.print(message);
            }
            result = scanner.nextInt();
        } while (result < min || result > max);

        return result;
    }

    // Get a boolean
    private boolean getBoolean(String message) {
        int result = getIntInRange(message + ": ", 0, 1, false);
        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }

    // Display a menu of the values of any enum and let the user pick a valid constant
    private <E extends Enum<E>> E displayMenu(Class<E> enumClass) {
        int i = 1;
        E option;

        // Print the enum's constants
        for (E e : enumClass.getEnumConstants()) {
            System.out.printf("%d. %s\n", i++, e);
        }

        System.out.println();
        i = getIntInRange("Choose", 1, enumClass.getEnumConstants().length, true);

        // Get the value
        return enumClass.getEnumConstants()[i - 1];
    }

    // This is the main game
    private void playGame() {
        double bet = 0;

        // Without this, the user gets stuck in a loop
        if (money < 0.01) {
            System.out.println("You can't play, you're broke");
            return;
        }

        // Get a valid bet
        do {
            System.out.print("Enter your bet: ");
            bet = scanner.nextDouble();
            if (bet >= 0 && bet < money) {
                break;
            }
            System.out.printf("Enter an amount between $0.00 and $%.2f\n", money);
        } while (bet <= 0 || bet > money);

        // Take the money out of the account
        money -= bet;
        System.out.printf("You have chosen to bet $%.2f, your new balance is $%.2f.\n", bet, money);

        // Get the guesses before generating the values so it's harder to use a memory dump to cheat
        boolean coinGuess = getBoolean("Guess the coin flip (0 for heads, 1 for tails)");
        int dieGuess = getIntInRange("Guess the die roll", 1, 6, true);
        System.out.println("Guess the spinner");
        Spinner spinnerGuess = displayMenu(Spinner.class);

        // Generate the values
        boolean coin = random.nextBoolean();
        int die = random.nextInt(1, 6);
        Spinner spinner = Spinner.values()[random.nextInt(0, Spinner.values().length - 1)];

        // Check if the guesses are right, reusing bet because it isn't needed again
        if (coinGuess == coin && dieGuess == die && spinnerGuess == spinner) {
            bet *= 3;
            System.out.print("You got all three right!");
        } else if ((coinGuess == coin && dieGuess == die) || (dieGuess == die && spinnerGuess == spinner)) {
            bet *= 2;
            System.out.print("You got two right!");
        } else if (dieGuess == die) {
            System.out.print("You got the die right!");
        } else if (spinnerGuess == spinner) {
            bet *= 0.5;
            System.out.print("You got the spinner right!");
        } else if (coinGuess == coin) {
            bet *= 0.25;
            System.out.print("You got the coin right!");
        } else {
            bet = 0;
            System.out.print("You lose.");
        }

        // Add the money to the account
        money += bet;
        System.out.printf(" $%.2f has been added to your account.\n", bet);
    }

    // Deposit money into the account
    private void deposit() {
        System.out.print("Enter the money: ");
        double extraMoney = scanner.nextDouble();
        money += extraMoney;
        System.out.printf("New balance is $%.2f after deposit of $%.2f\n", money, extraMoney);
    }

    // Constructor, initializes the member variables, and greets the user
    public Game() {
        scanner = new Scanner(System.in);
        random = new Random();
        money = 0;

        System.out.println("Welcome to gambling");
    }

    // This is the main loop of the game
    public void run() {
        running = true;
        while (running) {
            System.out.printf("Current balance: $%.2f\n\n", money);
            // Get the option the user wants to do
            MenuOption option = displayMenu(MenuOption.class);
            switch (option) {
            case PLAY:
                playGame();
                break;
            case DEPOSIT:
                deposit();
                break;
            case QUIT:
                running = false;
                System.out.println("Goodbye");
                break;
            }
        }
    }
}

public class Gambling {
    // Creates a Game object and runs it
    public static void main(String[] args) {
        Game game = new Game();
        game.run();
    }
}
