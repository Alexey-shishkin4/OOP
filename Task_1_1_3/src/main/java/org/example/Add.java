package org.example;

import java.util.Map;

/**
 * Класс Add представляет операцию сложения двух выражений.
 */
public class Add extends Expression {
    private final Expression left, right;

    /**
     * Конструктор для создания операции сложения.
     *
     * @param left левое выражение
     * @param right правое выражение
     */
    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String print() {
        return "(" + left.print() + "+" + right.print() + ")";
    }

    @Override
    public Expression derivative(String variable) {
        return new Add(left.derivative(variable),
                right.derivative(variable));
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        return left.eval(variables)
                + right.eval(variables);
    }
}
