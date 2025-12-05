package software.blacknode.backend.api.controller.setup.converter;

import software.blacknode.backend.api.controller.converter.BaseResponseConverter;
import software.blacknode.backend.api.controller.setup.response.InitialSetupResponse;
import software.blacknode.backend.application.setup.usecase.InitialSetupUseCase;
import software.blacknode.backend.application.setup.usecase.InitialSetupUseCase.Result;

public class InitialSetupResponseConverter implements BaseResponseConverter<InitialSetupUseCase.Result, InitialSetupResponse> {

	@Override
	public InitialSetupResponse convert(Result source) {
		return InitialSetupResponse.builder()
			.organizationId(source.getOrganizationId().toUUID())
			.build();
	}

}
