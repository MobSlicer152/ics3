package calculator;

import java.lang.System;
import java.util.Arrays;
import java.util.Scanner;

public class Calculator {
    private static Scanner scanner;
    private static boolean running;
    private static double lastAnswer;
    private static DegreesMode degreesMode;

    private static void doSingleOperation() {
        Operator operator = Util.displayMenu(scanner, Operator.class, 
        " ---<[ OPERATOR ]>---\n" +
        "----------------------\n");
        if (operator == null) {
            return;
        }

        Double[] operands = operator.getInputs(scanner);
        lastAnswer = operator.execute(operands);
    }

    private static void doTrigFunction() {
        TrigFunction function = Util.displayMenu(scanner, TrigFunction.class, 
        " ---<[ TRIGONOMETRY ]>---\n" +
        "--------------------------\n");
        if (function == null) {
            return;
        }

        // The implementations all work in radians
        Double[] arguments = function.getInputs(scanner);
        if (degreesMode == DegreesMode.DEGREES && function.takesAngle()) {
            for (int i = 0; i < arguments.length; i++) {
                arguments[i] = Math.toRadians(arguments[i]);
            }
        }
        lastAnswer = function.execute(arguments);
        if (degreesMode == DegreesMode.DEGREES && function.givesAngle()) {
            lastAnswer = Math.toDegrees(lastAnswer);
        }
    }

    private static void doExpression() {

    }

    private static void doReversePolish() {

    }

    public static void main(String[] args) {
        scanner = new Scanner(System.in);

        lastAnswer = 0;
        degreesMode = DegreesMode.DEGREES;

        running = true;
        while (running) {
            MainMenuOptions option = Util.displayMenu(scanner, MainMenuOptions.class, String.format(
                " ---<[ CALCULATOR ]>---\n" +
                "------------------------\n" +
                "Last answer: %G\n" +
                "%s\n" +
                "------------------------\n",
                lastAnswer,
                degreesMode
            ));

            if (option == null) {
                running = false;
                break;
            }

            switch (option) {
            case MainMenuOptions.SINGLE_OPERATION:
                doSingleOperation();
                break;
            case MainMenuOptions.TRIG_FUNCTION:
                doTrigFunction();
                break;
            case MainMenuOptions.EXPRESSION:
                doExpression();
                break;
            case MainMenuOptions.REVERSE_POLISH:
                doReversePolish();
                break;
            }
        }

        System.out.printf("Goodbye. The last answer was %f.\n", lastAnswer);
    }
}
