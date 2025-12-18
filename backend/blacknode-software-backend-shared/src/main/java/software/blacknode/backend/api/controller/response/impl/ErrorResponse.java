package software.blacknode.backend.api.controller.response.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class ErrorResponse extends BaseResponse<ErrorResponse> {
	
	@Schema(example = DEFAULT_FAILURE_MESSAGE)
	@Builder.Default
	protected String message = BaseResponse.DEFAULT_FAILURE_MESSAGE;
	
	@Schema(example = "failure")
	@Builder.Default
	protected Status status = Status.FAILURE;
	
	public static ResponseEntity<ErrorResponse> with(String message) {
		return with(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public static ResponseEntity<ErrorResponse> with(String message, HttpStatusCode status) {
		ErrorResponse response = ErrorResponse.builder().build();
		return response.toFailureResponse(message, status);
	}
}
