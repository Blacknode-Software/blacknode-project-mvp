package software.blacknode.backend.api.controller.task.mapper.impl;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.api.controller.task.response.TasksListResponse;
import software.blacknode.backend.application.task.usecase.TasksInChannelUseCase;

@Mapper(componentModel = "spring")
public interface TasksInChannelMapper extends ResponseMapper<TasksInChannelUseCase.Result, TasksListResponse> {

	@Override
	@Mapping(target = "ids", source = "tasksIds")
	TasksListResponse toResponse(TasksInChannelUseCase.Result result);
	
	
	
}
