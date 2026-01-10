package software.blacknode.backend.api.controller.member.request;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import software.blacknode.backend.api.controller.request.BaseRequest;

@Getter
@Builder
public class MemberAddRequest extends BaseRequest {

	private final UUID memberId;
	
}
