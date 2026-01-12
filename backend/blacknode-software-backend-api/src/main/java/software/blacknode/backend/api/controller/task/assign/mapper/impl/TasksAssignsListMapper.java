package software.blacknode.backend.api.controller.task.assign.mapper.impl;

import software.blacknode.backend.api.controller.task.assign.request.TasksAssignsOfMembersRequest;
import software.blacknode.backend.api.controller.task.assign.request.TasksAssignsOfTasksRequest;
import software.blacknode.backend.api.controller.task.assign.response.TasksAssignsListResponse;
import software.blacknode.backend.application.task.assign.command.TasksAssignsOfMembersCommand;
import software.blacknode.backend.application.task.assign.command.TasksAssignsOfTasksCommand;
import software.blacknode.backend.application.task.assign.usecase.TasksAssignsOfMemberUseCase;
import software.blacknode.backend.application.task.assign.usecase.TasksAssignsOfMembersUseCase;
import software.blacknode.backend.application.task.assign.usecase.TasksAssignsOfTaskUseCase;
import software.blacknode.backend.application.task.assign.usecase.TasksAssignsOfTasksUseCase;

public interface TasksAssignsListMapper {

	TasksAssignsOfTasksCommand toCommand(TasksAssignsOfTasksRequest request);
	
	TasksAssignsOfMembersCommand toCommand(TasksAssignsOfMembersRequest request);
	
	TasksAssignsListResponse toAssignsOfTaskResponse(TasksAssignsOfTaskUseCase.Result result);
	
	TasksAssignsListResponse toAssignsOfTasksResponse(TasksAssignsOfTasksUseCase.Result result);
	
	TasksAssignsListResponse toAssingsOfMemberResponse(TasksAssignsOfMemberUseCase.Result result);
	
	TasksAssignsListResponse toAssingsOfMembersResponse(TasksAssignsOfMembersUseCase.Result result);
	
}
