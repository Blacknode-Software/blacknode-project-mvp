package software.blacknode.backend.api.controller.task.mapper.impl;

import java.util.UUID;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.mapper.annotation.PatchOperationsMappingRequest;
import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.api.controller.task.mapper.TaskMapper;
import software.blacknode.backend.api.controller.task.request.TaskPatchRequest;
import software.blacknode.backend.api.controller.task.response.TaskPatchResponse;
import software.blacknode.backend.api.controller.task.response.content.annotation.TaskResponseContentMapping;
import software.blacknode.backend.application.task.command.TaskPatchCommand;
import software.blacknode.backend.application.task.usecase.TaskPatchUseCase;

@Mapper(componentModel = "spring")
public interface TaskPatchMapper extends TaskMapper, ResponseMapper<TaskPatchUseCase.Result, TaskPatchResponse> {

	@PatchOperationsMappingRequest
	TaskPatchCommand toCommand(TaskPatchRequest request, UUID id);
	
	@Override
	@TaskResponseContentMapping
	TaskPatchResponse toResponse(TaskPatchUseCase.Result result);
	
}
