package software.blacknode.backend.api.controller.task.assign.mapper.impl;

import java.util.UUID;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.task.assign.request.TaskAssignRequest;
import software.blacknode.backend.application.task.assign.command.TaskAssignMemberCommand;

@Mapper(componentModel = "spring")
public interface TaskAssignMemberMapper {

	TaskAssignMemberCommand toCommand(TaskAssignRequest request, UUID taskId);
	
}
