package software.blacknode.backend.domain.role.meta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

@Getter
@With
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleMeta {
	
	@Builder.Default
	private String name = "Unknown";
	
	@Builder.Default
	private String description = "Unknown role description";
	
	@Builder.Default
	private String color = "#FF0000";
	
	@Builder.Default
	private boolean systemDefault = false;
	
	@Builder.Default
	private boolean byDefaultAssigned = false;

	@Builder.Default
	private boolean superPrivileged = false;
	
}
