package software.blacknode.backend.api.controller.role.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import software.blacknode.backend.api.controller.request.PatchRequest;

@Getter
@NoArgsConstructor
public class RolePatchRequest extends PatchRequest {

	private String name;
	
	private String description;
	
	private String color;
	
}
