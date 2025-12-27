package software.blacknode.backend.domain.project.meta.modify.impl;

import java.util.Optional;

import lombok.Builder;
import lombok.ToString;
import software.blacknode.backend.domain.project.meta.modify.ProjectModificationMeta;

@Builder
@ToString
public class ProjectNameModificationMeta implements ProjectModificationMeta {

	private String name;
	
	public Optional<String> getName() {
		return Optional.ofNullable(name);
	}
	
}
