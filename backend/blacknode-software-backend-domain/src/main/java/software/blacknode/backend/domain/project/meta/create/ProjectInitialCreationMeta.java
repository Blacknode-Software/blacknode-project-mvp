package software.blacknode.backend.domain.project.meta.create;

import lombok.Builder;
import lombok.Getter;
import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.domain.modifier.create.meta.CreationMeta;

@Getter
@Builder
public class ProjectInitialCreationMeta implements CreationMeta {

	private String name;
	private String description;
	
	@Builder.Default
	private String color = "#FAFAFA";
	
}
