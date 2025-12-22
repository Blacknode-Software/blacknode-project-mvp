package software.blacknode.backend.domain.project.meta.modify.impl;

import java.util.Optional;

import lombok.Builder;
import lombok.ToString;
import software.blacknode.backend.domain.project.meta.modify.ProjectModificationMeta;

@Builder
@ToString
public class ProjectColorModificationMeta implements ProjectModificationMeta {

	private String color;
	
	@Override
	public Optional<String> getColor() {
		return Optional.ofNullable(color);
	}
	
}
