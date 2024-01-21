package calculator;

import java.lang.Double;

import java.util.ArrayList;
import java.util.Stack;

public class ReversePolishNotation {

    // Shunting yard algorithm to get Reverse Polish Notation
    // It doesn't reject all invalid expressions, but it can detect
    // mismatched parentheses and should work fine
    // https://en.wikipedia.org/wiki/Shunting_yard_algorithm#The_algorithm_in_detail
    public static ArrayList<Token<?>> makeRpn(ArrayList<Token<?>> tokens) {
        Stack<Token<?>> operatorStack = new Stack<>();
        ArrayList<Token<?>> outputQueue = new ArrayList<>();

        for (Token<?> token : tokens) {
            switch (token.getType()) {
                case TokenType.NUMBER:
                    outputQueue.add(token);
                    break;
                case TokenType.FUNCTION:
                case TokenType.LEFT_PARENTHESIS:
                    operatorStack.push(token);
                    break;
                case TokenType.OPERATOR: {
                    Operator o1 = (Operator) token.getData();
                    while (!operatorStack.isEmpty() && operatorStack.peek().getType() == TokenType.OPERATOR) {
                        Operator o2 = (Operator) operatorStack.peek().getData();
                        if (o2.getPrecedence() > o1.getPrecedence() ||
                                (o1.getPrecedence() == o2.getPrecedence() &&
                                        o1.getAssociativity() == Associativity.LEFT)) {
                            outputQueue.add(operatorStack.pop());
                        } else {
                            break;
                        }
                    }
                    operatorStack.push(token);
                    break;
                }
                case TokenType.RIGHT_PARENTHESIS:
                    while (operatorStack.peek().getType() != TokenType.LEFT_PARENTHESIS) {
                        if (operatorStack.isEmpty()) {
                            System.out.println("Mismatched parentheses (case 1)");
                            return null;
                        }
                        outputQueue.add(operatorStack.pop());
                    }
                    if (operatorStack.peek().getType() != TokenType.LEFT_PARENTHESIS) {
                        System.out.println("Mismatched parentheses (case 2)");
                        return null;
                    }
                    operatorStack.pop();
                    if (!operatorStack.isEmpty() && operatorStack.peek().getType() == TokenType.FUNCTION) {
                        outputQueue.add(operatorStack.pop());
                    }
                    break;
                case TokenType.UNKNOWN:
                default:
                    System.out.println("Ignoring unknown token");
                    break;
            }
        }

        while (!operatorStack.isEmpty()) {
            if (operatorStack.peek().getType() == TokenType.LEFT_PARENTHESIS) {
                System.out.println("Mismatched parentheses (case 3)");
                return null;
            }
            outputQueue.add(operatorStack.pop());
        }

        return outputQueue;
    }

    public static double evaluate(ArrayList<Token<?>> tokens) {
        Stack<Double> stack = new Stack<>();
        for (Token<?> token : tokens) {
            switch (token.getType()) {
                // Push numbers to the stack
                case TokenType.NUMBER:
                    // NUMBER tokens always have Double as their type parameter
                    stack.push((Double) token.getData());
                    System.out.printf("Pushed %s\n", stack.peek());
                    break;
                // Execute functions on however many arguments they take (rejecting the
                // expression if there aren't enough)
                case TokenType.FUNCTION: {
                    MathFunction function = (MathFunction) token.getData();
                    if (stack.size() < function.getArgumentCount()) {
                        System.out.printf("Invalid use of %s (expected %d arguments, got %d), cannot continue\n",
                                function, function.getArgumentCount(), stack.size());
                        break;
                    }
                    Double[] arguments = new Double[function.getArgumentCount()];
                    // Keep elements in the right order
                    for (int i = function.getArgumentCount() - 1; i > 0; i--) {
                        arguments[i] = stack.pop();
                    }
                    stack.push(function.execute(arguments));
                    break;
                }
                // Do the same with operators
                case TokenType.OPERATOR: {
                    Operator operator = (Operator) token.getData();
                    if (stack.size() < operator.getOperandCount()) {
                        System.out.printf("Invalid use of %s (expected %d arguments, got %d), cannot continue\n",
                                operator, operator.getOperandCount(), stack.size());
                        break;
                    }
                    Double[] arguments = new Double[operator.getOperandCount()];
                    // Keep elements in the right order
                    for (int i = operator.getOperandCount() - 1; i >= 0; i--) {
                        arguments[i] = stack.pop();
                    }
                    stack.push(operator.execute(arguments));
                    break;
                }
                // Do nothing with other tokens, they're not meant to be here
                default:
                    break;
            }
        }

        // For correct expressions only one number should be left on the stack, right?
        return stack.pop();
    }
}