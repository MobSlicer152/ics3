package dev.randomcode.calculator;

import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Expression parse() {
        try {
            return expression();
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    // Basically, the order these functions are called in determines the precedence
    private Expression expression() {
        return term();
    }

    // + and -
    private Expression term() {
        Expression expression = factor();

        while (match(Token.ADD, Token.SUBTRACT)) {
            Token operator = previous();
            Expression right = factor();
            expression = new Expression.Binary(expression, operator, right);
        }

        return expression;
    }

    // * and /
    private Expression factor() {
        Expression expression = unary();

        while (match(Token.DIVIDE, Token.MULTIPLY, Token.MODULO)) {
            Token operator = previous();
            Expression right = unary();
            expression = new Expression.Binary(expression, operator, right);
        }

        return expression;
    }

    // negation
    private Expression unary() {
        if (match(Token.SUBTRACT)) {
            Token operator = previous();
            Expression right = unary();
            return new Expression.Unary(operator, right);
        }

        return call();
    }

    // functions
    private Expression call() {
        Expression expression = primary();

        while (match(Token.LEFT_PARENTHESIS)) {
            expression = finishCall(expression);
        }

        return expression;
    }

    // TODO: implement multi-argument functions (those do exist in math, right?)
    private Expression finishCall(Expression callee) {
        Expression argument = expression();
        Token parenthesis = consume(Token.RIGHT_PARENTHESIS, "Missing ) after arguments");
        return new Expression.Call(callee, parenthesis, argument);
    }

    // literals (numbers)
    private Expression primary() {
        if (match(Token.ANY_NUMBER, Token.ANY_FUNCTION)) {
            return new Expression.Literal(previous().getData());
        }

        if (match(Token.LEFT_PARENTHESIS)) {
            Expression expression = expression();
            consume(Token.RIGHT_PARENTHESIS, "Missing ) after expression");
            return new Expression.Grouping(expression);
        }

        return null;
    }

    // Note: I'm not super thorough with error handling, for something simple like this
    // where the user will probably be able to see what's wrong
    private Token consume(Token type, String message) {
        if (check(type)) {
            return advance();
        }

        throw new RuntimeException(String.format("Error at %s: %s\n", peek().getLexeme(), message));
    }

    // My Token is different from the tutorial's, because of how my menus work
    private boolean check(Token token) {
        if (peek().getType() == token.getType()) {
            // Functions and operators store the type they are as their data
            if (token.getType() != TokenType.NUMBER && token.getData() != null) {
                return peek().getData() == token.getData();
            }

            return true;
        } else {
            return false;
        }
    }

    private boolean isAtEnd() {
        return current >= tokens.size() || peek().getType() == TokenType.EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        if (current < 1) {
            return null;
        }
        return tokens.get(current - 1);
    }

    private Token advance() {
        if (!isAtEnd()) {
            current++;
        }
        return previous();
    }

    private boolean match(Token... types) {
        for (Token type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }

        return false;
    }
}
