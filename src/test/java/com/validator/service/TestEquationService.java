package com.validator.service;

import com.validator.dao.PersistenceEquationDao;
import com.validator.entity.Equation;
import com.validator.exception.DaoException;
import com.validator.exception.ServiceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.validator.dao.QueriesContext.FIND_ALL;
import static com.validator.dao.QueriesContext.FIND_BY_ROOT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TestEquationService {
    public static final String ERROR = "Database error";
    PersistenceEquationDao persistenceEquationDao = mock(PersistenceEquationDao.class);

    @Test
    @DisplayName("Save method should catch and wrap DaoException in ServiceException")
    void shouldCatchAndWrapSave() throws DaoException {
        PersistenceEquationDao mockDao = mock(PersistenceEquationDao.class);
        Equation equation = Equation.builder().expression("2*x+5=17").build();
        doThrow(DaoException.class).when(mockDao).save(equation);
        EquationService equationService = new EquationService(mockDao);
        assertThrows(ServiceException.class, () -> equationService.save(equation));
    }

    @Test
    @DisplayName("Test Save method")
    void testSave() throws DaoException, ServiceException {
        Equation equation = Equation.builder().expression("2*x+5=17").build();
        PersistenceEquationDao persistenceEquationDao = mock(PersistenceEquationDao.class);
        when(persistenceEquationDao.save(equation)).thenReturn(equation);

        EquationService equationService = new EquationService(persistenceEquationDao);
        Equation result = equationService.save(equation);
        assertEquals(result, equation);
        verify(persistenceEquationDao).save(equation);
    }

    @Test
    @DisplayName("Find equation by ID")
    void testFindById() throws ServiceException, DaoException {
        Long id = 1L;
        Equation equation = Equation.builder().expression("2*x+5=17").build();
        PersistenceEquationDao persistenceEquationDao = mock(PersistenceEquationDao.class);
        when(persistenceEquationDao.findById(id)).thenReturn(equation);

        EquationService equationService = new EquationService(persistenceEquationDao);
        Equation result = equationService.findById(id);
        assertEquals(equation, result);
        verify(persistenceEquationDao).findById(id);
    }

    @Test
    @DisplayName("Find equation by ID - not found")
    void testFindByIdNotFound() throws DaoException {
        Long id = 1L;
        PersistenceEquationDao persistenceEquationDao = mock(PersistenceEquationDao.class);
        when(persistenceEquationDao.findById(id)).thenReturn(null);

        EquationService equationService = new EquationService(persistenceEquationDao);
        assertThrows(ServiceException.class, () -> equationService.findById(id));
        verify(persistenceEquationDao).findById(id);
    }

    @Test
    @DisplayName("Find all equations - DaoException")
    void testFindAllDaoException() throws DaoException {
        PersistenceEquationDao persistenceEquationDao = mock(PersistenceEquationDao.class);
        when(persistenceEquationDao.find(FIND_ALL)).thenThrow(new DaoException(ERROR));

        EquationService equationService = new EquationService(persistenceEquationDao);
        ServiceException exception = assertThrows(
                ServiceException.class, () -> equationService.find(FIND_ALL));
        assertEquals(ERROR, exception.getMessage());
        verify(persistenceEquationDao).find(FIND_ALL);
    }

    @Test
    @DisplayName("Find equations by root - DaoException")
    void testFindEquationsByRootDaoException() throws DaoException {
        String root = "2.0";
        PersistenceEquationDao persistenceEquationDao = mock(PersistenceEquationDao.class);
        when(persistenceEquationDao.findEquations(FIND_BY_ROOT, "root", root)).thenThrow(new DaoException(ERROR));

        EquationService equationService = new EquationService(persistenceEquationDao);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> equationService.find(FIND_BY_ROOT, "root", root));
        assertEquals(ERROR, exception.getMessage());
        verify(persistenceEquationDao).findEquations(FIND_BY_ROOT, "root", root);
    }

    @Test
    @DisplayName("Delete equation")
    void testDelete() throws ServiceException, DaoException {
        Long id = 1L;
        PersistenceEquationDao persistenceEquationDao = mock(PersistenceEquationDao.class);
        when(persistenceEquationDao.delete(id)).thenReturn(true);

        EquationService equationService = new EquationService(persistenceEquationDao);
        equationService.delete(id);
        verify(persistenceEquationDao).delete(id);
    }

    @Test
    @DisplayName("Delete equation - not found")
    void testDeleteNotFound() throws DaoException {
        Long id = 1L;
        PersistenceEquationDao persistenceEquationDao = mock(PersistenceEquationDao.class);
        when(persistenceEquationDao.delete(id)).thenReturn(false);

        EquationService equationService = new EquationService(persistenceEquationDao);
        assertThrows(ServiceException.class, () -> equationService.delete(id));
        verify(persistenceEquationDao).delete(id);
    }

    @Test
    @DisplayName("Delete equation - DaoException")
    void testDeleteDaoException() throws DaoException {
        Long id = 1L;
        PersistenceEquationDao persistenceEquationDao = mock(PersistenceEquationDao.class);
        when(persistenceEquationDao.delete(id)).thenThrow(new DaoException(ERROR));

        EquationService equationService = new EquationService(persistenceEquationDao);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> equationService.delete(id));
        assertEquals(ERROR, exception.getMessage());
        verify(persistenceEquationDao).delete(id);
    }

    @Test
    @DisplayName("Update equation")
    void testUpdate() throws ServiceException, DaoException {
        Equation equation = Equation.builder().expression("2*x+5=17").build();
        Equation updatedEquation = Equation.builder().expression("2*x+5=18").build();
        PersistenceEquationDao persistenceEquationDao = mock(PersistenceEquationDao.class);
        when(persistenceEquationDao.save(equation)).thenReturn(updatedEquation);

        EquationService equationService = new EquationService(persistenceEquationDao);
        Equation result = equationService.update(equation);

        assertEquals(updatedEquation, result);
        verify(persistenceEquationDao).save(equation);
    }

    @Test
    @DisplayName("Update equation - DaoException")
    void testUpdateDaoException() throws DaoException {
        Equation equation = Equation.builder().expression("x^2+1=10").build();
        when(persistenceEquationDao.save(equation)).thenThrow(new DaoException(ERROR));

        EquationService equationService = new EquationService(persistenceEquationDao);

        ServiceException exception = assertThrows(ServiceException.class, () -> equationService.update(equation));
        assertEquals(ERROR, exception.getMessage());
        verify(persistenceEquationDao).save(equation);
    }
}
