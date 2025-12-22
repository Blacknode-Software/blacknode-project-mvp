package software.blacknode.backend.domain.project.meta.modify.impl;

import java.util.Optional;

import lombok.Builder;
import software.blacknode.backend.domain.project.meta.modify.ProjectModificationMeta;

@Builder
public class ProjectColorModificationMeta implements ProjectModificationMeta {

	private String color;
	
	@Override
	public Optional<String> getColor() {
		return Optional.ofNullable(color);
	}
	
}
