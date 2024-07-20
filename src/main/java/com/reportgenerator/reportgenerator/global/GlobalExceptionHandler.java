package com.reportgenerator.reportgenerator.global;

import com.reportgenerator.reportgenerator.error.Error;
import com.reportgenerator.reportgenerator.exception.GenericException;
import com.reportgenerator.reportgenerator.exception.ParsingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ParsingException.class)
    public ResponseEntity<Error> handleParsingException(ParsingException ex) {
        Error errorResponse = new Error(ex.getError().getErrorCode(), ex.getMessage(), ex.getError().getMessage());
        logger.error("Exception occurred while parsing : {}", errorResponse);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<Error> handleGenericErrors(GenericException ex) {
        Error errorResponse = new Error(ex.getError().getErrorCode(), ex.getMessage(), ex.getError().getMessage());
        logger.error("Generic Exception occurred : {}", errorResponse);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
