package software.blacknode.backend.api.controller.task.response.content;

import java.util.UUID;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class TaskResponseContent {

	private UUID id;
	
	private String title;
	private String description;
	
	private Integer priority;
	private Long beginAt;
	private Long endAt;
	
	private UUID statusId;
}
