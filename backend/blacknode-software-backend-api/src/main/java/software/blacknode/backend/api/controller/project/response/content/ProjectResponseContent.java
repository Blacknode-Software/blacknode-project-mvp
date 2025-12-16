package software.blacknode.backend.api.controller.project.response.content;

import java.util.UUID;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.domain.project.Project;

@Getter
@SuperBuilder
public class ProjectResponseContent {
	
	public static ProjectResponseContent from(Project project) {
		var meta = project.getMeta();
		var id = project.getId();
		
		var name = meta.getName();
		var description = meta.getDescription();
		var color = meta.getColor();
		
		return ProjectResponseContent.builder()
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
