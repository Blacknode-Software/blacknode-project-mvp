package software.blacknode.backend.api.controller.response.impl;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.api.controller.response.Response;

@Getter
@SuperBuilder
public class BaseResponse<T extends Response<T>> implements Response<T> {
	
	@Schema(example = DEFAULT_SUCCESS_MESSAGE)
	@Builder.Default
	protected String message = DEFAULT_SUCCESS_MESSAGE;
	
	@Schema(example = "success")
	@Builder.Default
	protected Status status = Status.SUCCESS;

	@Override
	public void success(String message) {
		this.message = message;
		this.status = Status.SUCCESS;
	}

	@Override
	public void failure(String message) {
		this.message = message;
		this.status = Status.FAILURE;
	}
	
}