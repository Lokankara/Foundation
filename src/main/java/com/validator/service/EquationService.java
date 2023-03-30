package com.validator.service;

import com.validator.dao.EquationDao;
import com.validator.entity.Equation;
import com.validator.exception.DaoException;
import com.validator.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.Integer.parseInt;

@Slf4j
@Service
@AllArgsConstructor
@CacheConfig(cacheNames = "equations")
public class EquationService implements BaseService<Equation> {

    private final EquationDao equationDao;

    @Override
    @Transactional
    @CacheEvict(value = "equations", allEntries = true)
    public Equation save(final Equation equation)
            throws ServiceException {
        try {
            return equationDao.save(equation);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "equations", allEntries = true)
    public void delete(
            final Long id)
            throws ServiceException {
        try {
            if (!equationDao.delete(id)) {
                throw new ServiceException(String.valueOf(id));
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "equations", allEntries = true)
    public Equation update(
            final Equation equation)
            throws ServiceException {
        try {
            return equationDao.save(equation);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "equations", key = "#id")
    public Equation findById(final Long id)
            throws ServiceException {
        try {
            Equation equation = equationDao.findById(id);
            if (equation != null) {
                return equation;
            }
            throw new ServiceException(id.toString());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Cacheable("equations")
    @Transactional(readOnly = true)
    public List<Equation> find(String sql)
            throws ServiceException {
        try {
            return equationDao.find(sql);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Cacheable("equations")
    @Transactional(readOnly = true)
    public List<Equation> find(
            final String sql,
            final String name,
            final String root)
            throws ServiceException {
        try {
            return equationDao.findEquations(sql, name, root);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Cacheable("equations")
    @Transactional(readOnly = true)
    public Equation findByExpression(
            final String sql,
            final String expression)
            throws ServiceException {
        try {
            return equationDao.findByExpression(sql, expression);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    @Cacheable("equations")
    @Transactional(readOnly = true)
    public List<Equation> findByCounter(
            final String sql,
            final Long number)
            throws ServiceException {
        try {
            return equationDao.findByNumberRoots(sql, number);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
