package software.blacknode.backend.api.controller.project.response;

import java.util.UUID;

import lombok.Builder;
import software.blacknode.backend.api.controller.response.BaseResponse;
import software.blacknode.backend.domain.project.Project;

@Builder
public class ProjectResponse extends BaseResponse<ProjectResponse> {

	public static ProjectResponse from(Project project) {
		var meta = project.getMeta();
		var id = project.getId();
		
		var name = meta.getName();
		var description = meta.getDescription();
		var color = meta.getColor();
		
		return ProjectResponse.builder()
				.id(id.toUUID())
				.name(name)
				.description(description)
				.color(color)
				.build();
	}
	
	private UUID id;
	private String name;
	private String description;
	private String color;
	
}
