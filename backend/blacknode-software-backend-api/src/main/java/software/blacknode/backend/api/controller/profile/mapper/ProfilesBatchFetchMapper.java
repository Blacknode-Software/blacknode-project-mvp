package software.blacknode.backend.api.controller.profile.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import software.blacknode.backend.api.controller.mapper.impl.RequestMapper;
import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.api.controller.profile.request.ProfilesBatchFetchRequest;
import software.blacknode.backend.api.controller.profile.response.ProfilesBatchFetchResponse;
import software.blacknode.backend.application.profile.command.ProfilesBatchFetchCommand;
import software.blacknode.backend.application.profile.usecase.ProfilesBatchFetchUseCase;

@Mapper(componentModel = "spring")
public interface ProfilesBatchFetchMapper extends RequestMapper<ProfilesBatchFetchRequest, ProfilesBatchFetchCommand>, ResponseMapper<ProfilesBatchFetchUseCase.Result, ProfilesBatchFetchResponse> {

	@Override
	@Mapping(target = "memberIds", source = "ids")
	ProfilesBatchFetchCommand toCommand(ProfilesBatchFetchRequest request);
	
	@Override
	@Mapping(target = "items", source = "profiles")
	ProfilesBatchFetchResponse toResponse(ProfilesBatchFetchUseCase.Result result);
	
}
