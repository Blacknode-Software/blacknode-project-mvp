package software.blacknode.backend.api.controller.project.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.blacknode.backend.api.controller.request.PatchRequest;

@Getter
@Setter
@NoArgsConstructor
public class ProjectPatchRequest extends PatchRequest {
	private String name;
	private String description;
	private String color;
}
