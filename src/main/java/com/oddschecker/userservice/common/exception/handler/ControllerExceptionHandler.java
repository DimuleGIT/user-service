package com.oddschecker.userservice.common.exception.handler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.oddschecker.userservice.common.exception.dto.OddsApiError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        OddsApiError error = new OddsApiError();
        for (ConstraintViolation<?> constraintViolation : ex.getConstraintViolations()) {
            error.addError(ex.getClass().getSimpleName(),
                           "Error",
                           constraintViolation.getPropertyPath().toString());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON_UTF8).body(error);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        BindingResult result = ex.getBindingResult();
        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(getOddsApiError(result, request));
    }

    private OddsApiError getOddsApiError(BindingResult result, WebRequest request) {
        OddsApiError damanError = new OddsApiError();
        result.getAllErrors().forEach(error -> addError(damanError, error));
        return damanError;
    }

    private void addError(final OddsApiError damanError, final ObjectError error) {
        String field = null;
        if (error instanceof FieldError) {
            field = ((FieldError) error).getField();
        }
        damanError.addError(error.getCode(), error.getDefaultMessage(), field);
    }

}
