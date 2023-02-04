package com.zedapps.common.utils;

import org.json.JSONObject;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Shamah M Zoha
 * @since 04-Feb-23
 */
public class ResponseUtils {

    public static JSONObject generateSuccessMage(String message, Object payload) {
        JSONObject successResponse = new JSONObject();
        successResponse.put("message", message);

        if (Objects.nonNull(payload)) {
            successResponse.put("payload", payload);
        }

        return successResponse;
    }

    public static JSONObject generateErrorResponse(Errors errors) {
        JSONObject errorResponse = new JSONObject();

        if (!errors.getGlobalErrors().isEmpty()) {
            List<String> globalErrors = errors.getGlobalErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());

            errorResponse.put("global", globalErrors);
        }

        if (!errors.getFieldErrors().isEmpty()) {
            Map<String, List<String>> fieldErrorMap = new LinkedHashMap<>();

            errors.getFieldErrors().forEach(error -> {
                if (fieldErrorMap.containsKey(error.getField())) {
                    fieldErrorMap.get(error.getField()).add(error.getDefaultMessage());
                } else {
                    fieldErrorMap.put(error.getField(), new ArrayList<>(Collections.singletonList(error.getDefaultMessage())));
                }
            });

            errorResponse.put("field", fieldErrorMap);
        }

        return errorResponse;
    }
}
