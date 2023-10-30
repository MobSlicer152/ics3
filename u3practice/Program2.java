import java.lang.System;

import java.util.Random;
import java.util.Scanner;

public class Program2 {
    private enum Move {
        ROCK(0, 2), PAPER(1, 0), SCISSORS(2, 1);

        private final int number;
        private final int beats;
        Move(int number, int beats) {
            this.number = number;
            this.beats = beats;
        }

        private int number() { return number; }
        private int beats() { return beats; }

        public boolean beats(Move otherMove) {
            return this.beats() == otherMove.number();
        }

        public boolean isBeatenBy(Move otherMove) {
            return otherMove.beats(this);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("Rock (0), Paper (1), Scissors(2): ");
        Move user = Move.values()[scanner.nextInt()];
        Move computer = Move.values()[random.nextInt(0, 2)];

        System.out.printf("You played %s, the computer played %s\n", user, computer);

        if (user == computer) {
            System.out.println("It's a draw.");
        } else if (user.beats(computer)) {
            System.out.printf("You win, %s beats %s\n", user, computer);
        } else if (user.isBeatenBy(computer)) {
            System.out.printf("You lose, %s beats %s\n", computer, user);
        }
    }
}

