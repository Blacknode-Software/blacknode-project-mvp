package software.blacknode.backend.api.controller.project.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import software.blacknode.backend.api.controller.request.PatchRequest;

@Getter
@Setter
@AllArgsConstructor
public class ProjectPatchRequest extends PatchRequest {
	private String name;
	private String description;

}
