package ru.gsa.biointerface.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Gavrilov Stepan (itgavrilov@gmail.com) on 10.09.2021.
 */
public class ServiceException extends Exception {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceException.class);

    public ServiceException(String message) {
        super(message);
        LOGGER.error("{}:", message, this);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
        cause.printStackTrace();
        LOGGER.error("{}:", message, this);
    }
}