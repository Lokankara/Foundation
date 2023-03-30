package com.validator.dao;

public final class QueriesContext {
    private QueriesContext() {
    }
    public static final String FIND_ALL = "SELECT e FROM Equation e LEFT JOIN FETCH e.roots";
    public static final String FIND_BY_SINGLE_ROOT = "SELECT DISTINCT e FROM Equation e JOIN FETCH e.roots r WHERE (SELECT COUNT(r) FROM e.roots r) = 1";

    public static final String FIND_BY_ROOT = "SELECT DISTINCT e FROM Equation e JOIN FETCH e.roots r WHERE r.root = :root";

    public static final String FIND_BY_EQUATION = "SELECT e FROM Equation e WHERE e.expression = :expression";

    public static final String FIND_BY_NUMBER_ROOTS = "SELECT e FROM Equation e JOIN e.roots r GROUP BY e HAVING COUNT(r) = :count";
    public static final String FIND_BY_LIST_ROOTS = "SELECT e FROM Equation e JOIN e.roots r WHERE r.root IN :roots GROUP BY e HAVING COUNT(r) = :count";

    public static final String DELETE_EQUATION = "DELETE FROM equation WHERE id=?";
    public static final String FIND_BY_ID = "SELECT * FROM equation WHERE id = ?";
    public static final String FIND_BY = "SELECT * FROM equation e INNER JOIN root r ON e.id = r.equation_id GROUP BY e.id HAVING COUNT(r.root_id) = ?";
    public static final String INSERT = "INSERT INTO equation (id, expression, correct_brackets, correct_expression) VALUES (?, ?, ?, ?)";
}
