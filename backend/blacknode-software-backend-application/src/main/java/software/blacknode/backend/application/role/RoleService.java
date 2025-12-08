package software.blacknode.backend.application.role;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.domain.modifier.create.meta.CreationMeta;
import software.blacknode.backend.domain.role.Role;
import software.blacknode.backend.domain.role.repository.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {

	private final RoleRepository repository;
	
	public Role getRoleOrThrow(HUID roleId) {
		return repository.findById(roleId)
				.orElseThrow(() -> new RuntimeException("Role with ID " + roleId + " not found."));
	}
	
	public Role create(CreationMeta meta) {
		var role = new Role();
		
		role.create(meta);
		
		repository.save(role);
		
		return role;
	}
}
