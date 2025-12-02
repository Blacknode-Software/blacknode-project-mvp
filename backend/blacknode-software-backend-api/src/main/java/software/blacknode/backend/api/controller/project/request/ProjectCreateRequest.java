package software.blacknode.backend.api.controller.project.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProjectCreateRequest {
	private String name;
	private String description;
}
