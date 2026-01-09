package software.blacknode.backend.api.controller.member.request;

import lombok.Builder;
import lombok.Getter;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.api.controller.request.BaseRequest;

@Getter
@Builder
public class MemberAddRequest extends BaseRequest {

	private final HUID memberId;
	
}
