package org.example;

import java.util.HashMap;
import java.util.Map;


/**
 * Основной класс с методом main для демонстрации работы выражений.
 */
public class Main {
    public static void main(String[] args) {
        // Пример 1: Выражение (3 + (2 * x))
        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        System.out.println("Expression:");
        System.out.println(e.print());  // Печать выражения (3+(2*x))

        // Пример 2: Производная по x
        Expression de = e.derivative("x");
        System.out.println("Derivative:");
        System.out.println(de.print());  // Печать производной (0+((0*x)+(2*1)))

        // Пример 3: Вычисление значения выражения при x = 10
        Map<String, Integer> variables = parseVariableAssignments("x = 10; y = 13");
        int result = e.eval(variables);
        System.out.println("Evaluation result:");
        System.out.println(result);  // Результат 23 (3 + (2 * 10))
    }

    /**
     * Парсит строку с назначениями переменных и возвращает
     * карту переменных и их значений.
     *
     * @param variableAssignments строка вида "x = 10; y = 13"
     * @return карта, где ключ — это имя переменной, а значение — её значение
     */
    public static Map<String, Integer> parseVariableAssignments(String variableAssignments) {
        Map<String, Integer> variableValues = new HashMap<>();
        String[] assignments = variableAssignments.split(";");
        for (String assignment : assignments) {
            String[] parts = assignment.split("=");
            String variable = parts[0].trim();
            int value = Integer.parseInt(parts[1].trim());
            variableValues.put(variable, value);
        }
        return variableValues;
    }
}