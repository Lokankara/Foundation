package com.validator.dao;

import com.validator.entity.Root;
import com.validator.exception.DaoException;

import java.util.List;

public interface RootDao {
    void saveRoot(Root root);
    List<Root> findAll() throws DaoException;
}
