package software.blacknode.backend.domain.project.meta.modify.impl;

import java.util.Optional;

import lombok.Builder;
import software.blacknode.backend.domain.project.meta.modify.ProjectModificationMeta;

@Builder
public class ProjectDescriptionModificationMeta implements ProjectModificationMeta {

	private String description;
	
	public Optional<String> getDescription() {
		return Optional.ofNullable(description);
	}
	
}
