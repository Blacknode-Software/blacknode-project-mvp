package software.blacknode.backend.domain.role.repository;

import java.util.List;
import java.util.Optional;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.role.Role;

public interface RoleRepository {

	Optional<Role> findById(HUID organizationId, HUID id);
	
	List<Role> findAllById(HUID organizationId, List<HUID> ids);
	
	List<Role> findAll(HUID organizationId);

	void save(HUID organizationId, Role role);
}
