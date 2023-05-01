package com.zedapps.common.util;

import com.zedapps.common.dto.ErrorDto;
import com.zedapps.common.dto.ErrorResponse;
import com.zedapps.common.dto.LoginResponseDto;
import com.zedapps.common.dto.SupportingDocumentDto;
import org.apache.commons.io.FileUtils;
import org.springframework.validation.Errors;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Shamah M Zoha
 * @since 21-Feb-23
 */

public class ResponseUtils {

    public static final String ERROR_RESPONSE_TYPE_VALIDATION = "Validation Error";
    public static final String VALIDATION_TYPE_GLOBAL = "global";
    public static final String VALIDATION_TYPE_FIELD = "field";

    public static ErrorResponse getErrorResponse(Errors errors) {
        ErrorResponse errorResponse = new ErrorResponse(new Date(), HttpURLConnection.HTTP_BAD_REQUEST,
                ERROR_RESPONSE_TYPE_VALIDATION, null);

        List<ErrorDto> errorList = new ArrayList<>();

        errors.getGlobalErrors().stream()
                .map(error -> new ErrorDto(VALIDATION_TYPE_GLOBAL, null, error.getDefaultMessage()))
                .forEach(errorList::add);

        errors.getFieldErrors().stream()
                .map(error -> new ErrorDto(VALIDATION_TYPE_FIELD, error.getField(), error.getDefaultMessage()))
                .forEach(errorList::add);

        errorResponse.setPayload(errorList);

        return errorResponse;
    }

    public static LoginResponseDto getLoginResponse(String username, String email, String firstName, String lastName,
                                                    String status, Set<String> roles) {

        return new LoginResponseDto(username, email, firstName, lastName, status, roles);
    }

    public static SupportingDocumentDto getSupportingDocumentResponse(long docId, String name, String type, long size, Date uploadDate) {
        String fileSize = FileUtils.byteCountToDisplaySize(size);
        return new SupportingDocumentDto(docId, name, type, fileSize, uploadDate);
    }
}
