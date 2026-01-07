package software.blacknode.backend.infrastructure.role.entity.meta;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import software.blacknode.backend.infrastructure.entity.version.VersionableEntity;
import software.blacknode.backend.infrastructure.entity.version.annotation.VersionedEntity;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@VersionedEntity
@ToString
public class RoleEntityMeta implements VersionableEntity {

	@NotNull
	private String name;
	
	@NotNull
	private String description;
	
	@NotNull
	private String color;
	
	private boolean systemDefault;
	
	private boolean byDefaultAssigned;
	
	private boolean superPrivileged;
	
}
