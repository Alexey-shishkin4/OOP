package org.example;

import java.util.Map;

/**
 * Класс Sub представляет операцию вычитания двух выражений.
 */
public class Sub extends Expression {
    private final Expression left, right;

    /**
     * Конструктор для создания операции вычитания.
     *
     * @param left левое выражение
     * @param right правое выражение
     */
    public Sub(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String print() {
        return "(" + left.print() + "-" + right.print() + ")";
    }

    @Override
    public Expression derivative(String variable) {
        return new Sub(left.derivative(variable), right.derivative(variable));
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        return left.eval(variables) - right.eval(variables);
    }
}