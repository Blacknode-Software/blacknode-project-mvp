package software.blacknode.backend.api.controller.organization.mapper.impl;

import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.api.controller.organization.response.OrganizationResponse;
import software.blacknode.backend.api.controller.organization.response.content.annotation.OrganizationResponseContentMapping;
import software.blacknode.backend.application.organization.usecase.OrganizationFetchUseCase;

public interface OrganizationFetchMapper extends ResponseMapper<OrganizationFetchUseCase.Result, OrganizationResponse> {

	@Override
	@OrganizationResponseContentMapping
	OrganizationResponse toResponse(OrganizationFetchUseCase.Result result);

}
