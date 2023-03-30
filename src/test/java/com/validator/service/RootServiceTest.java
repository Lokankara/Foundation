package com.validator.service;

import com.validator.dao.JdbcRootDao;
import com.validator.dao.RootDao;
import com.validator.entity.Equation;
import com.validator.entity.Root;
import com.validator.exception.DaoException;
import com.validator.exception.ServiceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RootServiceTest {
    private static final RootDao rootDao = mock(JdbcRootDao.class);
    private static final RootService rootService = new RootService(rootDao);
    public static final String ERROR_MESSAGE = "Database connection error";

    @DisplayName("Test addRoot method with various scenarios")
    @ParameterizedTest(name = "Scenario {index}: root={0}")
    @CsvSource({
            "10.0, 1",
            "No roots, 1",
            "10.0,",
            "No roots, "
    })
    void testAddRoot(String rootValue, Long equationId) {
        doThrow(new RuntimeException("Error saving root")).when(rootDao).saveRoot(any(Root.class));
        Equation equation = Equation.builder().id(equationId).build();
        Root root = Root.builder().root(rootValue).equation(equation).build();
        assertThrows(ServiceException.class,
                () -> rootService.addRoot(root), "Expected ServiceException");
        verify(rootDao, times(1)).

                saveRoot(root);
    }

    @Test
    @DisplayName("Call the getAll() method and verify that it throws a ServiceException")
    void testGetAllWithException() throws DaoException {
        JdbcRootDao jdbcRootDaoMock = Mockito.mock(JdbcRootDao.class);
        Mockito.when(jdbcRootDaoMock.findAll()).thenThrow(new DaoException("DaoException"));
        try {
            rootService.getAll();
        } catch (ServiceException exception) {
            assertTrue(exception.getCause() instanceof DaoException);
        }
    }

    @DisplayName("Test getAll() method in RootService")
    @ParameterizedTest(name = "{index} - Test with id={0}")
    @CsvSource({
            "1",
            "2",
            "3"
    })
    void testGetAll() throws ServiceException {
        RootDao mockDao = mock(RootDao.class);
        RootService rootService = new RootService(mockDao);
        when(rootService.getAll()).thenThrow(new DaoException(ERROR_MESSAGE));

        ServiceException exception = assertThrows(ServiceException.class, rootService::getAll);
        assertEquals(ERROR_MESSAGE, exception.getMessage());
    }
}