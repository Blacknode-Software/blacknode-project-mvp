package software.blacknode.backend.domain.organization.meta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.With;


@Getter
@With
@Builder
public class OrganizationMeta {
	
	@NotBlank
	@Size(min = 3)
	private final String name;

}
