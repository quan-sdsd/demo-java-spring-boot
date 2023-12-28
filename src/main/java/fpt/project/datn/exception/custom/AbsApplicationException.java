package fpt.project.datn.exception.custom;

import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public abstract class AbsApplicationException extends RuntimeException {
    protected int status;
    protected Object errorMessage;

    protected AbsApplicationException(int status, Object message) {
        this.status = status;
        this.errorMessage = message;
    }
    public ResponseEntity<?> getExceptionResponse(){
        return ResponseEntity.status(this.status).body(this.getErrorMessage());
    }
}
