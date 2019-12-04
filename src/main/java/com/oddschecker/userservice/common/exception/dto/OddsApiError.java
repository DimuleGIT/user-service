package com.oddschecker.userservice.common.exception.dto;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class OddsApiError {
    private List<OddError> errors = new ArrayList<>();

    public OddsApiError addError(Throwable t, String message) {
        return addError(toError(t), message);
    }

    public OddsApiError addError(Throwable t, String message, String field) {
        return addError(toError(t), message, field);
    }

    public OddsApiError addError(Throwable t, String message, String field, Object info) {
        return addError(toError(t), message, field, info);
    }

    public OddsApiError addError(String error, String message) {
        return addError(error, message, null);
    }

    public OddsApiError addError(String error, String message, String field) {
        return addError(error, message, field, null);
    }

    public OddsApiError addError(String error, String message, String field, Object info) {
        errors.add(new OddError(error, message, field, info));
        return this;
    }

    private String toError(Throwable t) {
        return t.getClass().getSimpleName();
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private class OddError {
        @NonNull
        private final String error;
        private final String message;
        private final String field;
        private final Object info;
    }
}
