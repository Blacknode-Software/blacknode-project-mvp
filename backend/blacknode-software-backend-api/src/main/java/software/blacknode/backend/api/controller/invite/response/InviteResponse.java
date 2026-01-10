package software.blacknode.backend.api.controller.invite.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.api.controller.invite.response.content.InviteResponseContent;
import software.blacknode.backend.api.controller.response.impl.ResponseBySetter;

@SuperBuilder
public class InviteResponse extends InviteResponseContent implements ResponseBySetter<InviteResponse> {

	@Getter @Setter private Status status;
	@Getter @Setter private String message;
	
}
