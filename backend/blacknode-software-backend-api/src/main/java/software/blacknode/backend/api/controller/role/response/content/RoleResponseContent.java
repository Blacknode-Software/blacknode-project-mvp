package software.blacknode.backend.api.controller.role.response.content;

import java.util.UUID;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class RoleResponseContent {

	private UUID id;
	
	private String name;
	
	private String description;
	
	private String color;
	
	private boolean isByDefault;
	
	private boolean isSystemRole;
	
	private boolean isSuperPrivileged;
	
}
