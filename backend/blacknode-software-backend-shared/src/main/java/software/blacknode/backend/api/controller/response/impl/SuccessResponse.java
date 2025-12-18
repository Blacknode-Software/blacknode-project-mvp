package software.blacknode.backend.api.controller.response.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class SuccessResponse extends BaseResponse<SuccessResponse> {
	
	@Schema(example = DEFAULT_SUCCESS_MESSAGE)
	@Builder.Default
	protected String message = BaseResponse.DEFAULT_SUCCESS_MESSAGE;
	
	@Schema(example = "success")
	@Builder.Default
	protected Status status = Status.SUCCESS;
	
	public static ResponseEntity<SuccessResponse> with(String message) {
		return with(message, HttpStatus.OK);
	}
	
	public static ResponseEntity<SuccessResponse> with(String message, HttpStatusCode status) {
		SuccessResponse response = SuccessResponse.builder().build();
		return response.toSuccessResponse(message, status);
	}
}
