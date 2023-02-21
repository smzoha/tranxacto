package com.zedapps.txaccount.controller.advice;

import com.zedapps.common.dto.ErrorResponse;
import com.zedapps.common.util.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Shamah M Zoha
 * @since 21-Feb-23
 */

@ControllerAdvice
public class AccountControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationError(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();

        return ResponseEntity.badRequest().body(ResponseUtils.getErrorResponse(bindingResult));
    }
}
