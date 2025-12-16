package software.blacknode.backend.domain.organization.repository;

import java.util.List;
import java.util.List;
import java.util.Optional;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.organization.Organization;

public interface OrganizationRepository {
	
	public Optional<Organization> findById(HUID id);

	public List<Organization> findAll();
	
	public void save(Organization organization);
}
