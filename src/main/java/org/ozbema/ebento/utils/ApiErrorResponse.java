package org.ozbema.ebento.utils;

import lombok.Getter;

@Getter
public class ApiErrorResponse {
    private final String message;

    public ApiErrorResponse(String message) {
        this.message = message;
    }

}
