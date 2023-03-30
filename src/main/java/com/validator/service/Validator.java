package com.validator.service;

import com.validator.entity.Equation;
import com.validator.entity.Root;
import lombok.extern.slf4j.Slf4j;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
public final class Validator {

    private Validator() {
    }

    public static boolean checkExpression(final String expression) {
        return expression != null
                && isValid(removeSpaces(expression))
                && isValidBraces(expression);
    }

    public static String removeSpaces(final String equation) {
        return equation.replaceAll("\\s", "");
    }

    private static boolean isValid(final String expression) {
        return !isHasTwoEquals(expression)
                && isMatches(expression)
                && !hasOperatorBeforeOrAfter(expression)
                && hasValidCharacters(expression)
                && hasValidEqualsPosition(expression)
                && hasNoXWithOperators(expression)
                && isXAlone(expression)
                && hasOperatorBetweenBraces(expression);
    }

    public static boolean hasOperatorBetweenBraces(final String expression) {
        int i = expression.indexOf(")");
        return i != (expression.length() - 1)
                && expression.charAt(i + 1) != '(';
    }


    private static boolean isXAlone(final String equation) {
        int xIndex = equation.indexOf('x');
        if (xIndex != equation.length() - 1) {
            return xIndex == 0 ? !Character.isDigit(xIndex + 1)
                    : !Character.isDigit(equation.charAt(xIndex - 1))
                    && !Character.isDigit(equation.charAt(xIndex + 1));
        }
        return !Character.isDigit(equation.charAt(equation.length() - 2));
    }

    private static boolean hasNoXWithOperators(final String expression) {
        return expression.contains("x");
    }

    private static boolean hasValidCharacters(final String expression) {
        return expression.matches("^[()=x\\d\\s*+\\-/.]+$")
                && !expression.matches("[^()=x\\d\\s*+\\-/.]");
    }

    private static boolean hasValidEqualsPosition(final String expression) {
        int equalsIndex = expression.indexOf('=');
        return equalsIndex >= 0
                && equalsIndex < expression.length() - 1 && equalsIndex > 0;
    }

    private static boolean isMatches(final String expression) {

        return expression.matches("^[()=x\\d\\s*+\\-/.]+$");
    }

    private static boolean isHasTwoEquals(final String expression) {
        return IntStream
                .range(0, expression.toCharArray().length)
                .filter(i -> expression.charAt(i) == '=')
                .count() > 1;
    }

    private static boolean hasOperatorBeforeOrAfter(final String expression) {
        char[] charArray = expression.toCharArray();
        return IntStream.range(0, charArray.length - 1)
                .anyMatch(i -> isOperator(charArray[i]) && isOperator(charArray[i + 1])
                        && !hasTwoValidOperators(expression));
    }

    private static boolean hasTwoValidOperators(final String expression) {
        return Stream.of("-*", "+*", "+/", "-/", "*/", "-+")
                .noneMatch(expression::contains);
    }

    private static boolean isOperator(final char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    static boolean isValidBraces(final String expression) {
        int countOpenBraces = 0;
        int countCloseBraces = 0;
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '(') {
                countOpenBraces++;
                stack.push(expression.charAt(i));
            } else if (expression.charAt(i) == ')') {
                countCloseBraces++;
                if (stack.isEmpty()) {
                    return false;
                }
                stack.pop();
            }
        }
        return stack.isEmpty() && countOpenBraces == countCloseBraces;
    }

    public static String solve(final Equation equation) {
        String[] sides = equation.getExpression().split("=");
        Expression leftSide = new ExpressionBuilder(sides[0]).variable("x").build();
        Expression rightSide = new ExpressionBuilder(sides[1]).build();
        double rightSideValue = rightSide.evaluate();
        for (double i = -100; i < 101; i += 0.001) {
            double leftSideValue = leftSide.setVariable("x", i).evaluate();
            if (Math.abs(leftSideValue - rightSideValue) < 0.0001) {
                equation.setSolution(true);
                Set<Root> roots = equation.getRoots();

                Root root = Root.builder()
                        .rootId(System.currentTimeMillis() >> 48 & 0x0FFF)
                        .root(String.valueOf(Math.round(i * 1000.0) / 1000.0))
                        .equation(equation)
                        .build();
                if (roots == null) {
                    roots = new HashSet<>();
                }
                roots.add(root);
                equation.setRoots(roots);
                log.info("solution: " + equation);
                return root.getRoot();
            }
        }
        return ("No roots");
    }

    public static boolean checkRootOf(String equation, String root) {
        String expression = equation.replace("x", root);
        String rearranged = rearrangeEquation(expression);
        return calculate(rearranged);
    }

    private static boolean calculate(String expression) {
        try {
            return Math.abs(
                    new ExpressionBuilder(expression)
                            .build().evaluate())
                    < 1e-6;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static String rearrangeEquation(String equation) {
        String[] parts = equation.split("=");

        String leftHandSide = IntStream.range(1, parts.length)
                .mapToObj(i -> "-(" + parts[i] + ")")
                .collect(Collectors.joining("", parts[0], ""));

        String[] split = leftHandSide.replace("--", "+").split("=");
        return IntStream.range(1, split.length)
                .mapToObj(i -> "-(" + split[i] + ")")
                .collect(Collectors.joining("", split[0], ""));
    }
}
