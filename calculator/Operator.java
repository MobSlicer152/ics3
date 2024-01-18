package calculator;

import java.util.function.Function;
import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

public enum Operator {
    // Precedence based on C:
    // https://en.wikipedia.org/wiki/Operators_in_C_and_C%2B%2B#Operator_precedence

    // Each operator has a lambda function to get its inputs, and another one to do
    // the operation

    FACTORIAL("Factorial (!)", "!", 0, Associativity.LEFT, 1,
            (Scanner scanner) -> {
                // Construct an array with the inputs
                return new Double[] {
                        Math.abs(Util.getValidDouble(scanner, "Enter the number: "))
                };
            },
            (Double[] operands) -> {
                assert (operands.length == 1);
                double result = 1;
                for (long i = (long) (double) operands[0]; i > 0; i--) {
                    result *= i;
                }
                return result;
            }),
    // Should this be a function?
    SQUARE_ROOT("Square root (sqrt)", "sqrt", 0, Associativity.RIGHT, 2,
            (Scanner scanner) -> {
                return new Double[] {
                        Math.abs(Util.getValidDouble(scanner, "Enter the number: "))
                };
            },
            (Double[] operands) -> {
                assert (operands.length == 1);
                return Math.sqrt(operands[0]);
            }),
    EXPONENT("Exponent (^)", "^", 0, Associativity.LEFT, 2,
            (Scanner scanner) -> {
                return new Double[] {
                        Util.getValidDouble(scanner, "Enter the base: "),
                        Util.getValidDouble(scanner, "Enter the exponent: "),
                };
            },
            (Double[] operands) -> {
                return Math.pow(operands[0], operands[1]);
            }),
    MULTIPLY("Multiply (*)", "*", 1, Associativity.LEFT, 2,
            (Scanner scanner) -> {
                return new Double[] {
                        Util.getValidDouble(scanner, "Enter a: "),
                        Util.getValidDouble(scanner, "Enter b: "),
                };
            },
            (Double[] operands) -> {
                return operands[0] * operands[1];
            }),
    DIVIDE("Divide (/)", "/", 1, Associativity.LEFT, 2,
            (Scanner scanner) -> {
                return new Double[] {
                        Util.getValidDouble(scanner, "Enter a: "),
                        Util.getValidDouble(scanner, "Enter b: "),
                };
            },
            (Double[] operands) -> {
                return operands[0] / operands[1];
            }),
    MODULO("Modulo (%)", "%", 1, Associativity.LEFT, 2,
            (Scanner scanner) -> {
                return new Double[] {
                        Util.getValidDouble(scanner, "Enter a: "),
                        Util.getValidDouble(scanner, "Enter b: "),
                };
            },
            (Double[] operands) -> {
                return (double) ((long) (double) operands[0] % (long) (double) operands[1]);
            }),
    ADD("Add (+)", "+", 2, Associativity.LEFT, 2,
            (Scanner scanner) -> {
                return new Double[] {
                        Util.getValidDouble(scanner, "Enter a: "),
                        Util.getValidDouble(scanner, "Enter b: "),
                };
            },
            (Double[] operands) -> {
                return operands[0] + operands[1];
            }),
    SUBTRACT("Subtract (-)", "-", 2, Associativity.LEFT, 2,
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
    private int precedence;
    private Associativity associativity;
    private int operandCount;
    private Function<Scanner, Double[]> getInputsFunction;
    private Function<Double[], Double> executeFunction;

    private Operator(String text, String token, int precedence, Associativity associativity, int operandCount,
            Function<Scanner, Double[]> getInputsFunction, Function<Double[], Double> executeFunction) {
        this.text = text;
        this.token = token;
        this.precedence = precedence;
        this.associativity = associativity;
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

    public String getToken() {
        return token;
    }

    private boolean equals(String token) {
        if (token != null && token.length() >= this.token.length()) {
            return this.token.equalsIgnoreCase(token.substring(0, this.token.length()));
        }
        return false;
    }

    public int getPrecedence() {
        return precedence;
    }

    public Associativity getAssociativity() {
        return associativity;
    }

    public int getOperandCount() {
        return operandCount;
    }

    public static Operator getByToken(String token) {
        // https://stackoverflow.com/questions/1128723/how-do-i-determine-whether-an-array-contains-a-particular-value-in-java
        // This might be faster than a for loop;
        Optional<Operator> operator = Arrays.stream(values()).filter((v) -> v.equals(token)).findFirst();
        if (operator.isPresent()) {
            return operator.get();
        }
        return null;
    }
}
