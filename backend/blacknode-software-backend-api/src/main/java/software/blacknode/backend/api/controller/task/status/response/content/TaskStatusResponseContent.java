package software.blacknode.backend.api.controller.task.status.response.content;

import java.util.UUID;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class TaskStatusResponseContent {

	private final UUID id;
	
	private final String name;
	private final String description;
	private final String color;
	
}
