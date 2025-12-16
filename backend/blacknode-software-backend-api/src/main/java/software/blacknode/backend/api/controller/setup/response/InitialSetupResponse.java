package software.blacknode.backend.api.controller.setup.response;

import java.util.UUID;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.api.controller.response.impl.BaseResponse;

@Getter
@SuperBuilder
public class InitialSetupResponse extends BaseResponse<InitialSetupResponse>{

	private UUID organizationId;
}
