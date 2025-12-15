package software.blacknode.backend.infrastructure.role;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.domain.role.Role;
import software.blacknode.backend.domain.role.repository.RoleRepository;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

	@Override
	public Optional<Role> findById(HUID organizationId, HUID id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<Role> findAllById(HUID organizationId, List<HUID> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Role> findAll(HUID organizationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(HUID organizationId, Role role) {
		// TODO Auto-generated method stub
		
	}

}
