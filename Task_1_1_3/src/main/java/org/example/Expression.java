package org.example;

import java.util.Map;


/**
 * Абстрактный класс Expression представляет математическое выражение.
 * Содержит методы для вывода выражения, его дифференцирования и вычисления значения.
 */
abstract class Expression {
    /**
     * Возвращает строковое представление выражения.
     *
     * @return строка, представляющая математическое выражение
     */
    public abstract String print();

    /**
     * Возвращает производную выражения по указанной переменной.
     *
     * @param variable переменная, по которой берется производная
     * @return производная выражения
     */
    public abstract Expression derivative(String variable);

    /**
     * Вычисляет значение выражения, используя значения переменных.
     *
     * @param variables карта, в которой ключом является имя переменной, а значением — её значение
     * @return результат вычисления выражения
     */
    public abstract int eval(Map<String, Integer> variables);
}
