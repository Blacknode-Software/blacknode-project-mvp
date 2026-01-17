package software.blacknode.backend.api.controller.role.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import software.blacknode.backend.api.controller.request.BaseRequest;

@Getter
@NoArgsConstructor
@ToString
public class RoleCreateRequest extends BaseRequest {

	private String name;
	
	private String description;
	
	private String color;
	
	private UUID inheritedRoleId;
}
