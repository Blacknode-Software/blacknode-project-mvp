package software.blacknode.backend.application.role;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;
import software.blacknode.backend.domain.entity.modifier.impl.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.role.Role;
import software.blacknode.backend.domain.role.meta.delete.impl.RoleDefaultDeletionMeta;
import software.blacknode.backend.domain.role.repository.RoleRepository;

@Transactional
@Service
@RequiredArgsConstructor
public class RoleService {

	private final RoleRepository repository;
	
	public Role create(HUID organizationId, CreationMeta meta) {
		var role = new Role(organizationId);
		
		role.create(meta);
		
		repository.save(organizationId, role);
		
		return role;
	}
	
	
	public Role modify(HUID organizationId, HUID roleId, ModificationMeta meta) {
		return modify(organizationId, roleId, List.of(meta));
	}
	public Role modify(HUID organizationId, HUID roleId, List<ModificationMeta> metas) {
		var role = getOrThrow(organizationId, roleId);
		
		for(var meta : metas) {
			role.modify(meta);
		}
		
		repository.save(organizationId, role);
		
		return role;
	}
	
	public void delete(HUID organizationId, HUID roleId) {
		var meta = RoleDefaultDeletionMeta.builder().build();
		
		delete(organizationId, roleId, meta);
	}
	
	public void delete(HUID organizationId, HUID roleId, DeletionMeta meta) {
		var role = getOrThrow(organizationId, roleId);
		
		role.delete(meta);
		
		repository.save(organizationId, role);
	}
	
	public Role getOrThrow(HUID organizationId, HUID roleId) {
		return repository.findById(organizationId, roleId)
				.orElseThrow(() -> new BlacknodeException("Role with ID " + roleId + " not found."));
	}
	
	public Optional<Role> get(HUID organizationId, HUID roleId) {
		return repository.findById(organizationId, roleId);
	}
	
	public List<Role> getByIds(HUID organizationId, List<HUID> roleIds) {
		return getByIds(organizationId, Set.copyOf(roleIds));
	}
	
	public List<Role> getByIds(HUID organizationId, Set<HUID> roleIds) {
		return repository.findAllById(organizationId, roleIds);
	}
	
	public List<Role> getAll(HUID organizationId) {
		return repository.findAll(organizationId);
	}
	
	public List<Role> getOrganizationRoles(HUID organizationId) {
		return getRolesOfScope(organizationId, Role.Scope.ORGANIZATION);
	}
	
	public List<Role> getProjectRoles(HUID organizationId) {
		return getRolesOfScope(organizationId, Role.Scope.PROJECT);
	}
	
	public List<Role> getChannelRoles(HUID organizationId) {
		return getRolesOfScope(organizationId, Role.Scope.CHANNEL);
	}
	
	public List<Role> getRolesOfScope(HUID organizationId, Role.Scope scope) {
		var roles = repository.findAll(organizationId).stream()
				.filter(role -> role.getScope() == scope)
				.toList();
		
		return roles;
	}
	
	
}
