package com.validator.dao;

import com.validator.entity.Equation;
import com.validator.exception.DaoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Arrays;

import static com.validator.dao.QueriesContext.FIND_BY_LIST_ROOTS;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class PersistenceEquationDaoTest {
    private static final String FIND_BY_EXPRESSION = "SELECT e FROM Equation e WHERE e.expression = :expression";
    private static final String VALID_EXPRESSION = "2*x + 3 = 7";
    private static final String INVALID_EXPRESSION = "2x + 3 = 7";

    private final TypedQuery<Equation> query = mock(TypedQuery.class);
    private final EntityManager entityManager = mock(EntityManager.class);
    private final PersistenceEquationDao equationDao = new PersistenceEquationDao(entityManager);

    @DisplayName("Test findByRoots method with exception")
    @Test
    void testFindByRootsWithException() throws DaoException {
        when(entityManager.createQuery(anyString(), eq(Equation.class))).thenReturn(query);
        doThrow(new RuntimeException("Database error")).when(query).getResultList();
        assertThrows(DaoException.class, () -> equationDao.findByRoots(FIND_BY_LIST_ROOTS, Arrays.asList(1.0, 2.0)));
    }

    @Test
    @DisplayName("Test findByExpression with invalid expression")
    void testFindByInvalidExpression() {
        assertThrows(DaoException.class, () -> equationDao.findByExpression(FIND_BY_EXPRESSION, INVALID_EXPRESSION));
    }
}


//    @DisplayName("Test findByRoot method")
//    @ParameterizedTest(name = "Test {index}: root={0}")
//    @CsvSource({"0", "1", "2", "3"})
//    void testFindByRoot() {
//        Root root = Root.builder().root("root").build();
//        List<Equation> equations = null;
//        try {
//            equations = equationDao.findEquations(FIND_BY_ROOT, "root", root.getRoot());
//        } catch (DaoException e) {
//            fail("Exception thrown: " + e.getMessage());
//        }
//        assertNotNull(equations);
//        assertFalse(equations.isEmpty());
//        for (Equation equation : equations) {
//            assertTrue(equation.getRoots().stream().anyMatch(r -> r.getRoot() == root.getRoot()));
//        }
//    }
