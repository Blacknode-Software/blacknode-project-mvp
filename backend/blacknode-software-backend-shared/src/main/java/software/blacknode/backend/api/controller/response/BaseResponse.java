package software.blacknode.backend.api.controller.response;

import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public abstract class BaseResponse<T extends BaseResponse<T>> {
	protected static final String DEFAULT_SUCCESS_MESSAGE = "Operation completed successfully.";
	protected static final String DEFAULT_FAILURE_MESSAGE = "Operation failed.";
	
	@Schema(example = DEFAULT_SUCCESS_MESSAGE)
	protected Optional<String> message = Optional.of(DEFAULT_SUCCESS_MESSAGE);
	
	@Schema(example = "success")
	protected Status status = Status.SUCCESS;
	
	private void success(String message) {
		this.status = Status.SUCCESS;
		this.message = Optional.of(message);
	}
	
	private void failure(String message) {
		this.status = Status.FAILURE;
		this.message = Optional.of(message);
	}
	
	@JsonIgnore
	public boolean isSuccess() {
		return this.status == Status.SUCCESS;
	}
	
	@JsonIgnore
	public boolean isFailure() {
		return this.status == Status.FAILURE;
	}
	
	/* ResponseEntity Default Implementations  */
	
	public ResponseEntity<T> toOKResponse() {
		return toSuccessResponse(HttpStatus.OK);
	}
	
	public ResponseEntity<T> toOKResponse(String message) {
		return toSuccessResponse(message, HttpStatus.OK);
	}
	
	
	public ResponseEntity<T> toBadRequestResponse() {
		return toFailureResponse(HttpStatus.BAD_REQUEST);
	}
	
	public ResponseEntity<T> toBadRequestResponse(String message) {
		return toFailureResponse(message, HttpStatus.BAD_REQUEST);
	}
	
	/* ResponseEntity Builders Base  */
	
	public ResponseEntity<T> toSuccessResponse(HttpStatusCode statusCode) {
		return toSuccessResponse(DEFAULT_SUCCESS_MESSAGE, statusCode);
	}
	
	public ResponseEntity<T> toSuccessResponse(String message, HttpStatusCode statusCode) {
		return toSuccessResponse(message, statusCode, (b) -> {});
	}
	
	public ResponseEntity<T> toSuccessResponse(String message, HttpStatusCode statusCode, Consumer<ResponseEntity.BodyBuilder> configurer) {
		success(message);
		
		return toResponse(statusCode, configurer);
	}
	
	public ResponseEntity<T> toFailureResponse(HttpStatusCode statusCode) {
		return toFailureResponse(DEFAULT_FAILURE_MESSAGE, statusCode);
	}
	
	public ResponseEntity<T> toFailureResponse(String message, HttpStatusCode statusCode) {
		return toFailureResponse(message, statusCode, (b) -> {});
	}
	
	public ResponseEntity<T> toFailureResponse(String message, HttpStatusCode statusCode, Consumer<ResponseEntity.BodyBuilder> configurer) {
		failure(message);
		
		return toResponse(statusCode, configurer);
	}
	
	@SuppressWarnings("unchecked")
	private ResponseEntity<T> toResponse(HttpStatusCode statusCode, Consumer<ResponseEntity.BodyBuilder> configurer) {
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