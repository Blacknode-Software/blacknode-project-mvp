package software.blacknode.backend.api.controller.view.mapper.impl;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.api.controller.view.mapper.ViewMapper;
import software.blacknode.backend.api.controller.view.response.ViewResponse;
import software.blacknode.backend.api.controller.view.response.content.annotation.ViewResponseContentMapping;
import software.blacknode.backend.application.view.usecase.ViewFetchUseCase;

@Mapper(componentModel = "spring")
public interface ViewFetchMapper extends ViewMapper, ResponseMapper<ViewFetchUseCase.Result, ViewResponse> {

	@Override
	@ViewResponseContentMapping
	ViewResponse toResponse(ViewFetchUseCase.Result result);

}
