package calculator;

import java.lang.Character;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class ExpressionParser {
    // This code is far from perfect or optimal, but it seems to handle the cases I can think of.
    // Mainly, parentheses are handled weirdly, and there are better ways of identifying tokens.
    // Additionally, it works on some pretty complex test inputs from ChatGPT.
    public static ArrayList<Token<?>> parse(String expression) {
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
                String tokenStr = expression.substring(i, tokenEnd);

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

    // Shunting yard algorithm to get Reverse Polish Notation
    // It doesn't reject all invalid expressions, but it can detect
    // mismatched parentheses and should work fine
    // https://en.wikipedia.org/wiki/Shunting_yard_algorithm#The_algorithm_in_detail
    public static String makeRpn(ArrayList<Token<?>> tokens) {
        Stack<Token<?>> operatorStack = new Stack<>();
        ArrayList<Token<?>> outputQueue = new ArrayList<>();

        for (Token<?> token : tokens) {
            System.out.println(token);
            switch (token.getType()) {
            case TokenType.NUMBER:
                outputQueue.add(token);
                break;
            case TokenType.FUNCTION:
            case TokenType.LEFT_PARENTHESIS:
                operatorStack.push(token);
                break;
            case TokenType.OPERATOR: {
                Operator o1 = (Operator)token.getData();
                // I think the description includes right parentheses as
                // operators, but they have the lowest precedence (I think),
                // so they don't come up in this condition anyway
                while (!operatorStack.isEmpty() && operatorStack.getFirst().getType() == TokenType.OPERATOR) {
                    Operator o2 = (Operator)operatorStack.getFirst().getData();
                    if (o2.getPrecedence() > o1.getPrecedence() ||
                        (o1.getPrecedence() == o2.getPrecedence() &&
                             o1.getAssociativity() == Associativity.LEFT)) {
                        outputQueue.add(operatorStack.removeFirst());
                    }
                }
                outputQueue.add(token);
                break;
            }
            case TokenType.RIGHT_PARENTHESIS:
                while (operatorStack.getFirst().getType() != TokenType.LEFT_PARENTHESIS) {
                    if (operatorStack.isEmpty()) {
                        System.out.println("Mismatched parentheses (case 1)");
                        return null;
                    }
                    outputQueue.add(operatorStack.removeFirst());
                }
                if (operatorStack.getFirst().getType() != TokenType.LEFT_PARENTHESIS) {
                    System.out.println("Mismatched parentheses (case 2)");
                    return null;
                }
                operatorStack.removeFirst();
                if (!operatorStack.isEmpty() && operatorStack.getFirst().getType() == TokenType.FUNCTION) {
                    outputQueue.add(operatorStack.removeFirst());
                }
                break;
            case TokenType.UNKNOWN:
            default:
                System.out.println("Ignoring unknown token");
                break;
            }
        }

        for (Token<?> token : operatorStack) {
            if (token.getType() == TokenType.LEFT_PARENTHESIS) {
                System.out.println("Mismatched parentheses (case 3)");
                return null;
            }
            outputQueue.add(operatorStack.removeFirst());
        }

        String rpn = "";
        for (Token<?> token : outputQueue) {
            System.out.println(token);
            rpn += String.format("%s ", token.getRpnString());
        }

        System.out.println(rpn);
        return rpn;
    }

    public static double parseAndEvaluate(String expression) {
        return ReversePolishParser.evaluate(makeRpn(parse(expression)));
    }
}