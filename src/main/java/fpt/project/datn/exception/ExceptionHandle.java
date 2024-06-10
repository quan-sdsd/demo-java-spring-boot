package fpt.project.datn.exception;

import fpt.project.datn.exception.custom.AbsApplicationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler({
            AbsApplicationException.class
    })
    public ResponseEntity<?> response(AbsApplicationException ex) {
        return ex.getExceptionResponse();
    }
}
