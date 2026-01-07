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
	
	private String name;
	
	private String description;
	
	private String color;
	
	private boolean systemDefault;
	
	private boolean byDefaultAssigned;

	private boolean superPrivileged;
	
}
