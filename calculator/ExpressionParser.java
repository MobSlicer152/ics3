package calculator;

import java.util.ArrayList;

// Based on https://craftinginterpreters.com/scanning.html,
// but stripped down to support just mathematical expressions
public class ExpressionParser {
    String source;
    ArrayList<Token> tokens;
    int start;
    int current;

    private char advance() {
        return source.charAt(current++);
    }

    private char peek() {
        return peek(0);
    }

    private char peek(int n) {
        if (current + n >= source.length()) {
            return '\0';
        }
        return source.charAt(current + n);
    }

    private boolean isDigit(char c) {
        return '0' <= c && c <= '9';
    }

    private void number() {
        while (isDigit(peek())) {
            advance();
        }

        if (peek() == '.' && isDigit(peek(1))) {
            advance();
            while (isDigit(peek())) {
                advance();
            }
        }

        tokens.add(new Token(TokenType.NUMBER, Double.parseDouble(source.substring(start, current))));
    }

    private void function() {
        while (Character.isAlphabetic(peek()) || isDigit(peek())) {
            advance();
        }

        tokens.add(new Token(TokenType.FUNCTION, MathFunction.getByToken(source.substring(start, current))));
    }

    public ExpressionParser(String source) {
        this.tokens = new ArrayList<>();
        this.source = source;
    }

    public ArrayList<Token> parse() {
        start = 0;
        current = 0;
        while (current < source.length()) {
            start = current;
            char c = advance();
            switch (c) {
                // Single character operators and parentheses
                case '(':
                    tokens.add(new Token(TokenType.LEFT_PARENTHESIS, null));
                    break;
                case ')':
                    tokens.add(new Token(TokenType.RIGHT_PARENTHESIS, null));
                    break;
                case '-':
                    tokens.add(new Token(TokenType.OPERATOR, Operator.SUBTRACT));
                    break;
                case '+':
                    tokens.add(new Token(TokenType.OPERATOR, Operator.ADD));
                    break;
                case '*':
                    tokens.add(new Token(TokenType.OPERATOR, Operator.MULTIPLY));
                    break;
                case '/':
                    tokens.add(new Token(TokenType.OPERATOR, Operator.DIVIDE));
                    break;
                case '%':
                    tokens.add(new Token(TokenType.OPERATOR, Operator.MODULO));
                    break;
                case '^':
                    tokens.add(new Token(TokenType.OPERATOR, Operator.EXPONENT));
                    break;
                case '!':
                    tokens.add(new Token(TokenType.OPERATOR, Operator.FACTORIAL));
                    break;

                // Ignore whitespace
                case ' ':
                case '\t':
                case '\r':
                    break;

                default:
                    if (isDigit(c)) {
                        number();
                    } else if (Character.isAlphabetic(c)) {
                        function();
                    } else {
                        System.out.printf("Unexpected character '%c'\n", c);
                        current = Integer.MAX_VALUE;
                    }
                    break;
            }
        }

        tokens.add(new Token(TokenType.EOF, null));
        return tokens;
    }

    public static double parseAndEvaluate(String expression) {
        return ReversePolishNotation
                .evaluate(ReversePolishNotation.makeRpn((new ExpressionParser(expression)).parse()));
    }

    public static double parseAndEvaluateReversePolish(String expression) {
        return ReversePolishNotation.evaluate((new ExpressionParser(expression)).parse());
    }
}