package software.blacknode.backend.api.controller.task.status.mapper;

import software.blacknode.backend.api.controller.mapper.ControllerMapper;
import software.blacknode.backend.api.controller.task.status.response.content.TaskStatusResponseContent;
import software.blacknode.backend.api.controller.task.status.response.content.annotation.TaskStatusResponseContentMapping;
import software.blacknode.backend.domain.task.status.TaskStatus;

public interface TaskStatusMapper extends ControllerMapper {

	@TaskStatusResponseContentMapping
	TaskStatusResponseContent map(TaskStatus status);
	
}
