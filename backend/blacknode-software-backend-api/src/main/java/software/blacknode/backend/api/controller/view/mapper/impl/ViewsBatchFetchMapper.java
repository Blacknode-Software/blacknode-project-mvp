package software.blacknode.backend.api.controller.view.mapper.impl;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import software.blacknode.backend.api.controller.mapper.impl.RequestMapper;
import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.api.controller.view.mapper.ViewMapper;
import software.blacknode.backend.api.controller.view.request.ViewsBatchFetchRequest;
import software.blacknode.backend.api.controller.view.response.ViewsBatchFetchResponse;
import software.blacknode.backend.application.view.command.ViewsBatchFetchCommand;
import software.blacknode.backend.application.view.usecase.ViewsBatchFetchUseCase;

@Mapper(componentModel = "spring")
public interface ViewsBatchFetchMapper extends ViewMapper, RequestMapper<ViewsBatchFetchRequest, ViewsBatchFetchCommand>, ResponseMapper<ViewsBatchFetchUseCase.Result, ViewsBatchFetchResponse> {
	
	@Override
	@Mapping(target = "viewIds", source = "ids")
	ViewsBatchFetchCommand toCommand(ViewsBatchFetchRequest request);
	
	@Override
	@Mapping(target = "items", source = "views")
	ViewsBatchFetchResponse toResponse(ViewsBatchFetchUseCase.Result result);

}
