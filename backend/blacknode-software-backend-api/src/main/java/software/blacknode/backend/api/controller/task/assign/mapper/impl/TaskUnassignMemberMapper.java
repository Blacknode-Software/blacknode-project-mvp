package software.blacknode.backend.api.controller.task.assign.mapper.impl;

import java.util.UUID;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.task.assign.mapper.TaskAssignMapper;
import software.blacknode.backend.api.controller.task.assign.request.TaskUnassignMemberRequest;
import software.blacknode.backend.application.task.assign.command.TaskUnassignMemberCommand;

@Mapper(componentModel = "spring")
public interface TaskUnassignMemberMapper extends TaskAssignMapper {

	TaskUnassignMemberCommand toCommand(TaskUnassignMemberRequest request, UUID taskId);
	
}
