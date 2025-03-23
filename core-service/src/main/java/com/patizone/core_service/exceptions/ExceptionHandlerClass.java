package com.patizone.core_service.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerClass {

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBusinessException(BusinessException ex) {
        log.error(ex.getMessage());
        return new HashMap<>() {{
            put("errorMessage", ex.getMessage());
        }};
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFoundException(NotFoundException ex) {
        log.error(ex.getMessage());
        return new HashMap<>() {{
            put("errorMessage", ex.getMessage());
        }};
    }


    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public  Map<String, String> handleSqlException(SQLException ex) {
        log.error(ex.getMessage());
        return new HashMap<>() {{
            put("errorMessage", ex.getMessage());
        }};
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public  Map<String, String> handleUnauthorizedException(UnauthorizedException ex) {
        log.error(ex.getMessage());
        return new HashMap<>() {{
            put("errorMessage", ex.getMessage());
        }};
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public  Map<String, String> handleGeneralException(Exception ex) {
        log.error(ex.getMessage());
        return new HashMap<>() {{
            put("errorMessage", ex.getMessage());
        }};
    }




}
