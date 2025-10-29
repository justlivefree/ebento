package org.ozbeman.ebento.exceptions.handler;

import lombok.extern.slf4j.Slf4j;
import org.ozbeman.ebento.exceptions.AccessDenied;
import org.ozbeman.ebento.exceptions.InvalidRequestException;
import org.ozbeman.ebento.exceptions.ResourceNotFound;
import org.ozbeman.ebento.utils.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse resourceNotFoundHandler(ResourceNotFound ex) {
        log.error(ex.getMessage());
        return new ApiErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse invalidRequestHandler(InvalidRequestException ex) {
        log.error(ex.getMessage());
        return new ApiErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(AccessDenied.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrorResponse customAccessDeniedHandler(AccessDenied ex) {
        log.error(ex.getMessage());
        return new ApiErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrorResponse authorizationDeniedExceptionHandler(AuthorizationDeniedException ex) {
        log.error(ex.getMessage());
        return new ApiErrorResponse("Access Denied");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse methodArgumentNotValidHandler(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage());
        return new ApiErrorResponse("Invalid field");
    }

    @ExceptionHandler(MultipartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse multipartFileExceptionHandler(MultipartException ex) {
        log.error(ex.getMessage());
        return new ApiErrorResponse("Invalid File");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse generalHandler(Exception ex) {
        log.error(String.valueOf(ex.fillInStackTrace()));
        return new ApiErrorResponse("Server Error");
    }

}
