package com.audiosphere.api.service;

public class DiscoveryServiceException extends RuntimeException {

    public DiscoveryServiceException(
            String message,
            Throwable cause) {

        super(message, cause);
    }
}