package org.example;

import java.util.Map;

/**
 * Класс Div представляет операцию деления двух выражений.
 */
class Div extends Expression {
    private final Expression left, right;

    /**
     * Конструктор для создания операции деления.
     *
     * @param left левое выражение
     * @param right правое выражение
     */
    public Div(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String print() {
        return "(" +left.print()
                + "/" + right.print() + ")";
    }

    @Override
    public Expression derivative(String variable) {
        // (u/v)' = (u'v - uv') / v^2
        return new Div(
                new Sub(new Mul(left.derivative(variable), right),
                        new Mul(left, right.derivative(variable))),
                new Mul(right, right)
        );
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        return left.eval(variables) / right.eval(variables);
    }
}