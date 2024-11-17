package org.example;

import java.util.Map;

/**
 * Класс Variable представляет переменную.
 */
public class Variable extends Expression {
    private final String name;

    /**
     * Конструктор для создания переменной.
     *
     * @param name имя переменной
     */
    public Variable(String name) {
        this.name = name;
    }

    @Override
    public String print() {
        return name;
    }

    @Override
    public Expression derivative(String variable) {
        return new Number(name.equals(variable) ? 1 : 0);  // Производная по переменной
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        return variables.getOrDefault(name, 0);
    }
}
