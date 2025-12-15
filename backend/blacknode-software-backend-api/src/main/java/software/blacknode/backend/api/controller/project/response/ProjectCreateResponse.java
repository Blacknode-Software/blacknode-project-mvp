package software.blacknode.backend.api.controller.project.response;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import software.blacknode.backend.api.controller.response.BaseResponse;

@Getter
@Builder
public class ProjectCreateResponse extends BaseResponse<ProjectCreateResponse> {

	private UUID projectId;
	
}
