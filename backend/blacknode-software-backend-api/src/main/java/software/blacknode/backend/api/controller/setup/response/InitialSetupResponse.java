package software.blacknode.backend.api.controller.setup.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import software.blacknode.backend.api.controller.response.BaseResponse;

@Builder
@Getter
@AllArgsConstructor
public class InitialSetupResponse extends BaseResponse<InitialSetupResponse>{

	private UUID organizationId;
}
