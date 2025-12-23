package software.blacknode.backend.api.controller.project.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import software.blacknode.backend.api.controller.request.PatchRequest;

@Getter
@NoArgsConstructor
public class ProjectPatchRequest extends PatchRequest {
	
	private String name;
	private String description;
	private String color;
	
}
