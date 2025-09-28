package upm.services.exceptions;

public class OverlapException extends RuntimeException {
    private static final String DESCRIPTION = "Overlap Exception, it is overlapping";

    public OverlapException(String detail) {
        super(DESCRIPTION + ": " + detail);
    }
}
