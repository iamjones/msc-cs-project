package project.exception;

/**
 * Exception that is thrown whenever there is a problem during
 * processing html and removing the web page noise.
 */
public class WebPagePartitionException extends Exception {

    public WebPagePartitionException(String msg) {
        super(msg);
    }
}
