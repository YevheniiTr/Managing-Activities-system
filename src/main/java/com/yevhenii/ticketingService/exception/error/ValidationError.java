package com.yevhenii.ticketingService.exception.error;

import lombok.Data;
import lombok.Value;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class ValidationError extends ApiError {

    private List<Violation> violations;

    public ValidationError(HttpStatus status, String message, List<Violation> violations) {
        super(status, message);
        this.violations = violations;
    }

    @Value
    public static class Violation {
        String fieldName;
        String message;
    }
}
