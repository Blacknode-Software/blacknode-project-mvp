package software.blacknode.backend.api.controller.advice;

import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import software.blacknode.backend.api.controller.response.impl.ErrorResponse;
import software.blacknode.backend.domain.auth.exception.AuthenticationException;
import software.blacknode.backend.domain.exception.BlacknodeException;

@RestControllerAdvice
public class BaseControllerAdvice {

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
		return ErrorResponse.with(ex.getMessage(), HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(JwtException.class)
	public ResponseEntity<ErrorResponse> handleJwtException(JwtException ex) {
		return ErrorResponse.with(ex.getMessage(), HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<ErrorResponse> handleJwtExpiredException(ExpiredJwtException ex) {
		return ErrorResponse.with("Your token has expired!", HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
		return ErrorResponse.with(ex.getMessage(), HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(BlacknodeException.class)
	public ResponseEntity<ErrorResponse> handleBlacknodeException(BlacknodeException ex) {
		return ErrorResponse.with(ex.getMessage(), HttpStatus.OK);
	}
	
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
		return ErrorResponse.with(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        ex.printStackTrace();
        
		return ErrorResponse.with(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
}
