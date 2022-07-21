package ua.yahniukov.notes.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.yahniukov.notes.exceptions.*;

import java.util.Date;

@ControllerAdvice
public class CustomHandlerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleUserNotFoundException() {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.NOT_FOUND.value(), "User not found", new Date().getTime()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> handleUserAlreadyExistsException() {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.BAD_REQUEST.value(), "User already exists", new Date().getTime()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AdminIsNotBlockException.class)
    public ResponseEntity<ErrorMessage> handleAdminIsNotBlockException() {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.BAD_REQUEST.value(), "Admin can't be blocked", new Date().getTime()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SamePasswordException.class)
    public ResponseEntity<ErrorMessage> handleSamePasswordException() {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.BAD_REQUEST.value(), "Old password can't be equal to new password", new Date().getTime()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ErrorMessage> handleIncorrectPasswordException() {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.BAD_REQUEST.value(), "Incorrect password", new Date().getTime()), HttpStatus.BAD_REQUEST);
    }

    private record ErrorMessage(
            Integer status,
            String message,
            Long timestamp
    ) {
    }
}