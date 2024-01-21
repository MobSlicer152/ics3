package calculator;

import java.lang.Character;

import java.util.ArrayList;

public class ExpressionParser {
    // This code is far from perfect or optimal, but it seems to handle the cases I can think of.
    // Mainly, parentheses are handled weirdly, and there are better ways of identifying tokens.
    // Additionally, it works on some pretty complex test inputs from ChatGPT.
    public static ArrayList<Token<?>> parse(String expressionRaw) {
        String expression = expressionRaw.stripLeading().stripTrailing();

        ArrayList<Token<?>> tokens = new ArrayList<>();
        for (int i = 0; i < expression.length(); i++) {
            // Skip whitespace
            while (i < expression.length() && Character.isWhitespace(expression.charAt(i))) {
                i++;
            }

            // Get the next whitespace
            int tokenEnd = i;
            while (tokenEnd < expression.length() && !Character.isWhitespace(expression.charAt(tokenEnd))) {
                tokenEnd++;
            }

            // Get left parentheses
            while (i < expression.length() && expression.charAt(i) == '(') {
                tokens.add(new Token<Object>(TokenType.LEFT_PARENTHESIS, null));
                i++;
                tokenEnd++;
            }

            // Get right parentheses
            while (i < expression.length() && expression.charAt(i) == ')') {
                tokens.add(new Token<Object>(TokenType.RIGHT_PARENTHESIS, null));
                i++;
                tokenEnd--;
            }

            if (i < expression.length()) {
                // Don't go out of bounds
                if (tokenEnd > expression.length()) {
                    tokenEnd = expression.length();
                }

                // Skip right parentheses (but they get added earlier in the loop in the next iteration)
                while (expression.charAt(tokenEnd - 1) == ')') {
                    tokenEnd--;
                }

                if (i >= tokenEnd) {
                    i = tokenEnd - 1;
                }

                // Get the token string
                String tokenStr = expression.substring(i, tokenEnd).strip();
                if (tokenStr.length() < 1) {
                    continue;
                }

                // Add the real length of the token to i
                Token<?> token = Token.interpret(tokenStr);
                tokens.add(token);
                switch (token.getType()) {
                case TokenType.NUMBER:
                    while (i < expression.length() && (expression.charAt(i) == '-' || Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                        i++;
                    };
                    break;
                case TokenType.OPERATOR:
                    i += ((Operator)token.getData()).getToken().length();
                    break;
                case TokenType.FUNCTION:
                    i += ((MathFunction)token.getData()).getToken().length();
                    break;
                default:
                    i += tokenStr.length();
                    break;
                }

                i--;

                if (tokenEnd >= expression.length() - 1) {
                    break;
                }
            }
        }

        return tokens;
    }

    public static double parseAndEvaluate(String expression) {
        return ReversePolishNotation.evaluate(ReversePolishNotation.makeRpn(parse(expression)));
    }

    public static double parseAndEvaluateReversePolish(String expression) {
        return ReversePolishNotation.evaluate(parse(expression));
    }
}