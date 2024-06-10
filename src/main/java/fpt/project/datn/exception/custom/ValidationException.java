package fpt.project.datn.exception.custom;

import lombok.Getter;

import java.util.Map;
@Getter
public class ValidationException extends AbsApplicationException{
    private static final int STATUS = 442;
    public ValidationException(Map<String, String> validMessages) {
        super(STATUS, validMessages);
    }
}
