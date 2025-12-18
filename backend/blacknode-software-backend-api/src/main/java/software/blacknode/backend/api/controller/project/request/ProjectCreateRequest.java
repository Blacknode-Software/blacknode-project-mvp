package software.blacknode.backend.api.controller.project.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import software.blacknode.backend.api.controller.request.BaseRequest;

@Getter
@NoArgsConstructor
public class ProjectCreateRequest extends BaseRequest {
	private String name;
	private String description;
	private String color;
}
