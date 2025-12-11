package software.blacknode.backend.domain.project.meta.create;

import lombok.Builder;
import lombok.Getter;
import software.blacknode.backend.domain.modifier.create.meta.CreationMeta;

@Getter
@Builder
public class ProjectDefaultCreationMeta implements CreationMeta {
	
	private String name;
	private String description;
	private String color;
	
}
