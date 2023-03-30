package com.validator.dao;

import com.validator.entity.Equation;
import com.validator.exception.DaoException;

import java.util.List;

public interface EquationDao {

    Equation save(Equation equation) throws DaoException;

    boolean delete(Long id) throws DaoException;

    List<Equation> find(String query) throws DaoException;

    Equation findById(Long id) throws DaoException;

    List<Equation> findEquations(String query, String name, String root) throws DaoException;

    List<Equation> findByRoots(String query, List<Double> roots) throws DaoException;

    List<Equation> findByNumberRoots(String query, Long number) throws DaoException;

    Equation findByExpression(String query, String expression) throws DaoException;
}
