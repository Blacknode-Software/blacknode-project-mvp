package software.blacknode.backend.infrastructure.project;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.domain.project.Project;
import software.blacknode.backend.domain.project.repository.ProjectRepository;

@Repository
public class ProjectRepositoryImpl implements ProjectRepository {

	@Override
	public Optional<Project> findById(HUID organizationId, HUID id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<Project> findAllById(HUID organizationId, List<HUID> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> findProjectInOrganization(HUID organizationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(HUID organizationId, Project project) {
		// TODO Auto-generated method stub
		
	}

}
