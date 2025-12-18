package software.blacknode.backend.domain.organization.meta.create;

import lombok.Builder;
import lombok.Getter;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;

@Getter
@Builder
public class OrganizationInitialCreationMeta implements CreationMeta {

	private String name;
	
}
