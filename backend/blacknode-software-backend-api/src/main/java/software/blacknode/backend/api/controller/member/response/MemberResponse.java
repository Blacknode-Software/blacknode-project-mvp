package software.blacknode.backend.api.controller.member.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.api.controller.member.response.content.MemberResponseContent;
import software.blacknode.backend.api.controller.response.impl.ResponseBySetter;

@Getter
@SuperBuilder
public class MemberResponse extends MemberResponseContent implements ResponseBySetter<MemberResponse> {

	@Getter @Setter private Status status;
	@Getter @Setter private String message;
	
}
