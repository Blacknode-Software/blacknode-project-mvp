package software.blacknode.backend.api.controller.role.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import software.blacknode.backend.api.controller.request.PatchRequest;

@Getter
@AllArgsConstructor
@ToString
public class RolePatchRequest extends PatchRequest {

	private String name;
	
	private String description;
	
	private String color;
	
}
