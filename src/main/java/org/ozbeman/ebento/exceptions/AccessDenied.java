package org.ozbeman.ebento.exceptions;

public class AccessDenied extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Access denied";

    public AccessDenied(String message) {
        super(message);
    }

    public AccessDenied() {
        super(DEFAULT_MESSAGE);
    }
}
