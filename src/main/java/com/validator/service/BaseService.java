package com.validator.service;

import com.validator.entity.BaseEntity;
import com.validator.exception.ServiceException;

import java.util.List;

public interface BaseService<T extends BaseEntity> {
    T save(T t) throws ServiceException;

    void delete(Long id) throws ServiceException;

    T update(T t) throws ServiceException;

    T findById(Long id) throws ServiceException;

    List<T> find(String sql) throws ServiceException;

    List<T> find(String sql, String name, String root) throws ServiceException;

    T findByExpression(String sql, String expression) throws ServiceException;

    List<T> findByCounter(String sql, Long number) throws ServiceException;
}
