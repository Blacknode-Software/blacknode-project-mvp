package software.blacknode.backend.api.controller.member.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import software.blacknode.backend.api.controller.request.BaseRequest;

@Getter
@AllArgsConstructor
@ToString
public class MemberAddRequest extends BaseRequest {

	private final UUID memberId;
	
}
