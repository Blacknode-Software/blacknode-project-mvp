package software.blacknode.backend.domain.project.meta.modify;

import lombok.Builder;
import lombok.Getter;
import software.blacknode.backend.domain.modifier.modify.meta.ModificationMeta;

@Getter
@Builder
public class ProjectDescriptionModificationMeta implements ModificationMeta {

	private final String description;
	
}
