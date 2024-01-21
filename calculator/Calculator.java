package calculator;

import java.lang.System;
import java.util.Arrays;
import java.util.Scanner;

public class Calculator {
    private static Scanner scanner;
    private static boolean running;
    private static String lastOperation;
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
        lastOperation = operator.toString();
    }

    private static void doTrigFunction() {
        MathFunction function = Util.displayMenu(scanner, MathFunction.class, 
        " ---<[ FUNCTIONS ]>---\n" +
        "-----------------------\n");
        if (function == null) {
            return;
        }

        // Slightly faster and cleaner than evaluating this twice in the if statements
        boolean convertDegrees = function.isTrig() && degreesMode == DegreesMode.DEGREES && function.givesAngle();

        // The implementations all work in radians, so they need to be converted to and from
        Double[] arguments = function.getInputs(scanner);
        if (convertDegrees) {
            for (int i = 0; i < arguments.length; i++) {
                arguments[i] = Math.toRadians(arguments[i]);
            }
        }
        lastAnswer = function.execute(arguments);
        if (convertDegrees) {
            lastAnswer = Math.toDegrees(lastAnswer);
        }
        lastOperation = function.toString();
    }

    private static void doExpression() {
        System.out.print(
            " ---<[ EXPRESSION ]>---\n" +
            "------------------------\n" +
            "Enter an expression (parentheses and all other operators/functions supported):\n");
        String expression = scanner.nextLine();
        lastAnswer = ExpressionParser.parseAndEvaluate(expression);
        lastOperation = expression;
    }

    private static void doReversePolish() {
        System.out.print(
            " ---<[ RPN ]>---\n" +
            "-----------------\n" +
            "Enter an expression in reverse Polish notation (all operators/functions supported):\n");
        String expression = scanner.nextLine();
        lastAnswer = ExpressionParser.parseAndEvaluateReversePolish(expression);
        lastOperation = expression;
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
                "Last operation: %s\n" +
                "Last answer: %G\n" +
                "%s\n" +
                "------------------------\n",
                lastOperation,
                lastAnswer,
                degreesMode
            ));

            if (option == null) {
                running = false;
                break;
            }

            switch (option) {
            case SINGLE_OPERATION:
                doSingleOperation();
                break;
            case FUNCTION:
                doTrigFunction();
                break;
            case EXPRESSION:
                doExpression();
                break;
            case REVERSE_POLISH:
                doReversePolish();
                break;
            }
        }

        System.out.printf("Goodbye. The last answer was %f, to %s.\n", lastAnswer, lastOperation);
    }
}
