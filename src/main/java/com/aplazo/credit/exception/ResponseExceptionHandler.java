package com.aplazo.credit.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
@RestController
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> handleAllException(Exception ex, WebRequest request){
		ExceptionResponse er = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));		
		return new ResponseEntity<>(er, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ModelNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleModelNotFoundException(ModelNotFoundException ex, WebRequest request) {
		ExceptionResponse er = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(er, HttpStatus.NOT_FOUND);
	}	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String message = ex.getBindingResult()
				.getAllErrors().stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage)
				.collect(Collectors.joining(", "));
		
		ExceptionResponse er = new ExceptionResponse(LocalDateTime.now(), message, request.getDescription(false));
		return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
	}

	
}
