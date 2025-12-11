package software.blacknode.backend.application.role;

import java.util.List;
import java.util.Optional;

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
	
	public Role getByIdOrThrow(HUID roleId) {
		return repository.findById(roleId)
				.orElseThrow(() -> new RuntimeException("Role with ID " + roleId + " not found."));
	}
	
	public Optional<Role> getById(HUID roleId) {
		return repository.findById(roleId);
	}
	
	public List<Role> getRolesByIds(List<HUID> roleIds) {
		return repository.findAllById(roleIds);
	}
	
	public List<Role> getAllRoles() {
		return repository.findAll();
	}
	
	public Role create(CreationMeta meta) {
		var role = new Role();
		
		role.create(meta);
		
		repository.save(role);
		
		return role;
	}
}
