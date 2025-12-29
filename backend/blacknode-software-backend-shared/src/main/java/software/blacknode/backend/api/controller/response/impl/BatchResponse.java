package software.blacknode.backend.api.controller.response.impl;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class BatchResponse<R extends BatchResponse<R, T>, T> extends BaseResponse<R> {
	
	@Builder.Default
	private List<T> items = new ArrayList<>();
	
}
