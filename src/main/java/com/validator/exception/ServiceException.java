package com.validator.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServiceException extends Exception {
    public ServiceException(final Exception e) {
        super(e.getMessage());
        log.error(e.getMessage());
    }

    public ServiceException(final String message) {
        super(message);
        log.error(message);
    }

    public ServiceException(final String message, final DaoException e) {
        super(message, e);
        log.error(message);
    }
}
