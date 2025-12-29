package software.blacknode.backend.api.controller.task.mapper.impl;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import software.blacknode.backend.api.controller.mapper.impl.RequestMapper;
import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.api.controller.task.mapper.TaskMapper;
import software.blacknode.backend.api.controller.task.request.TasksBatchFetchRequest;
import software.blacknode.backend.api.controller.task.response.TasksBatchFetchResponse;
import software.blacknode.backend.application.task.command.TasksBatchFetchCommand;
import software.blacknode.backend.application.task.usecase.TasksBatchFetchUseCase;

@Mapper(componentModel = "spring")
public interface TasksBatchFetchMapper extends TaskMapper, RequestMapper<TasksBatchFetchRequest, TasksBatchFetchCommand>, ResponseMapper<TasksBatchFetchUseCase.Result, TasksBatchFetchResponse> {
	
	@Override
	@Mapping(target = "taskIds", source = "ids")
	TasksBatchFetchCommand toCommand(TasksBatchFetchRequest request);
	
	@Override
	@Mapping(target = "items", source = "tasks")
	TasksBatchFetchResponse toResponse(TasksBatchFetchUseCase.Result result);

	
}
