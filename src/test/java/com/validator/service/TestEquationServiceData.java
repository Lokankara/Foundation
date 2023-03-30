package com.validator.service;

import com.validator.entity.Equation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.validator.service.Validator.*;
import static org.junit.jupiter.api.Assertions.*;

class TestEquationServiceData {

    @Test
    @DisplayName("Solve equation")
    void testSolve() {
        Equation equation = Equation.builder().expression("x^2+1=10").build();
        String result = solve(equation);
        assertEquals("-3.0", result);
    }

    @Test
    @DisplayName("Solve equation - no solution found")
    void testSolveNoSolution() {
        Equation equation = Equation.builder().expression("x^2+1=0").build();
        String result = solve(equation);
        assertEquals("No roots", result);
    }

    @ParameterizedTest
    @CsvSource({
            "2*x+5=17, 6.0",
            "x*2-4=0, 2.0",
            "x-2*2=0, 4.0",
            "x/2-1=0, 2.0"
    })
    @DisplayName("Solve equation: {}, expected result: {}")
    void testSolve(String expression, String expectedResult) {
        Equation equation = Equation.builder().expression(expression).build();
        String result = solve(equation);
        assertEquals(expectedResult, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "2*x+5=17",
            "(x-4)/5=9+(2x+4)/9",
            "2*x-3=0",
            "2*x+12-x-9=0",
            "-3*5-x=2"})
    void validEquations(String equation) {
        assertTrue(checkExpression(equation));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "2*+5=17",
            "1+2x=5x",
            "(10+2x)*2=5x",
            "1+2=5x",
            "(x-4/5=9+(2x+4)/9",
            "(10+2x*2=5х",
            "2*x-=3=0",
            "2*x+12-x-9=",
            "-3*5-x=2))"})
    void invalidEquations(String equation) {
        System.out.println(equation + " " + checkExpression(equation));
        assertFalse(checkExpression(equation));
    }

    @ParameterizedTest
    @CsvSource({
            "2*x+5=17,true",
            "x^2+1=0,false",
            "x/2-1=0,true",
            "5-x=2*x+5=17,false",
            "3+*4-7-x=2,false",
            "-3*5.7-x=2,true",
            "-3*5.7-a=2,false",
            "-3*5.7-x'=2,false",
            "{10+2*2x}*2=5*x,false",
            "10+2x2=5-x,false",
            "2*x+5=17,true",
            "1+2*x=5*x,true",
            "1+2*x-5=x,true",
            "(x-4)/5=9+(2*x+4)/9,true",
            "2*x-3=0,true",
            "2*x+12-x-9=0,true",
            "3*5.7-x*2=2,true",
            "-3+5*-x=2,true",
            "4*-7+2*x=0,true"
    })
    @DisplayName("Test Equations: {}")
    void testEquations(String equation, boolean expectedResult) {
        assertEquals(expectedResult, checkExpression(equation));
        if (checkExpression(equation) != expectedResult) {
            System.out.println(equation);

        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"2*x+5=17",
            "1+2*x=5*x",
            "1+2*x-5=x",
            "(x-4)/5=9+(2*x+4)/9",
            "2*x-3=0",
            "2*x+12-x-9=0",
            "3*5.7-x*2=2",
            "-3+5*-x=2",
            "4*-7+2*x=0"})
    @DisplayName("invalidEquations: {}")
    void checkValidEquations(String equation) {
        assertTrue(checkExpression(equation));
    }

    @ParameterizedTest
    @CsvSource({
            "5-x=2*x+5=17, false",
            "3+*4-7-x=2, false",
            "-3*5.7-x=2, true",
            "-3*5.7-a=2, false",
            "-3*5.7-x'=2, false",
            "{10+2*2x}*2=5*х, false",
            "10+2x*2=5-х, false"
    })
    @DisplayName("Equations: {}, expected result: {}")
    void checkEquations(String equation, boolean expectedResult) {
        assertEquals(expectedResult, checkExpression(equation));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "2(x - 3) - (4 - x) = 3(x + 1)",
            "((2*x + 1) * (3 - 4)) / (5 + (6 - x)) = 2*x",
            "2*x + ((3 - x) / (2 + (5 - 3*x))) = 5",
            "(x - 1) / ((2*x - 3) / (4 + x)) = 6 - x"})
    @DisplayName("validEquations: {}")
    void checkValidEquationsWithBraces(String equation) {
        assertTrue(checkExpression(equation));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "(2x +1",
            "2x + 3(4x + 2) = 5x - 1",
            "(3x + 2) / (2x - 3) = 2 + (x + 1) / (x - 2)",
            "3x - (2x + 5) = 7",
            "(x + 2)(3*x - 4) = 6",
            "(x + 2)(x - 4) = 6",
            "(x + 2)(x - 4) = 6",
            "((3x + 5)",
            "((2x - 1) + 3x))",
            "((5x + 2) / (2x - 4)",
            "(2x +1))=0",
            "(2x -(3) +1)) = x",
            "((x+1)) - 2(3x+4)) = 0",
            "(x+1) - 2(3x+4))) = 0",
            "(2x +1))=0",
            "(2x -(3) +1)) = x",
            "2x+((3x+2)/5=7",
            "2x+3=5)x"})
    @DisplayName("invalidEquations: {}")
    void checkInvalidEquationsWithBraces(String equation) {
        assertFalse(checkExpression(equation));
    }

    @ParameterizedTest
    @CsvSource({
            "2*x+5=17, true",
            "1+2*x=5*x, true",
            "1+2*x-5=x, true",
            "(x-4)/5=9+(2*x+4)/9, true",
            "2*x-3=0, true",
            "2*x+12-x-9=0, true",
            "3*5.7-x*2=2, true",
            "-3+5*-x=2, true",
            "4*-7+2*x=0, true"
    })
    @DisplayName("Equations: {}, expected result: {}")
    void checkEquationsWithCsvSource(String equation, boolean expectedResult) {
        assertEquals(expectedResult, checkExpression(equation));
    }

    @ParameterizedTest(name = "Equation {index}: {0}")
    @CsvSource({
            "(x + 2)(3x - 4) = 6,false",
            "(x + 2)*(x - 4) = 6,true",
            "(x + 1)-(3 - x) = 6,true",
            "(x + 1)+(3 - x) = 6,true"
    })
    void testHasOperatorBetweenBraces(String equation, boolean expected) {
        boolean actual = hasOperatorBetweenBraces(equation);
        assertEquals(expected, actual);
    }

    @ParameterizedTest(name = "Equation: {0}, Expected Result: {1}")
    @CsvSource({
            "(x + 2)(3x - 4) = 6, false",
            "(x + 2)*(x - 4) = 6, true",
            "(x + 1)-(3 - x) = 6, true",
            "(x + 1)+(3 - x) = 6, true",
            "(2x+3)(4-2x)=5x+6, false",
            "3*x + 4 * (5 - 2) = (1 + 2), true"
    })
    void testEquationBrace(String equation, boolean expectedResult) {
        assertEquals(expectedResult, hasOperatorBetweenBraces(equation));
    }

    @DisplayName("Test expressions with braces")
    @ParameterizedTest(name = "{index}: expression=''{0}'', expected={1}")
    @CsvSource({
            "(x + 2)(3x - 4) = 6, false",
            "(x + 2)*(x - 4) = 6, true",
            "(x + 1)-(3 - x) = 6, true",
            "(x + 1)+(3 - x) = 6, true"
    })
    void testHasOperatorWithBraces(String expression, boolean expected) {
        boolean actual = hasOperatorBetweenBraces(expression);
        assertEquals(expected, actual, "Failed for expression: " + expression);
    }

    @DisplayName("Test Valid Braces")
    @ParameterizedTest(name = "{index}: expression=''{0}'', expected={1}")
    @CsvSource({
            "(x + 2)*(x - 4) = 6, true",
            "((x + 1)-(3 - x)) = 6, true",
            "(x + 1)+(3 - x) = 6, true",
            "3x + 4 * (5 - 2)) = 1, false",
            "(x + 2)*(x - 4)) = 6, false",
            "(x + 2)*(x - 4) = 6), false",
            ")(x + 2)*(x - 4) = 6(, false",
            "(x + 2)*(x - 4) = 6, true",
            "((x + 1)-(3 - x)) = 6, true",
            "(x + 1)+(3 - x) = 6, true",
            "3x + 4 * (5 - 2)) = 1, false",
            "(x + 2)*(x - 4)) = 6, false",
            "(x + 2)*(x - 4) = 6), false",
            ")(x + 2)*(x - 4) = 6(, false",
    })
    void testIsValidBraces(String expression, boolean expected) {
        boolean actual = isValidBraces(expression);
        assertEquals(expected, actual);
    }

    @DisplayName("Check if a value is a root of an equation")
    @ParameterizedTest(name = "{index}: Equation: {0}, Root: {1} => Is Root: {2}")
    @CsvSource({
            "2*x+5=17, 6, true",
            "3*x-10=5, 5, true",
            "x-5=0, 5, true",
            "x-5=0, 0, false",
            "3*x-10=5, 3.33, false",
            "3*x-10=5, 10, false",
            "2*x+5=17, 1, false",
            "x-5=0, 6, false",
    })
    void checkRootOfTest(String expression, String root, boolean expected) {
        boolean actual = Validator.checkRootOf(expression, root);
        assertEquals(expected, actual);
    }

    @ParameterizedTest(name = "{index} - Expression: {0}, Expected Root: {1}, Expected Result: {2}")
    @CsvSource({
            "2*x+5=17, 6, true",
            "3*x-10=5, 5, true",
            "x-5=0, 5, true",
            "x-5=0, 0, false",
            "(x+2)/3=5, 13, true",
    })
    void testCalculate(String expression, String expectedRoot, boolean expectedResult) {
        Equation equation = Equation.builder().expression(expression).build();
        assertEquals(expectedResult, checkRootOf(equation.getExpression(), expectedRoot));
    }

    @DisplayName("Test solve method")
    @ParameterizedTest(name = "{index} - Equation: {0}, Expected Solution: {1}")
    @CsvSource({
            "2*x-17=-5, 6.0",
            "3*x=15, 5.0",
            "x=5, 5.0",
            "x=-5, -5.0",
            "3*x-6=0, 2.0",
            "5*x-10=0, 2.0"
    })
    void testSolves(String equation, String expectedSolution) {
        Equation expression = Equation.builder().expression(equation).build();
        String solve = solve(expression);
        assertTrue(expression.isSolution());
        assertEquals(expectedSolution, solve);
    }
}
