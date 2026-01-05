package software.blacknode.backend.api.controller.advice;

import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import software.blacknode.backend.api.controller.response.impl.ErrorResponse;
import software.blacknode.backend.domain.auth.exception.AuthenticationException;
import software.blacknode.backend.domain.exception.BlacknodeException;

@RestControllerAdvice
public class BaseControllerAdvice {

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ErrorResponse> handleAuthenticationException(Exception ex) {
		return ErrorResponse.with(ex.getMessage(), HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> handleAccessDeniedException(Exception ex) {
		return ErrorResponse.with(ex.getMessage(), HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(BlacknodeException.class)
	public ResponseEntity<ErrorResponse> handleBlacknodeException(Exception ex) {
		return ErrorResponse.with(ex.getMessage(), HttpStatus.OK);
	}
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        return ErrorResponse.with(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
}
