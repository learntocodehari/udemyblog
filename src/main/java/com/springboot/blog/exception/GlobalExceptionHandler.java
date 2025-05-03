package com.springboot.blog.exception;

import com.springboot.blog.payload.ErrorDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
// we use this annotation to handle exceptions globally, this class is configured as
//spring bean or auto-detected while component scan because of @ControllerAdvice annotation
//because ControllerAdvice internally uses @Component.
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // handle specific exceptions

    @ExceptionHandler(ResourceNotFoundException.class)
    // we use ExceptionHandler annotation to handle specific exception
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                        WebRequest webRequest) {
        // we are going to send some details from the web request to the client
        // that's why we are using WebRequest here
        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                exception.getMessage(),
                webRequest.getDescription(false));
        // we are not going to send whole description of the web request to the client , only send the url , that's why false is passed

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(BlogApiException.class)
    // we use ExceptionHandler annotation to handle specific exception
    public ResponseEntity<ErrorDetails> handleBlogAPIException(BlogApiException exception,
                                                               WebRequest webRequest) {
        // we are going to send some details from the web request to the client
        // that's why we are using WebRequest here
        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                exception.getMessage(),
                webRequest.getDescription(false));
        // we are not going to send whole description of the web request to the client , only send the url , that's why false is passed

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);

    }

    // handle global exceptions


    @ExceptionHandler(Exception.class)
    // we use ExceptionHandler annotation to handle specific exception
    public ResponseEntity<ErrorDetails> handleBlogAPIException(Exception exception,
                                                               WebRequest webRequest) {
        // we are going to send some details from the web request to the client
        // that's why we are using WebRequest here
        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                exception.getMessage(),
                webRequest.getDescription(false));
        // we are not going to send whole description of the web request to the client , only send the url , that's why false is passed

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        // creating a map which will have all the errors
        Map<String, String> errors = new HashMap<>();
        // get all the errors from exception object, and we will keep all those errors in map object
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // another way to do it
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    // we use ExceptionHandler annotation to handle specific exception
//    public ResponseEntity<Object> handleBlogAPIException(MethodArgumentNotValidException exception,
//                                                         WebRequest webRequest) {
//        // creating a map which will have all the errors
//        Map<String, String> errors = new HashMap<>();
//        // get all the errors from exception object, and we will keep all those errors in map object
//        exception.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String message = error.getDefaultMessage();
//            errors.put(fieldName, message);
//        });
//
//        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//
//    }
}
