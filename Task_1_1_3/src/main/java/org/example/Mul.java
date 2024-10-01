package org.example;

import java.util.Map;


/**
 * Класс Mul представляет операцию умножения двух выражений.
 */
public class Mul extends Expression {
    private final Expression left, right;

    /**
     * Конструктор для создания операции умножения.
     *
     * @param left левое выражение
     * @param right правое выражение
     */
    public Mul(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String print() {
        return "(" + left.print() + "*" + right.print() + ")";
    }

    @Override
    public Expression derivative(String variable) {
        // (u*v)' = u'v + uv'
        return new Add(new Mul(left.derivative(variable),right),
                new Mul(left, right.derivative(variable)));
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        return left.eval(variables) * right.eval(variables);
    }
}