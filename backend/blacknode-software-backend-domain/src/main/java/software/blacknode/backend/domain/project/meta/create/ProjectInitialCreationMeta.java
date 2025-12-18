package software.blacknode.backend.domain.project.meta.create;

import lombok.Builder;
import lombok.Getter;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;

@Getter
@Builder
public class ProjectInitialCreationMeta implements CreationMeta {

	private final String name;
	private final String description;
	
	@Builder.Default
	private final String color = "#FAFAFA";
	
}
