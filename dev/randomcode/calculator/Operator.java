package dev.randomcode.calculator;

import java.util.function.Function;
import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

public enum Operator {
    // Each operator has a lambda function to get its inputs, and another one to do
    // the operation
    EXPONENT("Exponent (^)", "^", 2,
            (Scanner scanner) -> {
                return new Double[] {
                        Util.getValidDouble(scanner, "Enter the base: "),
                        Util.getValidDouble(scanner, "Enter the exponent: "),
                };
            },
            (Double[] operands) -> {
                return Math.pow(operands[0], operands[1]);
            }),
    MULTIPLY("Multiply (*)", "*", 2,
            (Scanner scanner) -> {
                return new Double[] {
                        Util.getValidDouble(scanner, "Enter a: "),
                        Util.getValidDouble(scanner, "Enter b: "),
                };
            },
            (Double[] operands) -> {
                return operands[0] * operands[1];
            }),
    DIVIDE("Divide (/)", "/", 2,
            (Scanner scanner) -> {
                return new Double[] {
                        Util.getValidDouble(scanner, "Enter a: "),
                        Util.getValidDouble(scanner, "Enter b: "),
                };
            },
            (Double[] operands) -> {
                return operands[0] / operands[1];
            }),
    MODULO("Modulo (%)", "%", 2,
            (Scanner scanner) -> {
                return new Double[] {
                        Util.getValidDouble(scanner, "Enter a: "),
                        Util.getValidDouble(scanner, "Enter b: "),
                };
            },
            (Double[] operands) -> {
                return (double) ((long) (double) operands[0] % (long) (double) operands[1]);
            }),
    ADD("Add (+)", "+", 2,
            (Scanner scanner) -> {
                return new Double[] {
                        Util.getValidDouble(scanner, "Enter a: "),
                        Util.getValidDouble(scanner, "Enter b: "),
                };
            },
            (Double[] operands) -> {
                return operands[0] + operands[1];
            }),
    SUBTRACT("Subtract (-)", "-", 2,
            (Scanner scanner) -> {
                return new Double[] {
                        Util.getValidDouble(scanner, "Enter a: "),
                        Util.getValidDouble(scanner, "Enter b: "),
                };
            },
            (Double[] operands) -> {
                return operands[0] - operands[1];
            });

    private String text;
    private String token;
    private int operandCount;
    private Function<Scanner, Double[]> getInputsFunction;
    private Function<Double[], Double> executeFunction;

    private Operator(String text, String token, int operandCount,
            Function<Scanner, Double[]> getInputsFunction, Function<Double[], Double> executeFunction) {
        this.text = text;
        this.token = token;
        this.operandCount = operandCount;
        this.getInputsFunction = getInputsFunction;
        this.executeFunction = executeFunction;
    }

    public Double[] getInputs(Scanner scanner) {
        return getInputsFunction.apply(scanner);
    }

    public double execute(Double[] operands) {
        return executeFunction.apply(operands);
    }

    @Override
    public String toString() {
        return text;
    }

    // Getters so that data can't be externally modified but can still be read

    public String getToken() {
        return token;
    }

    public int getOperandCount() {
        return operandCount;
    }

    // These are for looking up operators in the parser

    // Check if a token starts with the operator
    private boolean equals(String token) {
        if (token != null && token.length() >= this.token.length()) {
            return this.token.equalsIgnoreCase(token.substring(0, this.token.length()));
        }
        return false;
    }

    // Returns a matching operator or null if none is found
    public static Operator getByToken(String token) {
        // https://stackoverflow.com/questions/1128723/how-do-i-determine-whether-an-array-contains-a-particular-value-in-java
        // This is cleaner than a for loop, and a construct I frequently use in Rust (I just didn't know how to do it in Java)
        Optional<Operator> operator = Arrays.stream(values()).filter((v) -> v.equals(token)).findFirst();
        if (operator.isPresent()) {
            return operator.get();
        }
        return null;
    }
}
