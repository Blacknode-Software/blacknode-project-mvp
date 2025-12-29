package software.blacknode.backend.api.controller.view.mapper.impl;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.api.controller.view.response.ViewResponse;
import software.blacknode.backend.application.view.usecase.ViewFetchUseCase;

@Mapper(componentModel = "spring")
public interface ViewFetchMapper extends ResponseMapper<ViewFetchUseCase.Result, ViewResponse> {

	@Override
	ViewResponse toResponse(ViewFetchUseCase.Result result);

}
