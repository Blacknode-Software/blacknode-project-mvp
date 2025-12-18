package software.blacknode.backend.domain.role.meta.create;

import lombok.Builder;
import lombok.Getter;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;

@Getter
@Builder
public class RoleInitialProjectScopeCreationMeta implements CreationMeta {

	private String name;
	private String description;
	private String color;
	
	@Builder.Default
	private boolean byDefaultAssigned = false;
}
