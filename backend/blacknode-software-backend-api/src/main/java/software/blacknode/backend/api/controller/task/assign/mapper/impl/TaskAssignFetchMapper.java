package software.blacknode.backend.api.controller.task.assign.mapper.impl;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.api.controller.task.assign.response.TaskAssignResponse;
import software.blacknode.backend.application.task.assign.usecase.TaskAssignFetchUseCase;

@Mapper(componentModel = "spring")
public interface TaskAssignFetchMapper extends ResponseMapper<TaskAssignFetchUseCase.Result, TaskAssignResponse> {

}
