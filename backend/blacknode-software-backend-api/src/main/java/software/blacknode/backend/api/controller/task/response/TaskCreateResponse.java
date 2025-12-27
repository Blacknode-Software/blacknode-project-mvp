package software.blacknode.backend.api.controller.task.response;

import java.util.UUID;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.api.controller.response.impl.BaseResponse;

@Getter
@SuperBuilder
public class TaskCreateResponse extends BaseResponse<TaskCreateResponse> {

	private UUID taskId;
	
}
