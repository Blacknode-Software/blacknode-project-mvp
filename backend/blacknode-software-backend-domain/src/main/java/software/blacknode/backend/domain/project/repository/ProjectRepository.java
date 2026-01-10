package software.blacknode.backend.domain.project.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Repository;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.project.Project;

@Repository
public interface ProjectRepository {

	Optional<Project> findById(HUID organizationId, HUID id);

	List<Project> findAllById(HUID organizationId, Set<HUID> ids);
	
	List<Project> findProjectInOrganization(HUID organizationId);
	
	void save(HUID organizationId, Project project);
}
