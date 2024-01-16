package calculator;

public enum Operator {
    // Precedence based on C: https://en.wikipedia.org/wiki/Operators_in_C_and_C%2B%2B#Operator_precedence
    FACTORIAL("Factorial (!)", "!", 0, Associativity.LEFT, 1),
    SQUARE_ROOT("Square root (sqrt)", "sqrt", 0, Associativity.RIGHT, 1), // Should this be a function?
    EXPONENT("Exponent (^)", "^", 0, Associativity.LEFT, 2),
    MULTIPLY("Multiply (*)", "*", 1, Associativity.LEFT, 2),
    DIVIDE("Divide (/)", "/", 1, Associativity.LEFT, 2),
    MODULO("Modulo (%)", "%", 1, Associativity.LEFT, 2),
    ADD("Add (+)", "+", 2, Associativity.LEFT, 2),
    SUBTRACT("Subtract (-)", "-", 2, Associativity.LEFT, 2);

    private String text;
    private String token;
    private int precedence;
    private Associativity associativity;
    private int operandCount;

    private Operator(String text, String token, int precedence, Associativity associativity, int operandCount) {
        this.text = text;
        this.token = token;
        this.precedence = precedence;
        this.associativity = associativity;
        this.operandCount = operandCount;
    }

    @Override
    public String toString() {
        return text;
    }

    public String getToken() {
        return token;
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
}
