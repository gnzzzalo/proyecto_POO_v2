package upm.services.exceptions;

public class NotFoundException extends RuntimeException {
    private static final String DESCRIPTION = "Not Found Exception, the object does not exist";

    public NotFoundException(String detail) {
        super(DESCRIPTION + ": " + detail);
    }
}
