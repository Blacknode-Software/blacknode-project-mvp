package software.blacknode.backend.api.controller.task.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import software.blacknode.backend.api.controller.request.PatchRequest;

@Getter
@AllArgsConstructor
@ToString
public class TaskPatchRequest extends PatchRequest {

	private String title;
	private String description;

	private Integer priority;
	private Long beginAt;
	private Long endAt;

	private UUID statusId;
	
}
