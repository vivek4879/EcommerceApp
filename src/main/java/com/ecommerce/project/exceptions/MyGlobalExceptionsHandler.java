package com.ecommerce.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice // specialized version of controller advice geared towards REST APIs. so if making RESTAPIs then
// you should use this. The moment we add this annotation to any class, it will intercept any exception thrown by any controller in the app.
//This class will have a custom error response whenever an exception occurs
public class MyGlobalExceptionsHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class) // can use to define methods in our handler to handle a specific type of exception
    //we define the return type as a response entity as if we return just a map then it won't register the HTTP response and though it will give a more user-friendly
    // response, it will still give a 200 OK response. but we need 400 Bad request here. so making it a response entity will return a 400 bad request.
    public ResponseEntity<Map<String, String>> myMethodArgumentNotValidException(MethodArgumentNotValidException e){
        //we want a response which is a bit more user-friendly so we create a hashMap.
        Map<String, String> response = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(err ->{
            String fieldName = ((FieldError)err).getField();
            String message = ((FieldError) err).getDefaultMessage(); // we can also create a custom error message here
            response.put(fieldName, message);
        });
        return new ResponseEntity<Map<String,String>>(response, HttpStatus.BAD_REQUEST);
    }

    //this handler is intercepting all the ResourceNotFoundException in all the controllers
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> myResourceNotFoundException(ResourceNotFoundException e){
        String response = e.getMessage();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
