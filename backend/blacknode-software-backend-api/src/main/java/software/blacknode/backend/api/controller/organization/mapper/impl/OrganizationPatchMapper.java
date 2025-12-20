package software.blacknode.backend.api.controller.organization.mapper.impl;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.mapper.impl.RequestMapper;
import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.api.controller.organization.request.OrganizationPatchRequest;
import software.blacknode.backend.api.controller.organization.response.OrganizationPatchResponse;
import software.blacknode.backend.application.organization.command.OrganizationPatchCommand;
import software.blacknode.backend.application.organization.usecase.OrganizationPatchUseCase;

@Mapper(componentModel = "spring")
public interface OrganizationPatchMapper extends RequestMapper<OrganizationPatchRequest, OrganizationPatchCommand>, ResponseMapper<OrganizationPatchUseCase.Result, OrganizationPatchResponse> {

}
