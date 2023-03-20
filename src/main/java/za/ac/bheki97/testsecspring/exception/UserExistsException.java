package za.ac.bheki97.testsecspring.exception;

public class UserExistsException extends Exception{

    public UserExistsException(String message) {
        super(message);
    }
}
