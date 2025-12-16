package software.blacknode.backend.domain.role.meta.create;

import lombok.Builder;
import lombok.Getter;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.modifier.create.meta.CreationMeta;

@Getter
@Builder
public class RoleInitialChannelScopeCreationMeta implements CreationMeta {

	private String name;
	private String description;
	private String color;
	
	@Builder.Default
	private boolean byDefaultAssigned = false;
}
