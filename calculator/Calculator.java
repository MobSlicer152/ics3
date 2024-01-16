package calculator;

import java.lang.System;

import java.util.Scanner;

public class Calculator {
    private static Scanner scanner;
    private static boolean running;
    private static Base base;
    private static double lastAnswer;
    
    private static void singleOperation() {

    }

    public static void main(String[] args) {
        scanner = new Scanner(System.in);

        base = Base.DECIMAL;
        lastAnswer = 0;

        running = true;
        while (running) {
            MainMenuOptions option = Util.displayMenu(scanner, MainMenuOptions.class, String.format(
                " ---<[ CALCULATOR ]>---\n" +
                "------------------------\n" +
                "Current base: %s\n" +
                "Last answer: %s\n" +
                "------------------------\n",
                base,
                Util.getNumberInBase(lastAnswer, base)
            ));
            switch (option) {
            case MainMenuOptions.SINGLE_OPERATION:
                singleOperation();
                break;
            case MainMenuOptions.SET_BASE:
                base = Util.displayMenu(scanner, Base.class, "");
                break;
            case MainMenuOptions.QUIT:
                running = false;
                break;
            }
        }
    }
}
