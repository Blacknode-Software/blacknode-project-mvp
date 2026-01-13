package software.blacknode.backend.api.controller.task.assign.mapper;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.mapper.ControllerMapper;
import software.blacknode.backend.api.controller.task.assign.response.content.TaskAssignResponseContent;
import software.blacknode.backend.api.controller.task.assign.response.content.annotation.TaskAssignResponseContentMapping;
import software.blacknode.backend.domain.task.assign.TaskAssign;

@Mapper(componentModel = "spring")
public interface TaskAssignMapper extends ControllerMapper {

	@TaskAssignResponseContentMapping
	TaskAssignResponseContent map(TaskAssign assign);
	
}
