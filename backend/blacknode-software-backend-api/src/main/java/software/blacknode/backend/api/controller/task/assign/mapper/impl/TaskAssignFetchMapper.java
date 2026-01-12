package software.blacknode.backend.api.controller.task.assign.mapper.impl;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.api.controller.task.assign.mapper.TaskAssignMapper;
import software.blacknode.backend.api.controller.task.assign.response.TaskAssignResponse;
import software.blacknode.backend.api.controller.task.assign.response.content.annotation.TaskAssignResponseContentMapping;
import software.blacknode.backend.application.task.assign.usecase.TaskAssignFetchUseCase;

@Mapper(componentModel = "spring")
public interface TaskAssignFetchMapper extends TaskAssignMapper, ResponseMapper<TaskAssignFetchUseCase.Result, TaskAssignResponse> {

	@Override
	@TaskAssignResponseContentMapping
	TaskAssignResponse toResponse(TaskAssignFetchUseCase.Result result);
	
}
