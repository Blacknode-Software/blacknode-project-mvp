package software.blacknode.backend.api.controller.task.assign.mapper.impl;

import software.blacknode.backend.api.controller.task.assign.response.TasksAssignsListResponse;
import software.blacknode.backend.application.task.assign.usecase.TasksAssignsOfMemberUseCase;
import software.blacknode.backend.application.task.assign.usecase.TasksAssignsOfMembersUseCase;
import software.blacknode.backend.application.task.assign.usecase.TasksAssignsOfTaskUseCase;

public interface TasksAssignsListMapper {

	TasksAssignsListResponse toAssignsOfTaskResponse(TasksAssignsOfTaskUseCase.Result result);
	
	TasksAssignsListResponse toAssignsOfTasksResponse(TasksAssignsOfTaskUseCase.Result result);
	
	TasksAssignsListResponse toAssingsOfMemberResponse(TasksAssignsOfMemberUseCase.Result result);
	
	TasksAssignsListResponse toAssingsOfMembersResponse(TasksAssignsOfMembersUseCase.Result result);
	
}
