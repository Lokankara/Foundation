package com.validator.dao;

import com.validator.entity.Equation;
import com.validator.exception.DaoException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;

@Slf4j
@Repository
@AllArgsConstructor
public class PersistenceEquationDao implements EquationDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Equation save(
            final Equation equation)
            throws DaoException {
        try {
            return entityManager.merge(equation);
        } catch (Exception e) {
            log.error(equation.toString());
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(
            final Long id)
            throws DaoException {
        try {
            entityManager.remove(findById(id));
            return true;
        } catch (Exception e) {
            throw new DaoException(id.toString(), e);
        }
    }

    @Override
    public Equation findById(
            final Long id)
            throws DaoException {
        try {
            Equation equation = entityManager
                    .find(Equation.class, id);
            log.info("Found " + equation);
            if (equation != null) {
                return equation;
            }
            throw new DaoException(id.toString());
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Equation findByExpression(
            final String query,
            final String expression) throws DaoException {
        try {
            log.info("Found " + expression);
            log.info("query " + query);

            return entityManager.createQuery(query, Equation.class)
                    .setParameter("expression", expression)
                    .getSingleResult();
        } catch (Exception e) {
            throw new DaoException("Error finding equation by expression: " + expression, e);
        }
    }

    @Override
    public List<Equation> find(
            final String query)
            throws DaoException {
        try {
            return entityManager.createQuery(
                            query,
                            Equation.class
                    )
                    .getResultList();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Equation> findEquations(
            final String query,
            final String name,
            final String params)
            throws DaoException {
        try {
            return entityManager.createQuery(
                            query,
                            Equation.class
                    )
                    .setParameter(name, params)
                    .getResultList();
        } catch (Exception e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    @Override
    public List<Equation> findByNumberRoots(final String query, final Long number) throws DaoException {
        try {
            return entityManager.createQuery(
                            query,
                            Equation.class
                    )
                    .setParameter("count", number)
                    .getResultList();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Equation> findByRoots(final String query, final List<Double> roots) throws DaoException {
        if (roots == null || roots.isEmpty()) {
            return Collections.emptyList();
        }
        try {
            return entityManager.createQuery(
                            query,
                            Equation.class
                    )
                    .setParameter("roots", roots)
                    .setParameter("count", roots.size())
                    .getResultList();
        } catch (Exception e) {
            throw new DaoException(e.getMessage(), e);
        }
    }
}
