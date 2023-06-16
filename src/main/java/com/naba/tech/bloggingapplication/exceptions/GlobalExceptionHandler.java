package com.naba.tech.bloggingapplication.exceptions;


import com.naba.tech.bloggingapplication.payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundException(ResourceNotFoundException ex){
        return new ResponseEntity<ApiResponse>(new ApiResponse(ex.getMessage(),false),HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){

        Map<String,String> errorDetails=new HashMap<>(  );
        ex.getBindingResult().getAllErrors().forEach((error)->{
         String fieldName=((FieldError) error).getField();
         String message=error.getDefaultMessage();
         errorDetails.put(fieldName,message);
        });
        return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
    }
}
