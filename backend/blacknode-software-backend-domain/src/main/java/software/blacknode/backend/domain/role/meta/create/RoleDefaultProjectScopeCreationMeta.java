package software.blacknode.backend.domain.role.meta.create;

import lombok.Builder;
import lombok.Getter;
import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.domain.modifier.create.meta.CreationMeta;
import software.blacknode.backend.domain.role.meta.RoleMeta.Scope;

@Getter
@Builder
public class RoleDefaultProjectScopeCreationMeta implements CreationMeta {

	private String name;
	private String description;
	private String color;
	
	private HUID organizationId;
}
