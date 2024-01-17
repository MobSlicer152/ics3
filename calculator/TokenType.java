package calculator;

import java.lang.Double;

public enum TokenType {
    NUMBER,
    FUNCTION,
    OPERATOR,
    UNKNOWN;

    public TokenType getKind(String token) {
        try {
            Double.parseDouble(token);
            return NUMBER; // This line is only reached if no NumberFormatException is thrown, which means it's a number
        } catch (NumberFormatException e) {
            if (TrigFunction.getByToken(token) != null) {
                return FUNCTION;
            } else if (Operator.getByToken(token) != null) {
                return OPERATOR;
            } else {
                return UNKNOWN;
            }
        }
    }
}