package fpt.project.datn.exception.custom;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;

public class AccountAuthenticationException extends AbsApplicationException {
    private static final int STATUS = 403;
    public AccountAuthenticationException(String msg) {
        super(STATUS, msg);
    }
}
