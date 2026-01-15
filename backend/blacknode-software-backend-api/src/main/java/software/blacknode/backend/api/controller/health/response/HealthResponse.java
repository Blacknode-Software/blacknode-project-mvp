package software.blacknode.backend.api.controller.health.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.api.controller.response.impl.BaseResponse;

@Getter
@SuperBuilder
public class HealthResponse extends BaseResponse<HealthResponse> {

	private final String state;
	
	private final String version;
	
}
