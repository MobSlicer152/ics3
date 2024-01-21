package dev.randomcode.calculator;

import java.util.ArrayList;

// Based on https://craftinginterpreters.com/scanning.html,
// but stripped down to support just mathematical expressions
public class TokenScanner {
    final String source;
    final ArrayList<Token> tokens;
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

    public TokenScanner(String source) {
        this.tokens = new ArrayList<>();
        this.source = source;
    }

    public ArrayList<Token> scanTokens() {
        start = 0;
        current = 0;
        while (current < source.length()) {
            start = current;
            char c = advance();
            switch (c) {
                // Single character operators and parentheses
                case '(':
                    tokens.add(Token.LEFT_PARENTHESIS);
                    break;
                case ')':
                    tokens.add(Token.RIGHT_PARENTHESIS);
                    break;
                case '-':
                    tokens.add(Token.SUBTRACT);
                    break;
                case '+':
                    tokens.add(Token.ADD);
                    break;
                case '*':
                    tokens.add(Token.MULTIPLY);
                    break;
                case '/':
                    tokens.add(Token.DIVIDE);
                    break;
                case '%':
                    tokens.add(Token.MODULO);
                    break;
                case '^':
                    tokens.add(Token.EXPONENT);
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

    public static Expression scanAndParse(String source) {
        return new Parser(
            new TokenScanner(source)
                .scanTokens()
        ).parse();
    }

    public static double scanParseAndEvaluate(String source) {
        return new Evaluator().evaluate(scanAndParse(source));
    }
}