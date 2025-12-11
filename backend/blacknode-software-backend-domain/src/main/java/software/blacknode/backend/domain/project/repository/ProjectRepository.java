package software.blacknode.backend.domain.project.repository;

import java.util.List;
import java.util.Optional;

import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.domain.project.Project;

public interface ProjectRepository {

	Optional<Project> findById(HUID id);

	List<Project> findAllById(List<HUID> ids);
	
	List<Project> findProjectInOrganization();
	
	void save(Project project);
}
