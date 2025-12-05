package software.blacknode.backend.domain.role.repository;

import java.util.Optional;

import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.domain.role.Role;

public interface RoleRepository {

	Optional<Role> findById(HUID id);

	void save(Role role);
}
