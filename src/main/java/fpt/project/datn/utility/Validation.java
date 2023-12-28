package fpt.project.datn.utility;

import fpt.project.datn.exception.custom.ValidationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Validation {
    private Validation() {
    }

    public static void valid(BindingResult rs) {
        if(rs.hasErrors()) {
            List<FieldError> errors = rs.getFieldErrors();
            Map<String, String> validMessages = new HashMap<>();
            for(FieldError err : errors) {
                validMessages.put(err.getField(), err.getDefaultMessage());
            }
            throw new ValidationException(validMessages);
        }
    }

    public static final String USER_USERNAME_VALIDATION = "username phải chứa từ 8 đến 50 ký tự";
    public static final String USER_PASSWORD_VALIDATION = "password phải chứa từ 8 đến 50 ký tự";
    public static final String USER_EMAIL_VALIDATION = "email không hợp lệ";
    public static final String USER_PHONENUMBER_VALIDATION = "số điện thoại không hợp lệ";
}
