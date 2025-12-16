package software.blacknode.backend.api.controller.response.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class ListResponse<T extends ListResponse<T>> extends BaseResponse<T> {
	
	@Builder.Default
	private List<UUID> ids = new ArrayList<>();
	
}
