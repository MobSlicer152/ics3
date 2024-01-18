package calculator;

import java.lang.Character;

import java.util.ArrayList;

public class ExpressionParser {
    // This might be bad, but it will work.
    // Mainly, parentheses are handled weirdly, and there are better ways of identifying tokens.
    public static ArrayList<Token<?>> parse(String expression) {
        ArrayList<Token<?>> tokens = new ArrayList<>();
        for (int i = 0; i < expression.length(); i++) {
            // Skip whitespace
            while (Character.isWhitespace(expression.charAt(i)) && i < expression.length()) {
                i++;
            }

            // Get the next whitespace
            int tokenEnd = i;
            while (!Character.isWhitespace(expression.charAt(tokenEnd)) && tokenEnd < expression.length() - 1) {
                tokenEnd++;
            }

            // Get parentheses
            if (expression.charAt(i) == '(') {
                tokens.add(new Token<Object>(TokenType.LEFT_PARENTHESIS, null));
                i++;
            } else if (expression.charAt(i) == ')') {
                tokens.add(new Token<Object>(TokenType.RIGHT_PARENTHESIS, null));
                i++;
            }

            // Only if the parenthesis wasn't the end
            if (i < expression.length()) {
                if (i == tokenEnd) {
                    continue;
                }

                String tokenStr = expression.substring(i, tokenEnd);
                if (!tokenStr.equals(")") && tokenStr.endsWith(")")) {
                    tokenStr = tokenStr.substring(0, tokenStr.length() - 1);
                }

                // Add the real length of the token to i
                Token<?> token = Token.interpret(tokenStr);
                tokens.add(token);
                switch (token.getType()) {
                case TokenType.NUMBER:
                    while (expression.charAt(i) == '-' || Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.') {
                        i++;
                    };
                    break;
                case TokenType.OPERATOR:
                    i += ((Operator)token.getData()).getToken().length();
                    break;
                case TokenType.FUNCTION:
                    i += ((TrigFunction)token.getData()).getToken().length();
                    break;
                default:
                    i++; // to cancel out the i-- that's used on all the other ones
                    break;
                }

                i--;
            }
        }

        return tokens;
    }

    public static String makeRpn(ArrayList<Token<?>> tokens) {
        for (int i = 0; i < tokens.size(); i++) {
            System.out.println(tokens.get(i));
        }
        
        return "";
    }

    public static double parseAndEvaluate(String expression) {
        return ReversePolishParser.evaluate(makeRpn(parse(expression)));
    }
}