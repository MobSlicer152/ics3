package calculator;

import java.lang.Double;

public class Token<T> {
    private TokenType type;
    private T data; // bad

    public Token(TokenType type, T data) {
        this.type = type;
        this.data = data;
    }

    // This takes a string, and interprets it into a token if possible
    // Not sure exactly how bad this is, but it feels at least kind of iffy
    public static Token<?> interpret(String token) {
        try {
            double value = Double.parseDouble(token);
            return new Token<Double>(TokenType.NUMBER, value); // This line is only reached if no NumberFormatException is thrown, which means it's a number
        } catch (NumberFormatException e) {
            // Try everything, in order of most to least likely (that makes it a little faster)
            Operator operator = Operator.getByToken(token);
            if (operator != null) {
                return new Token<Operator>(TokenType.OPERATOR, operator);
            }

            MathFunction function = MathFunction.getByToken(token);
            if (function != null) {
                return new Token<MathFunction>(TokenType.FUNCTION, function);
            }
        }

        return new Token<Object>(TokenType.UNKNOWN, token);
    }

    public TokenType getType() {
        return type;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", type, data);
    }
}
