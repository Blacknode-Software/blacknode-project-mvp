package software.blacknode.backend.api.controller.task.assign.mapper.impl;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import software.blacknode.backend.api.controller.task.assign.mapper.TaskAssignMapper;
import software.blacknode.backend.api.controller.task.assign.request.TasksAssignsOfMembersRequest;
import software.blacknode.backend.api.controller.task.assign.request.TasksAssignsOfTasksRequest;
import software.blacknode.backend.api.controller.task.assign.response.TasksAssignsListResponse;
import software.blacknode.backend.application.task.assign.command.TasksAssignsOfMembersCommand;
import software.blacknode.backend.application.task.assign.command.TasksAssignsOfTasksCommand;
import software.blacknode.backend.application.task.assign.usecase.TasksAssignsOfMemberUseCase;
import software.blacknode.backend.application.task.assign.usecase.TasksAssignsOfMembersUseCase;
import software.blacknode.backend.application.task.assign.usecase.TasksAssignsOfTaskUseCase;
import software.blacknode.backend.application.task.assign.usecase.TasksAssignsOfTasksUseCase;

@Mapper(componentModel = "spring")
public interface TasksAssignsListMapper extends TaskAssignMapper {

	TasksAssignsOfTasksCommand toCommand(TasksAssignsOfTasksRequest request);
	
	TasksAssignsOfMembersCommand toCommand(TasksAssignsOfMembersRequest request);
	
	@Mapping(target = "ids", source = "assignIds")
	TasksAssignsListResponse toAssignsOfTaskResponse(TasksAssignsOfTaskUseCase.Result result);
	
	@Mapping(target = "ids", source = "assignIds")
	TasksAssignsListResponse toAssignsOfTasksResponse(TasksAssignsOfTasksUseCase.Result result);
	
	@Mapping(target = "ids", source = "assignIds")
	TasksAssignsListResponse toAssingsOfMemberResponse(TasksAssignsOfMemberUseCase.Result result);
	
	@Mapping(target = "ids", source = "assignIds")
	TasksAssignsListResponse toAssingsOfMembersResponse(TasksAssignsOfMembersUseCase.Result result);
	
}
