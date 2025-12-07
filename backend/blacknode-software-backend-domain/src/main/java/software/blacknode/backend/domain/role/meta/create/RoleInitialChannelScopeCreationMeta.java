package software.blacknode.backend.domain.role.meta.create;

import lombok.Builder;
import lombok.Getter;
import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.domain.modifier.create.meta.CreationMeta;
import software.blacknode.backend.domain.role.meta.RoleMeta.Scope;

@Getter
@Builder
public class RoleInitialChannelScopeCreationMeta implements CreationMeta {

	private String name;
	private String description;
	private String color;
	
	@Builder.Default
	private boolean byDefaultAssigned = false;
	
	private HUID organizationId;
}
