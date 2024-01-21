package calculator;

import java.lang.Double;

public class Token {
    private TokenType type;
    private Object data;

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

    public String getRpnString() {
        if (type == TokenType.LEFT_PARENTHESIS || type == TokenType.RIGHT_PARENTHESIS) {
            return "";
        } else if (type == TokenType.OPERATOR) {
            return ((Operator)data).getToken();
        } else if (type == TokenType.FUNCTION) {
            return ((MathFunction)data).getToken();
        } else {
            return data.toString();
        }
    }
}
