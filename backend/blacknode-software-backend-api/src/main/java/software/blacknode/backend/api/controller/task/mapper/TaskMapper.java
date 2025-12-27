package software.blacknode.backend.api.controller.task.mapper;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.mapper.ControllerMapper;
import software.blacknode.backend.api.controller.task.response.content.TaskResponseContent;
import software.blacknode.backend.api.controller.task.response.content.annotation.TaskResponseContentMapping;
import software.blacknode.backend.domain.task.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper extends ControllerMapper {

	@TaskResponseContentMapping
	TaskResponseContent map(Task task);
	
}
