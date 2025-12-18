package software.blacknode.backend.infrastructure.organization;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.organization.Organization;
import software.blacknode.backend.domain.organization.repository.OrganizationRepository;

@Repository
public class OrganizationRepositoryImpl implements OrganizationRepository {

	@Override
	public Optional<Organization> findById(HUID id) {
		
		return Optional.empty();
	}

	@Override
	public void save(Organization organization) {
		
	}

	@Override
	public List<Organization> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
