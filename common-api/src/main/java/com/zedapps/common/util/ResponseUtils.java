package com.zedapps.common.util;

import com.zedapps.common.dto.ErrorDto;
import com.zedapps.common.dto.ErrorResponse;
import org.springframework.validation.Errors;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Shamah M Zoha
 * @since 21-Feb-23
 */

public class ResponseUtils {

    public static ErrorResponse getErrorResponse(Errors errors) {
        ErrorResponse errorResponse = new ErrorResponse(new Date(), HttpURLConnection.HTTP_BAD_REQUEST,
                "Validation Error", null);

        List<ErrorDto> errorList = new ArrayList<>();

        errors.getGlobalErrors().stream()
                .map(error -> new ErrorDto("global", null, error.getDefaultMessage()))
                .forEach(errorList::add);

        errors.getFieldErrors().stream()
                .map(error -> new ErrorDto("field", error.getField(), error.getDefaultMessage()))
                .forEach(errorList::add);

        errorResponse.setPayload(errorList);

        return errorResponse;
    }
}
