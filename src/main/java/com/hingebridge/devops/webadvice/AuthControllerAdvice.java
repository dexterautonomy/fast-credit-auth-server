package com.hingebridge.devops.webadvice;

import static com.hingebridge.devops.constants.ResponseConstants.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hingebridge.devops.dtos.ResponseDTO;
import com.hingebridge.devops.exceptions.AuthException;

@RestControllerAdvice
public class AuthControllerAdvice {
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseDTO<Map<String, String>> handleMethodArgumentNotValidExceptions(MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    
	    return new ResponseDTO<>(INVALIDFIELDS.getCode(), INVALIDFIELDS.getMessage(), errors);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(AuthException.class)
	public ResponseDTO<String> handleGenericAppException(AuthException authException){
		return new ResponseDTO<>(FAILURE.getCode(), FAILURE.getMessage(), authException.getMessage());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseDTO<Map<String, String>> handleUsernameNotFoundExceptions(UsernameNotFoundException ex) {
	    Map<String, String> errors = new HashMap<>();
	    errors.put("message", ex.getMessage());
	    
	    return new ResponseDTO<>(INVALIDFIELDS.getCode(), INVALIDFIELDS.getMessage(), errors);
	}
}