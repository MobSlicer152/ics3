package calculator;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;

public enum TrigFunction {
    SINE("Sine (sin)", "sin", 1, true, false,
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
    COSINE("Cosine (cos)", "cos", 1, true, false,
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
    TANGENT("Tangent (tan)", "tan", 1, true, false,
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
    ARCSINE("Arcsine (asin)", "asin", 1, false, true,
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
    ARCCOSINE("Arccosine (acos)", "acos", 1, false, true,
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
    ARCTANGENT("Arctangent (atan)", "atan", 1, false, true,
            (Scanner scanner) -> {
                // Construct an array with the inputs
                return new Double[] {
                        Util.getValidDouble(scanner, "Enter the number: ")
                };
            },
            (Double[] arguments) -> {
                assert (arguments.length == 1);
                return Math.atan(arguments[0]);
            });

    private String text;
    private String token;
    private int argumentCount;
    private boolean takesAngle;
    private boolean givesAngle;
    private Function<Scanner, Double[]> getInputsFunction;
    private Function<Double[], Double> executeFunction;

    private TrigFunction(String text, String token, int argumentCount, boolean takesAngle, boolean givesAngle,
            Function<Scanner, Double[]> getInputsFunction, Function<Double[], Double> executeFunction) {
        this.text = text;
        this.token = token;
        this.argumentCount = argumentCount;
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

    public String getToken() {
        return token;
    }

    private boolean equals(String token) {
        return ((Object)this.token).equals(token);
    }

    public int getArgumentCount() {
        return argumentCount;
    }

    public boolean takesAngle() {
        return takesAngle;
    }

    public boolean givesAngle() {
        return givesAngle;
    }

    public static TrigFunction getByToken(String token) {
        // https://stackoverflow.com/questions/1128723/how-do-i-determine-whether-an-array-contains-a-particular-value-in-java
        // This might be faster than a for loop;
        Optional<TrigFunction> function = Arrays.stream(values()).filter((v) -> v.equals(token)).findFirst();
        if (function.isPresent()) {
            return function.get();
        }
        return null;
    }
}
