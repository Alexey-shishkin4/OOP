package org.example;


import java.util.Map;

/**
 * Класс Number представляет константу.
 */
class Number extends Expression {
    private final int value;

    /**
     * Конструктор для создания константы.
     *
     * @param value значение константы
     */
    public Number(int value) {
        this.value = value;
    }

    @Override
    public String print() {
        return Integer.toString(value);
    }

    @Override
    public Expression derivative(String variable) {
        return new Number(0);  // Производная числа — это 0
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        return value;
    }
}
