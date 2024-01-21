package dev.randomcode.calculator;

import dev.randomcode.calculator.Expression.Binary;
import dev.randomcode.calculator.Expression.Call;
import dev.randomcode.calculator.Expression.Grouping;
import dev.randomcode.calculator.Expression.Literal;
import dev.randomcode.calculator.Expression.Unary;

public class Evaluator implements Expression.Visitor<Object> {
    private Object evaluateInternal(Expression expression) {
        return expression.accept(this);
    }

    public double evaluate(Expression expression) {
        try {
            if (expression != null) {
                return (double) evaluateInternal(expression);
            } else {
                return Double.NaN;
            }
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            return Double.NaN;
        }
    }

    @Override
    public Object visitBinaryExpression(Binary expression) {
        Object left = (double) evaluateInternal(expression.left);
        Object right = (double) evaluateInternal(expression.right);
        Object operator = expression.operator.getData();

        // Make sure all casts are legal
        if (!(left instanceof Double) || !(right instanceof Double)
                || !(expression.operator.getData() instanceof Operator)) {
            throw new RuntimeException(String.format("Error: cannot apply binary operator %s to %s and %s\n", operator, left, right));
        }

        return ((Operator)operator).execute(new Double[] { (double) left, (double) right });
    }

    @Override
    public Object visitGroupingExpression(Grouping expression) {
        return evaluateInternal(expression.expression);
    }

    @Override
    public Object visitLiteralExpression(Literal expression) {
        return expression.value;
    }

    @Override
    public Object visitCallExpression(Call expression) {
        Object callee = evaluateInternal(expression.callee);
        Object argument = evaluateInternal(expression.argument);
        if (!(callee instanceof MathFunction) || !(argument instanceof Double)) {
            throw new RuntimeException(String.format("Error: cannot call %s on %s\n", callee, argument));
        }

        return ((MathFunction)callee).execute(new Double[]{(double)argument});
    }

    @Override
    public Object visitUnaryExpression(Unary expression) {
        Object right = evaluateInternal(expression.right);

        switch (expression.operator.getType()) {
            case OPERATOR:
                switch ((Operator) expression.operator.getData()) {
                    case SUBTRACT:
                        return -(double) right;
                    // Unreachable
                    default:
                        return null;
                }
                // Unreachable
            default:
                return null;
        }
    }

}
