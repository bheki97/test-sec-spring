package za.ac.bheki97.testsecspring.controller;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import za.ac.bheki97.testsecspring.exception.EventException;
import za.ac.bheki97.testsecspring.exception.UserExistsException;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({UserExistsException.class, EventException.class})
    private String handleUserExistsException(Exception exc){

        return exc.getMessage();
    }
}
