package upm.services.exceptions;

public class DuplicateException extends RuntimeException {
    private static final String DESCRIPTION = "Duplicate Exception, the attribute value should be unique";

    public DuplicateException(String detail) {
        super(DESCRIPTION + ": " + detail);
    }
}
