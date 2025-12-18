package software.blacknode.backend.api.controller.project.response;

import java.util.UUID;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.api.controller.response.impl.BaseResponse;

@Getter
@SuperBuilder
public class ProjectCreateResponse extends BaseResponse<ProjectCreateResponse> {

	private UUID projectId;
	
}
