package software.blacknode.backend.api.controller.task.mapper.impl;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.api.controller.task.response.TaskResponse;
import software.blacknode.backend.api.controller.task.response.content.annotation.TaskResponseContentMapping;
import software.blacknode.backend.application.task.usecase.TaskFetchUseCase;

@Mapper(componentModel = "spring")
public interface TaskFetchMapper extends ResponseMapper<TaskFetchUseCase.Result, TaskResponse> {

	@Override
	@TaskResponseContentMapping
	TaskResponse toResponse(TaskFetchUseCase.Result result);

}
