package software.blacknode.backend.domain.project.meta;

import lombok.Builder;
import lombok.Getter;
import lombok.With;

@Getter
@With
@Builder
public class ProjectMeta {

	@Builder.Default
	private final String name = "Unknown Project";
	
	@Builder.Default
	private final String description = "Unknown description";
	
	@Builder.Default
	private final String color = "#FAFAFA";
}
