package dev.randomcode.calculator;

import dev.randomcode.calculator.Expression.Binary;
import dev.randomcode.calculator.Expression.Call;
import dev.randomcode.calculator.Expression.Grouping;
import dev.randomcode.calculator.Expression.Literal;
import dev.randomcode.calculator.Expression.Unary;

public class AstPrinter implements Expression.Visitor<String> {
    String print(Expression expression) {
        if (expression != null) {
            return expression.accept(this);
        } else {
            return null;
        }
    }

    @Override
    public String visitBinaryExpression(Binary expression) {
        return parenthesize(expression.operator.getLexeme(), expression.left, expression.right);
    }

    @Override
    public String visitGroupingExpression(Grouping expression) {
        return parenthesize("group", expression);
    }

    @Override
    public String visitLiteralExpression(Literal expression) {
        return expression.value.toString();
    }

    @Override
    public String visitUnaryExpression(Unary expression) {
        return parenthesize(expression.operator.getLexeme(), expression.right);
    }

    @Override
    public String visitCallExpression(Call expression) {
        // Recursive
        return parenthesize((new AstPrinter().print(expression.callee)), expression.argument);
    }

    private String parenthesize(String name, Expression... expressions) {
        // StringBuilder is more efficient with allocations than using +
        StringBuilder builder = new StringBuilder();

        builder.append("(").append(name);
        for (Expression expression : expressions) {
            builder.append(" ").append(expression.accept(this));
        }
        builder.append(")");

        return builder.toString();
    }
}
