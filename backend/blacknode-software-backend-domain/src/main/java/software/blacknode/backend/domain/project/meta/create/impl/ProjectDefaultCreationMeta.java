package software.blacknode.backend.domain.project.meta.create.impl;

import java.util.Optional;

import lombok.Builder;
import lombok.ToString;
import software.blacknode.backend.domain.project.meta.create.ProjectCreationMeta;

@Builder
@ToString
public class ProjectDefaultCreationMeta implements ProjectCreationMeta {

	private String name;
	private String description;
	private String color;
	
	@Override
	public Optional<String> getName() {
		return Optional.ofNullable(name);
	}
	
	@Override
	public Optional<String> getDescription() {
		return Optional.ofNullable(description);
	}
	
	@Override
	public Optional<String> getColor() {
		return Optional.ofNullable(color);
	}
	
}
