package com.validator.service;

import com.validator.dao.RootDao;
import com.validator.entity.Root;
import com.validator.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class RootService {
    private final RootDao jdbcRootDao;

    @Transactional
    public void addRoot(final Root root) throws ServiceException {
        try {
            log.info(root.toString());
            jdbcRootDao.saveRoot(root);
        } catch (Exception e) {
            log.info(root.toString());
            throw new ServiceException(e);
        }
    }

    @Transactional(readOnly = true)
    public List<Root> getAll() throws ServiceException {
        try {
            return jdbcRootDao.findAll();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
