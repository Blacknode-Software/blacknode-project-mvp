package software.blacknode.backend.api.controller.health.mapper;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.health.response.HealthResponse;
import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.application.health.usecase.HealthFetchUseCase;

@Mapper(componentModel = "spring")
public interface HealthFetchMapper extends ResponseMapper<HealthFetchUseCase.Result, HealthResponse> {

	@Override
	HealthResponse toResponse(HealthFetchUseCase.Result result);
	
}
