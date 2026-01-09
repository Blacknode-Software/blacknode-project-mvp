package software.blacknode.backend.api.controller.role.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.api.controller.response.impl.ResponseBySetter;
import software.blacknode.backend.api.controller.role.response.content.RoleResponseContent;

@SuperBuilder
public class RoleResponse extends RoleResponseContent implements ResponseBySetter<RoleResponse> {

	@Getter @Setter private Status status;
	@Getter @Setter private String message;
	
}
