package software.blacknode.backend.api.controller.role.request;

import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import software.blacknode.backend.api.controller.request.BaseRequest;

@Getter
@NoArgsConstructor
public class RoleCreateRequest extends BaseRequest {

	private String name;
	
	private String description;
	
	private String color;
	
	private UUID inheritedRoleId;
}
