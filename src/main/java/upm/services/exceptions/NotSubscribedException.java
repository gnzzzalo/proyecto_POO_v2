package upm.services.exceptions;

public class NotSubscribedException extends RuntimeException {
    private static final String DESCRIPTION = "Not Subscribed Exception, you are not subscribed";

    public NotSubscribedException(String detail) {
        super(DESCRIPTION + ": " + detail);
    }
}
