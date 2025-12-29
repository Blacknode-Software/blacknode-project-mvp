package software.blacknode.backend.api.controller.task.request;

import java.time.Instant;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import software.blacknode.backend.api.controller.request.PatchRequest;

@Getter
@NoArgsConstructor
public class TaskPatchRequest extends PatchRequest {

	private String title;
	private String description;

	private Integer priority;
	private Instant beginAt;
	private Instant endAt;

	private UUID statusId;
	
}
