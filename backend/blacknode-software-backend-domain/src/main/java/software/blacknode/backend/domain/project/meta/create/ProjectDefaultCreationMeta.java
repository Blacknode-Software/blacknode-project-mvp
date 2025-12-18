package software.blacknode.backend.domain.project.meta.create;

import lombok.Builder;
import lombok.Getter;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;

@Getter
@Builder
public class ProjectDefaultCreationMeta implements CreationMeta {
	
	private final String name;
	private final String description;
	private final String color;
	
}
