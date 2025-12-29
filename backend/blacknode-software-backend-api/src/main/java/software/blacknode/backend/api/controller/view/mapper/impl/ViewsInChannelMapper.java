package software.blacknode.backend.api.controller.view.mapper.impl;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.api.controller.view.mapper.ViewMapper;
import software.blacknode.backend.api.controller.view.response.ViewsListResponse;
import software.blacknode.backend.application.view.usecase.ViewsInChannelUseCase;

@Mapper(componentModel = "spring")
public interface ViewsInChannelMapper extends ViewMapper, ResponseMapper<ViewsInChannelUseCase.Result, ViewsListResponse> {
	
	@Override
	@Mapping(target = "ids", source = "viewsIds")
	ViewsListResponse toResponse(ViewsInChannelUseCase.Result result);
}
