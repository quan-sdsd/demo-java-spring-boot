package fpt.project.datn.object.dto.req;

import fpt.project.datn.utility.Validation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserReq {
    @Size(min = 8, max = 50, message = Validation.USER_USERNAME_VALIDATION)
    private String username;
    @Size(min = 8, max = 50, message = Validation.USER_PASSWORD_VALIDATION)
    private String password;
    @Email(message = Validation.USER_EMAIL_VALIDATION)
    private String email;
    @Pattern(regexp = "0\\d{9,10}", message = Validation.USER_PHONENUMBER_VALIDATION)
    private String phoneNumber;
}
