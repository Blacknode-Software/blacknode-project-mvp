package software.blacknode.backend.api.controller.member.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import software.blacknode.backend.api.controller.request.BaseRequest;

@Getter
@NoArgsConstructor
@ToString
public class MemberRemoveRequest extends BaseRequest {

	private UUID memberId;
	
}
