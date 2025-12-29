package software.blacknode.backend.api.controller.task.mapper.impl;

import java.util.UUID;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.api.controller.task.mapper.TaskMapper;
import software.blacknode.backend.api.controller.task.request.TaskCreateRequest;
import software.blacknode.backend.api.controller.task.response.TaskCreateResponse;
import software.blacknode.backend.application.task.command.TaskCreateCommand;
import software.blacknode.backend.application.task.usecase.TaskCreateUseCase;

@Mapper(componentModel = "spring")
public interface TaskCreateMapper extends TaskMapper, ResponseMapper<TaskCreateUseCase.Result, TaskCreateResponse> {

	TaskCreateCommand toCommand(TaskCreateRequest request, UUID channelId);
	
	@Override
	TaskCreateResponse toResponse(TaskCreateUseCase.Result result);
	
}
