package software.blacknode.backend.domain.project.meta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.With;

@Getter
@With
@Builder
public class ProjectMeta {

	@NotBlank
	@Size(min = 3, max = 30)
	private final String name;
	
	@NotNull
	@Size(max = 255)
	private final String description;
	
	@NotBlank
	@Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$")
	private final String color;
	
}
