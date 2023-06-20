package com.credit.card.creditcardservice.controller.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Global exception handler class that handles exceptions thrown by controllers.
 * It is annotated with {@code @ControllerAdvice} to indicate that it provides
 * global exception handling for controllers. This class extends the
 * {@code ResponseEntityExceptionHandler} class, which is a convenient base
 * class for building {@code ResponseEntity} instances with common
 * error-handling functionality.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Exception handler method for handling {@code GenericException}. This method
	 * is triggered when a {@code GenericException} is thrown by a controller. It
	 * returns a {@code ResponseEntity} with an error message and the appropriate
	 * HTTP status code.
	 *
	 * @param ex      The thrown {@code GenericException}.
	 * @param request The current web request.
	 * @return A {@code ResponseEntity} with an error message and the appropriate
	 *         HTTP status code.
	 */
	@ExceptionHandler(GenericException.class)
	public ResponseEntity<Object> handleGenericException(GenericException ex, WebRequest request) {
		log.error(ex.getMessage());
		return new ResponseEntity<>("Exception in the backend", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Exception handler method for handling {@code ValidationException}. This
	 * method is triggered when a {@code ValidationException} is thrown by a
	 * controller. It returns a {@code ResponseEntity} with an error message and the
	 * appropriate HTTP status code.
	 *
	 * @param ex      The thrown {@code ValidationException}.
	 * @param request The current web request.
	 * @return A {@code ResponseEntity} with an error message and the appropriate
	 *         HTTP status code.
	 */
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<Object> handleValidationException(ValidationException ex, WebRequest request) {
		log.error(ex.getMessage());
		return new ResponseEntity<>("Mandatory Fields not present", HttpStatus.BAD_REQUEST);
	}

}
