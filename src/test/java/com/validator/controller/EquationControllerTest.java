package com.validator.controller;

import com.validator.entity.Equation;
import com.validator.service.EquationService;
import com.validator.service.RootService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.validator.dao.QueriesContext.FIND_BY_EQUATION;
import static com.validator.service.Validator.removeSpaces;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class EquationControllerTest {

    @Mock
    EquationService equationService;
    EquationController equationController;
    RedirectAttributes mockModel;
    String expression = "2 * x + 5 = 17";
    String redirect = "redirect:/";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        equationController = new EquationController(equationService);
        mockModel = Mockito.mock(RedirectAttributes.class);
    }

    @SneakyThrows
    @Test
    @DisplayName("Test save Equation with correct expression")
    void testAddEquationWithCorrectExpression() {
        MockitoAnnotations.openMocks(this);
        when(equationService.save(Equation.builder().id(1L).expression("2*x+5=17").build())).thenReturn(null);
        String result = equationController.addEquation(expression, mockModel);
        assertEquals(redirect, result);
    }

    @SneakyThrows
    @ParameterizedTest
    @CsvSource({
            "2*x + 5 = 17, redirect:/",
            "3*x - 4 = 0, redirect:/"
    })
    void testAddEquationWithCorrectExpression(String expression, String expectedRedirect) {
        when(equationService.findByExpression(FIND_BY_EQUATION, removeSpaces(expression))).thenReturn(null);
        String result = equationController.addEquation(expression, mockModel);
        assertEquals(expectedRedirect, result);
    }

    @SneakyThrows
    @Test
    @DisplayName("Test add Equation with existing expression")
    void testAddEquationWithExistingExpression() {
        MockitoAnnotations.openMocks(this);
        when(equationService.findByExpression(FIND_BY_EQUATION, "2*x+5=17")).thenReturn(Equation.builder().id(1L).expression("2*x+5=17").build());
        String result = equationController.addEquation(expression, null);
        assertEquals(redirect, result);
    }

    @Test
    @DisplayName("Test add Equation with incorrect expression")
    void testAddEquationWithIncorrectExpression() {
        MockitoAnnotations.openMocks(this);
        equationController = new EquationController(equationService);
        String expression = "2*x + 5 = 17 + ";
        String result = equationController.addEquation(expression, null);
        assertEquals("redirect:/", result);
    }
}
