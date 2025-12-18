package software.blacknode.backend.infrastructure.project;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.project.Project;
import software.blacknode.backend.domain.project.ProjectEntityMapper;
import software.blacknode.backend.domain.project.repository.ProjectRepository;
import software.blacknode.backend.infrastructure.project.entity.ProjectEntity;
import software.blacknode.backend.infrastructure.project.entity.ProjectEntityRepository;
import software.blacknode.backend.infrastructure.repository.InfrastructureRepository;

@Repository
@RequiredArgsConstructor
public class ProjectRepositoryImpl implements ProjectRepository, InfrastructureRepository<Project, ProjectEntity> {

	private final ProjectEntityMapper projectEntityMapper;
	private final ProjectEntityRepository projectEntityRepository;
	
	@Override
	public Optional<Project> findById(HUID organizationId, HUID id) {
		var projectEntityOpt = projectEntityRepository.findById(id);
		
		if(projectEntityOpt.isEmpty()) {
			return Optional.empty();
		}
		
		var projectEntity = projectEntityOpt.get();
		if(!projectEntity.getOrganizationId().equals(organizationId.toUUID())) {
			return Optional.empty();
		}
		
		var project = toDomainEntity(projectEntity);
		return Optional.of(project);
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
	
	@Override
	public ProjectEntity toInfrastructureEntity(Project project) {
		return projectEntityMapper.toInfrastructureEntity(project);
	}
	
	@Override
	public Project toDomainEntity(ProjectEntity projectEntity) {
		return projectEntityMapper.toDomainEntity(projectEntity);
	}

}
