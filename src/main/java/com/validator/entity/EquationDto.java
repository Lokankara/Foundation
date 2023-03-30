package com.validator.entity;

import lombok.Getter;

@Getter
public class EquationDto {
    private final Long id;
    private final String expression;
    private final boolean solution;

    public EquationDto(Equation equation) {
        this.id = equation.getId() == null
                ? Long.valueOf(System.nanoTime())
                : equation.getId();
        this.expression = equation.getExpression();
        this.solution = equation.isSolution();
    }
}
