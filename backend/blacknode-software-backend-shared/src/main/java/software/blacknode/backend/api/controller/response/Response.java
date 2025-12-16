package software.blacknode.backend.api.controller.response;

import java.util.function.Consumer;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

public interface Response<T extends Response<T>> {
	
	public static final String DEFAULT_SUCCESS_MESSAGE = "Operation completed successfully.";
	public static final String DEFAULT_FAILURE_MESSAGE = "Operation failed.";
	
	public void success(String message);
	public void failure(String message);
	
	public String getMessage();
	public Status getStatus();
	
	@JsonIgnore
	public default boolean isSuccess() {
		return getStatus() == Status.SUCCESS;
	}
	
	@JsonIgnore
	public default boolean isFailure() {
		return getStatus() == Status.FAILURE;
	}
	
	/* ResponseEntity Default Implementations  */
	
	public default ResponseEntity<T> toOkResponse() {
		return toSuccessResponse(HttpStatus.OK);
	}
	
	public default ResponseEntity<T> toOkResponse(String message) {
		return toSuccessResponse(message, HttpStatus.OK);
	}
	
	
	public default ResponseEntity<T> toBadRequestResponse() {
		return toFailureResponse(HttpStatus.BAD_REQUEST);
	}
	
	public default ResponseEntity<T> toBadRequestResponse(String message) {
		return toFailureResponse(message, HttpStatus.BAD_REQUEST);
	}
	
	/* ResponseEntity Builders Base  */
	
	public default ResponseEntity<T> toSuccessResponse(HttpStatusCode statusCode) {
		return toSuccessResponse(DEFAULT_SUCCESS_MESSAGE, statusCode);
	}
	
	public default ResponseEntity<T> toSuccessResponse(String message, HttpStatusCode statusCode) {
		return toSuccessResponse(message, statusCode, (b) -> {});
	}
	
	public default ResponseEntity<T> toSuccessResponse(String message, HttpStatusCode statusCode, Consumer<ResponseEntity.BodyBuilder> configurer) {
		success(message);
		
		return toResponse(statusCode, configurer);
	}
	
	public default ResponseEntity<T> toFailureResponse(HttpStatusCode statusCode) {
		return toFailureResponse(DEFAULT_FAILURE_MESSAGE, statusCode);
	}
	
	public default ResponseEntity<T> toFailureResponse(String message, HttpStatusCode statusCode) {
		return toFailureResponse(message, statusCode, (b) -> {});
	}
	
	public default ResponseEntity<T> toFailureResponse(String message, HttpStatusCode statusCode, Consumer<ResponseEntity.BodyBuilder> configurer) {
		failure(message);
		
		return toResponse(statusCode, configurer);
	}
	
	@SuppressWarnings("unchecked")
	public default ResponseEntity<T> toResponse(HttpStatusCode statusCode, Consumer<ResponseEntity.BodyBuilder> configurer) {
		var builder = ResponseEntity.status(statusCode);
		
		configurer.accept(builder);
		
		return builder.body((T) this);
	}
	
	public static enum Status {
		SUCCESS,
		FAILURE;

		@JsonValue
		public String toLowerCase() {
			return name().toLowerCase();
		}

		@JsonCreator
		public static Status fromString(String value) {
			return Status.valueOf(value.toUpperCase());
		}
	}
}
