package fpt.project.datn.exception.custom;

public class EmailConfirmationException extends AbsApplicationException {
    private static final int HTTP_STATUS = 401;
    public EmailConfirmationException() {
        super(HTTP_STATUS, "invalid Confirmation Code");
    }
}
