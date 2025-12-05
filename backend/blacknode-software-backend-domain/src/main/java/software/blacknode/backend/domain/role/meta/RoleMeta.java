package software.blacknode.backend.domain.role.meta;

import lombok.Getter;
import lombok.Setter;

public class RoleMeta {
	
	@Getter @Setter private Scope scope;
	
	public static enum Scope {
		GLOBAL,
		ORGANIZATION,
		PROJECT,
		CHANNEL
	}
	
}
