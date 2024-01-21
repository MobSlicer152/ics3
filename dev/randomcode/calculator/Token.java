package dev.randomcode.calculator;

import java.lang.Double;

public class Token {
    private final TokenType type;
    private final Object data;

    // Shorthands for types that don't store real data
    public static final Token EXPONENT = new Token(TokenType.OPERATOR, Operator.EXPONENT);
    public static final Token MULTIPLY = new Token(TokenType.OPERATOR, Operator.MULTIPLY);
    public static final Token DIVIDE = new Token(TokenType.OPERATOR, Operator.DIVIDE);
    public static final Token MODULO = new Token(TokenType.OPERATOR, Operator.MODULO);
    public static final Token ADD = new Token(TokenType.OPERATOR, Operator.ADD);
    public static final Token SUBTRACT = new Token(TokenType.OPERATOR, Operator.SUBTRACT);
    public static final Token SINE = new Token(TokenType.FUNCTION, MathFunction.SINE);
    public static final Token COSINE = new Token(TokenType.FUNCTION, MathFunction.COSINE);
    public static final Token TANGENT = new Token(TokenType.FUNCTION, MathFunction.TANGENT);
    public static final Token ARCSINE = new Token(TokenType.FUNCTION, MathFunction.ARCSINE);
    public static final Token ARCCOSINE = new Token(TokenType.FUNCTION, MathFunction.ARCCOSINE);
    public static final Token ARCTANGENT = new Token(TokenType.FUNCTION, MathFunction.ARCTANGENT);
    public static final Token SQUARE_ROOT = new Token(TokenType.FUNCTION, MathFunction.SQUARE_ROOT);
    public static final Token FACTORIAL = new Token(TokenType.FUNCTION, MathFunction.FACTORIAL);
    public static final Token LEFT_PARENTHESIS = new Token(TokenType.LEFT_PARENTHESIS, null);
    public static final Token RIGHT_PARENTHESIS = new Token(TokenType.RIGHT_PARENTHESIS, null);
    public static final Token ANY_NUMBER = new Token(TokenType.NUMBER, null);
    public static final Token ANY_OPERATOR = new Token(TokenType.OPERATOR, null);
    public static final Token ANY_FUNCTION = new Token(TokenType.FUNCTION, null);

    public Token(TokenType type, Object data) {
        this.type = type;
        this.data = data;
    }

    public TokenType getType() {
        return type;
    }

    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        if (type == TokenType.LEFT_PARENTHESIS || type == TokenType.RIGHT_PARENTHESIS) {
            return String.format("%s", type);
        } else {
            return String.format("%s(%s)", type, data);
        }
    }

    public String getLexeme() {
        switch (type) {
        case LEFT_PARENTHESIS:
            return "(";
        case RIGHT_PARENTHESIS:
            return ")";
        case OPERATOR:
            return ((Operator)data).getToken();
        case FUNCTION:
            return ((MathFunction)data).getToken();
        default:
            if (data == null) {
                return "(null)";
            } else {
                return data.toString();
            }
        }
    }
}
