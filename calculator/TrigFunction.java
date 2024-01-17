package calculator;

import java.util.Scanner;
import java.util.function.Function;

public enum TrigFunction {
    // https://en.wikipedia.org/wiki/Trigonometric_functions#Power_series_expansion
    SINE("Sine (sin)", "sin", 1, true, false,
            (Scanner scanner) -> {
                // Construct an array with the inputs
                return new Double[] {
                        Util.getValidDouble(scanner, "Enter the number: ")
                };
            },
            (Double[] arguments) -> {
                assert (arguments.length == 1);
                double result = 0;
                double x = arguments[0];
                for (int n = 0; n < 7; n++) { // 7 iterations gives a low enough margin of error
                    double twoNPlus1 = 2 * n + 1;
                    result += (Math.pow(-1, n) /
                            Operator.FACTORIAL.execute(new Double[] { twoNPlus1 }))
                            * Math.pow(x, twoNPlus1);
                }
                return result;
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
                double x = arguments[0];
                return SINE.execute(new Double[] { Math.PI / 2 - x });
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
                double x = arguments[0];
                // tan = sin/cos
                return SINE.execute(new Double[] { x }) / COSINE.execute(new Double[] { x });
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

                double result = 0;

                double z = arguments[0];
                if (Math.abs(z) > 1) {
                    return Double.NaN; // idk if NaN is right, but it's out of range and I don't want the program
                                       // crashing, so I'm lazy
                }

                for (double n = 0; n < 7; n++) {
                    double twoN = 2 * n;
                    result += (Operator.FACTORIAL.execute(new Double[] { twoN }) /
                            Math.pow((Math.pow(2, n) * Operator.FACTORIAL.execute(new Double[] { n })), 2)) *
                            (Math.pow(z, twoN + 1) / (twoN + 1));
                }
                System.out.println(Math.asin(z));

                return result;
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
                double x = arguments[0];
                return Math.PI / 2 - SINE.execute(new Double[] { x });
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

                double result = 0;

                double z = arguments[0];
                if (Math.abs(z) > 1) {
                    return Double.NaN; // idk if NaN is right, but it's out of range and I don't want the program
                                       // crashing, so I'm lazy
                }

                for (double n = 0; n < 100; n++) {
                    double twoN = 2 * n;
                    result += (Math
                            .pow((Math.pow(2, twoN) * Math.pow(Operator.FACTORIAL.execute(new Double[] { n }), 2)), 2))
                            /
                            (Operator.FACTORIAL.execute(new Double[] { twoN + 1 })) *
                            (Math.pow(z, twoN + 1) / Math.pow(1 + Math.pow(z, 2), n + 1));
                }

                System.out.println(Math.atan(z));
                return result;
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

    public int getArgumentCount() {
        return argumentCount;
    }

    public boolean takesAngle() {
        return takesAngle;
    }

    public boolean givesAngle() {
        return givesAngle;
    }
}
