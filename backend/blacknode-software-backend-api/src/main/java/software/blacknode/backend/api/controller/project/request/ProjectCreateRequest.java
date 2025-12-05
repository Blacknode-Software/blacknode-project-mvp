package software.blacknode.backend.api.controller.project.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import software.blacknode.backend.api.controller.request.BaseRequest;

@Getter
@Setter
@AllArgsConstructor
public class ProjectCreateRequest extends BaseRequest {
	private String name;
	private String description;
}
