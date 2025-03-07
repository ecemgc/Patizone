package com.patizone.core_service.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerClass {

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBusinessException(BusinessException businessException) {
        return new HashMap<>() {{
            put("errorMessage", businessException.getMessage());
        }};
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public  Map<String, String> handleDataIntegrityViolationException(SQLException ex) {
        return new HashMap<>() {{
            put("errorMessage", ex.getMessage());
        }};
    }

    @ExceptionHandler(Exception.class)
    public  Map<String, String> handleGeneralException(Exception ex) {
        return new HashMap<>() {{
            put("errorMessage", ex.getMessage());
        }};
    }

}
