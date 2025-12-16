package software.blacknode.backend.api.controller.response.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import lombok.Builder;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.api.controller.response.Response;

@SuperBuilder
public abstract class BatchResponse<R extends BatchResponse<R, T>, T> extends BaseResponse<R> {
	
	@Builder.Default
	private Map<UUID, T> items = new HashMap<>();
	
}
