package software.blacknode.backend.domain.organization.repository;

import java.util.Optional;

import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.domain.organization.Organization;

public interface OrganizationRepository {

	Optional<Organization> findById(HUID id);

	void save(Organization organization);
}
