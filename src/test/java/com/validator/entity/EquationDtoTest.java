package com.validator.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("EquationDto Test")
class EquationDtoTest {

    @ParameterizedTest(name = "{index} => input=''{0}'', expected={1}")
    @CsvSource({
            "1, '3x + 4 * (5 - 2)) = 1', false",
            "2, '(x + 2)*(x - 4) = 6', true",
            "3, '(x + 1)-(3 - x) = 6', true",
            "4, '(x + 1)+(3 - x) = 6', true",
            "5, '', false"
    })
    void testEquationDto(Long id, String expression, boolean solution) {
        Equation equation = Equation.builder().id(id).expression(expression).solution(solution).build();
        EquationDto equationDto = new EquationDto(equation);
        assertEquals(id, equationDto.getId(), "ID is incorrect");
        assertEquals(expression, equationDto.getExpression(), "Expression is incorrect");
        assertEquals(solution, equationDto.isSolution(), "Solution is incorrect");
    }

    @ParameterizedTest
    @CsvSource({
            "2.0, 2",
            "5.5, 5.5",
            "-3.0, -3.0"
    })
    @DisplayName("Test addRoot with valid root")
    void testAddRootWithValidRoot(String root, Double expectedRootValue) {
        Root rootBuilder = Root.builder().root(root).build();
        Equation equation = Equation.builder().build();
        boolean result = equation.addRoot(rootBuilder);
        assertTrue(result);
        if (expectedRootValue == null) {
            assertNull(rootBuilder.getRoot());
        } else {
            assertEquals(expectedRootValue, Double.parseDouble(rootBuilder.getRoot()), 0.001);
        }
    }

    @ParameterizedTest
    @CsvSource({
            "2.0",
            "5.5",
            "-3.0"
    })
    @DisplayName("Test addRoot with valid root")
    void testAddRootWithValidRoot(String root) {
        Equation equation = Equation.builder().expression("x^2+1=10").build();
        boolean result = equation.addRoot(Root.builder().root(root).build());
        assertTrue(result);
    }

    @ParameterizedTest
    @CsvSource({
            "2.0",
            "5.5",
            "-3.0"
    })
    @DisplayName("Test addRoot with duplicate root")
    void testAddRootWithDuplicateRoot(String root) {
        Root rootBuilder1 = Root.builder().root(root).build();
        Root rootBuilder2 = Root.builder().root(root).build();

        Equation equation = Equation.builder().expression("x^2+1=10").build();
        equation.addRoot(rootBuilder1);
        boolean result = equation.addRoot(rootBuilder2);
        assertFalse(result);
    }
}
