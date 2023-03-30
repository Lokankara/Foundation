package com.validator.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DaoException extends Exception {
    public DaoException(final String message, final Exception e) {
        super(message, e);
        log.error(message);
    }

    public DaoException(final String message) {
        super(message);
        log.error(message);
    }

    public DaoException(final Exception e) {
        super(e);
        log.error(e.getMessage());
    }
}
