package software.blacknode.backend.infrastructure.organization.entity.meta;

import lombok.Builder;
import lombok.Getter;
import software.blacknode.backend.infrastructure.entity.version.annotation.VersionedEntity;

@Builder
@Getter
@VersionedEntity
public class OrganizationEntityMeta {

	private String name;
	
}
