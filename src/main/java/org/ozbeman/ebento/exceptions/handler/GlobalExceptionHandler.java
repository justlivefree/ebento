package org.ozbeman.ebento.exceptions.handler;

import org.ozbeman.ebento.exceptions.AccessDenied;
import org.ozbeman.ebento.exceptions.InvalidOtpCodeException;
import org.ozbeman.ebento.exceptions.InvalidRequestException;
import org.ozbeman.ebento.exceptions.ResourceNotFound;
import org.ozbeman.ebento.utils.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse resourceNotFoundHandler(ResourceNotFound ex) {
        return new ApiErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse invalidRequestHandler(InvalidRequestException ex) {
        return new ApiErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(InvalidOtpCodeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse invalidOtpCodeHandler(InvalidOtpCodeException ex) {
        return new ApiErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(AccessDenied.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrorResponse invalidOtpCodeHandler(AccessDenied ex) {
        return new ApiErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse methodArgumentNotValidHandler(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String message = "Invalid field: %s".formatted(fieldError.getDefaultMessage());
        return new ApiErrorResponse(message);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse generalHandler(Exception ex) {
        ex.printStackTrace();
        return new ApiErrorResponse(ex.getMessage());
    }

}
