package software.blacknode.backend.application.role;

import org.springframework.stereotype.Service;

import software.blacknode.backend.domain.modifier.create.meta.CreationMeta;
import software.blacknode.backend.domain.role.Role;

@Service
public class RoleService {

	public Role create(CreationMeta meta) {
		var role = new Role();
		
		role.create(meta);
		
		return role;
	}
}
