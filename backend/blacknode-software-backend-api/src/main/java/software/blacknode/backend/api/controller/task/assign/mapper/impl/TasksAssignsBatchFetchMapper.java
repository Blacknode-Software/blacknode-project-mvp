package software.blacknode.backend.api.controller.task.assign.mapper.impl;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import software.blacknode.backend.api.controller.mapper.impl.RequestMapper;
import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.api.controller.task.assign.mapper.TaskAssignMapper;
import software.blacknode.backend.api.controller.task.assign.request.TasksAssignsBatchFetchRequest;
import software.blacknode.backend.api.controller.task.assign.response.TasksAssignsBatchFetchResponse;
import software.blacknode.backend.application.task.assign.command.TasksAssignsBatchFetchCommand;
import software.blacknode.backend.application.task.assign.usecase.TasksAssignsBatchFetchUseCase;

@Mapper(componentModel = "spring")
public interface TasksAssignsBatchFetchMapper extends TaskAssignMapper, RequestMapper<TasksAssignsBatchFetchRequest, TasksAssignsBatchFetchCommand>, ResponseMapper<TasksAssignsBatchFetchUseCase.Result, TasksAssignsBatchFetchResponse> {

	@Override
	@Mapping(target = "assignIds", source = "ids")
	TasksAssignsBatchFetchCommand toCommand(TasksAssignsBatchFetchRequest request);
	
	@Override
	@Mapping(target = "items", source = "taskAssigns")
	TasksAssignsBatchFetchResponse toResponse(TasksAssignsBatchFetchUseCase.Result result);

	
}
