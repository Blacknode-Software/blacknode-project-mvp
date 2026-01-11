package software.blacknode.backend.api.controller.profile.mapper.impl;

import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.api.controller.profile.mapper.ProfileMapper;
import software.blacknode.backend.api.controller.profile.response.ProfileResponse;
import software.blacknode.backend.api.controller.profile.response.content.annotation.ProfileResponseContentMapping;
import software.blacknode.backend.application.profile.usecase.ProfileFetchUseCase;

public interface ProfileFetchMapper extends ProfileMapper, ResponseMapper<ProfileFetchUseCase.Result, ProfileResponse> {

	@Override
	@ProfileResponseContentMapping
	ProfileResponse toResponse(ProfileFetchUseCase.Result result);
	
}
