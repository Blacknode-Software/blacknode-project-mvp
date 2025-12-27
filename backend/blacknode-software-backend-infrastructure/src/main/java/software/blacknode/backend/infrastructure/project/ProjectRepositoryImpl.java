package software.blacknode.backend.infrastructure.project;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.project.Project;
import software.blacknode.backend.domain.project.repository.ProjectRepository;
import software.blacknode.backend.infrastructure.entity.state.EntityState;
import software.blacknode.backend.infrastructure.organization.related.repository.OrganizationRelatedEntityRepository;
import software.blacknode.backend.infrastructure.project.entity.ProjectEntity;
import software.blacknode.backend.infrastructure.project.entity.mapper.ProjectEntityMapper;
import software.blacknode.backend.infrastructure.project.entity.repository.ProjectEntityRepository;

@Repository
@RequiredArgsConstructor
public class ProjectRepositoryImpl implements ProjectRepository, OrganizationRelatedEntityRepository<Project, ProjectEntity> {

	private final ProjectEntityRepository repository;
	private final ProjectEntityMapper mapper;
	
	@Override
	public Optional<Project> findById(HUID organizationId, HUID id) {
		var project = repository.queryByIdAndOrganizationIdAndState(id.toUUID(), 
				organizationId.toUUID(), EntityState.ACTIVE);
		
		return project.map(this::toDomainEntity);
	}

	@Override
	public List<Project> findAllById(HUID organizationId, List<HUID> ids) {
		var uuidIds = ids.stream().map(HUID::toUUID).toList();
		
		var projects = repository.queryAllByIdInAndOrganizationIdAndState(uuidIds, 
				organizationId.toUUID(), EntityState.ACTIVE);
		
		return projects.stream()
				.map(this::toDomainEntity)
				.toList();
	}

	@Override
	public List<Project> findProjectInOrganization(HUID organizationId) {
		var projects = repository.queryAllByOrganizationIdAndState(organizationId.toUUID(), 
				EntityState.ACTIVE);
		
		return projects.stream()
				.map(this::toDomainEntity)
				.toList();
	}

	@Override
	public void save(HUID organizationId, Project project) {
		project.ensureBelongsToOrganization(organizationId);
		
		var projectEntity = toInfrastructureEntity(project);
		
		repository.save(projectEntity);
	}
	
	@Override
	public ProjectEntity toInfrastructureEntity(Project project) {
		return mapper.toInfrastructureEntity(project);
	}
	
	@Override
	public Project toDomainEntity(ProjectEntity projectEntity) {
		return mapper.toDomainEntity(projectEntity);
	}

}
