import org.example.*;
import org.junit.jupiter.api.Test;

import java.beans.Expression;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для классов математических выражений.
 */
public class ExpressionTest {

    @Test
    public void testPrint_Add_Mul_Variable() {
        // Пример 1: (3 + (2 * x))
        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        String expectedOutput = "(3+(2*x))";
        assertEquals(expectedOutput, e.print());
    }

    @Test
    public void testDerivative_Add_Mul_Variable() {
        // Пример 2: производная по x для выражения (3 + (2 * x))
        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        Expression derivative = e.derivative("x");
        String expectedDerivativeOutput = "(0+((0*x)+(2*1)))";
        assertEquals(expectedDerivativeOutput, derivative.print());
    }

    @Test
    public void testEval_Add_Mul_Variable() {
        // Пример 3: вычисление выражения (3 + (2 * x)) при x = 10
        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 10);
        int expectedResult = 23; // 3 + (2 * 10)
        assertEquals(expectedResult, e.eval(variables));
    }

    @Test
    public void testPrint_Sub_Div_Variable() {
        // Пример 4: (x - (6 / y))
        Expression e = new Sub(new Variable("x"), new Div(new Number(6), new Variable("y")));
        String expectedOutput = "(x-(6/y))";
        assertEquals(expectedOutput, e.print());
    }

    @Test
    public void testDerivative_Sub_Div_Variable() {
        // Пример 5: производная по x для выражения (x - (6 / y))
        Expression e = new Sub(new Variable("x"), new Div(new Number(6), new Variable("y")));
        Expression derivative = e.derivative("x");
        String expectedDerivativeOutput = "(1-(0/y))";
        assertEquals(expectedDerivativeOutput, derivative.print());
    }

    @Test
    public void testEval_Sub_Div_Variable() {
        // Пример 6: вычисление выражения (x - (6 / y)) при x = 8, y = 2
        Expression e = new Sub(new Variable("x"), new Div(new Number(6), new Variable("y")));
        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 8);
        variables.put("y", 2);
        int expectedResult = 5; // 8 - (6 / 2)
        assertEquals(expectedResult, e.eval(variables));
    }

    @Test
    public void testEval_Number() {
        // Пример 7: Выражение с константой 5
        Expression e = new Number(5);
        int expectedResult = 5;
        assertEquals(expectedResult, e.eval(new HashMap<>()));
    }

    @Test
    public void testDerivative_Number() {
        // Пример 8: Производная числа 5 по любой переменной должна быть 0
        Expression e = new Number(5);
        Expression derivative = e.derivative("x");
        assertTrue(derivative instanceof Number);
        assertEquals(0, ((Number) derivative).eval(new HashMap<>()));
    }

    @Test
    public void testEval_Variable_NoValueProvided() {
        // Пример 9: Выражение с переменной x, значение переменной не указано
        Expression e = new Variable("x");
        Map<String, Integer> variables = new HashMap<>();
        int expectedResult = 0; // Если значение не задано, вернётся 0
        assertEquals(expectedResult, e.eval(variables));
    }

    @Test
    public void testDerivative_Variable() {
        // Пример 10: Производная переменной x по x должна быть 1
        Expression e = new Variable("x");
        Expression derivative = e.derivative("x");
        assertTrue(derivative instanceof Number);
        assertEquals(1, ((Number) derivative).eval(new HashMap<>()));

        // Производная переменной y по x должна быть 0
        Expression e2 = new Variable("y");
        Expression derivative2 = e2.derivative("x");
        assertTrue(derivative2 instanceof Number);
        assertEquals(0, ((Number) derivative2).eval(new HashMap<>()));
    }

    @Test
    public void testEval_MultipleVariables() {
        // Пример 11: Выражение с
