package software.blacknode.backend.domain.project.meta.modify;

import lombok.Builder;
import lombok.Getter;
import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;

@Getter
@Builder
public class ProjectColorModificationMeta implements ModificationMeta {

	private final String color;
	
}
