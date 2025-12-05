package software.blacknode.backend.api.controller.setup.converter;

import org.springframework.stereotype.Component;

import software.blacknode.backend.api.controller.converter.BaseResponseConverter;
import software.blacknode.backend.api.controller.setup.response.InitialSetupResponse;
import software.blacknode.backend.application.setup.usecase.InitialSetupUseCase;
import software.blacknode.backend.application.setup.usecase.InitialSetupUseCase.Result;

@Component
public class InitialSetupResponseConverter implements BaseResponseConverter<InitialSetupUseCase.Result, InitialSetupResponse> {

	@Override
	public InitialSetupResponse convert(Result source) {
		return InitialSetupResponse.builder()
			.organizationId(source.getOrganizationId().toUUID())
			.build();
	}

}
