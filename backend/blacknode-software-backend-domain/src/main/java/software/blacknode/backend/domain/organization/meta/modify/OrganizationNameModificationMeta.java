package software.blacknode.backend.domain.organization.meta.modify;

import lombok.Builder;
import lombok.Getter;
import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;

@Getter
@Builder
public class OrganizationNameModificationMeta implements ModificationMeta {

	private final String name;
	
}
