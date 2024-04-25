package com.yevhenii.ticketingService.exception.util;

import com.yevhenii.ticketingService.exception.error.ApiError;
import lombok.experimental.UtilityClass;
import org.springframework.http.ResponseEntity;

@UtilityClass
public class ExceptionHandlerUtils {
    public static ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
