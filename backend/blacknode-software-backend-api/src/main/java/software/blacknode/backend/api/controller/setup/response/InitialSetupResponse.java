package software.blacknode.backend.api.controller.setup.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import software.blacknode.backend.api.controller.response.BaseResponse;

@Builder
@AllArgsConstructor
public class InitialSetupResponse extends BaseResponse<InitialSetupResponse>{

	private UUID organizationId;
}
