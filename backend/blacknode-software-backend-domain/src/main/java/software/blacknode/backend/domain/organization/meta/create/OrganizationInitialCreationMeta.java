package software.blacknode.backend.domain.organization.meta.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import software.blacknode.backend.domain.modifier.create.meta.CreationMeta;

@AllArgsConstructor
public class OrganizationInitialCreationMeta implements CreationMeta {

	@Getter private String name;
	
}
