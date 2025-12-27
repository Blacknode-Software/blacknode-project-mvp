package software.blacknode.backend.api.controller.task.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import software.blacknode.backend.api.controller.request.PatchRequest;

@Getter
@NoArgsConstructor
public class TaskPatchRequest extends PatchRequest {

	private String title;
	private String description;

	private Integer priority;
	private Long beginAtTimestamp;
	private Long endAtTimestamp;

	private String statusId;
	
}
