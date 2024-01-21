package dev.randomcode.calculator;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;

public enum MathFunction {
    // Note: I tried implementing these myself, but I couldn't understand any of the
    // accurate approximations for the inverse functions, and I don't think I can
    // explain fast inverse square root (which is the only approximation that I
    // could use for square roots that I know about and could implement).
    SINE("Sine (sin)", "sin", 1, true, true, false,
            (Scanner scanner) -> {
                // Construct an array with the inputs
                return new Double[] {
                        Util.getValidDouble(scanner, "Enter the number: ")
                };
            },
            (Double[] arguments) -> {
                assert (arguments.length == 1);
                return Math.sin(arguments[0]);
            }),
    COSINE("Cosine (cos)", "cos", 1, true, true, false,
            (Scanner scanner) -> {
                // Construct an array with the inputs
                return new Double[] {
                        Util.getValidDouble(scanner, "Enter the number: ")
                };
            },
            (Double[] arguments) -> {
                assert (arguments.length == 1);
                return Math.cos(arguments[0]);
            }),
    TANGENT("Tangent (tan)", "tan", 1, true, true, false,
            (Scanner scanner) -> {
                // Construct an array with the inputs
                return new Double[] {
                        Util.getValidDouble(scanner, "Enter the number: ")
                };
            },
            (Double[] arguments) -> {
                assert (arguments.length == 1);
                return Math.tan(arguments[0]);
            }),
    ARCSINE("Arcsine (asin)", "asin", 1, true, false, true,
            (Scanner scanner) -> {
                // Construct an array with the inputs
                return new Double[] {
                        Util.getValidDouble(scanner, "Enter the number: ")
                };
            },
            (Double[] arguments) -> {
                assert (arguments.length == 1);
                return Math.asin(arguments[0]);
            }),
    ARCCOSINE("Arccosine (acos)", "acos", 1, true, false, true,
            (Scanner scanner) -> {
                // Construct an array with the inputs
                return new Double[] {
                        Util.getValidDouble(scanner, "Enter the number: ")
                };
            },
            (Double[] arguments) -> {
                assert (arguments.length == 1);
                return Math.acos(arguments[0]);
            }),
    ARCTANGENT("Arctangent (atan)", "atan", 1, true, false, true,
            (Scanner scanner) -> {
                // Construct an array with the inputs
                return new Double[] {
                        Util.getValidDouble(scanner, "Enter the number: ")
                };
            },
            (Double[] arguments) -> {
                assert (arguments.length == 1);
                return Math.atan(arguments[0]);
            }),
    // These two are here for ease of both inputting and parsing them
    SQUARE_ROOT("Square root (sqrt)", "sqrt", 1, false, false, false,
            (Scanner scanner) -> {
                return new Double[] {
                        Math.abs(Util.getValidDouble(scanner, "Enter the number: "))
                };
            },
            (Double[] arguments) -> {
                assert (arguments.length == 1);
                return Math.sqrt(arguments[0]);
            }),
    FACTORIAL("Factorial (fac)", "fac", 1, false, false, false,
            (Scanner scanner) -> {
                // Construct an array with the inputs
                return new Double[] {
                        Math.abs(Util.getValidDouble(scanner, "Enter the number: "))
                };
            },
            (Double[] arguments) -> {
                assert (arguments.length == 1);
                double result = 1;
                for (long i = (long) (double) arguments[0]; i > 0; i--) {
                    result *= i;
                }
                return result;
            });

    private String text;
    private String token;
    private int argumentCount;
    private boolean isTrig;
    private boolean takesAngle;
    private boolean givesAngle;
    private Function<Scanner, Double[]> getInputsFunction;
    private Function<Double[], Double> executeFunction;

    private MathFunction(String text, String token, int argumentCount, boolean isTrig, boolean takesAngle, boolean givesAngle,
            Function<Scanner, Double[]> getInputsFunction, Function<Double[], Double> executeFunction) {
        this.text = text;
        this.token = token;
        this.argumentCount = argumentCount;
        this.isTrig = isTrig;
        this.takesAngle = takesAngle;
        this.givesAngle = givesAngle;
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

    public int getArgumentCount() {
        return argumentCount;
    }

    public boolean isTrig() {
        return isTrig;
    }

    public boolean takesAngle() {
        return takesAngle;
    }

    public boolean givesAngle() {
        return givesAngle;
    }

    // Same as in Operator.java, maybe I should make an interface for this
    // (it would make some other code cleaner too)

    private boolean equals(String token) {
        if (token != null && token.length() >= this.token.length()) {
            return this.token.equalsIgnoreCase(token.substring(0, this.token.length()));
        }
        return false;
    }

    public static MathFunction getByToken(String token) {
        // https://stackoverflow.com/questions/1128723/how-do-i-determine-whether-an-array-contains-a-particular-value-in-java
        // This might be faster than a for loop;
        Optional<MathFunction> function = Arrays.stream(values()).filter((v) -> v.equals(token)).findFirst();
        if (function.isPresent()) {
            return function.get();
        }
        return null;
    }
}
