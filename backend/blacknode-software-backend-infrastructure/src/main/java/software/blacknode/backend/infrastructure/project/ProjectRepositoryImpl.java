package software.blacknode.backend.infrastructure.project;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.project.Project;
import software.blacknode.backend.domain.project.ProjectEntityMapper;
import software.blacknode.backend.domain.project.repository.ProjectRepository;
import software.blacknode.backend.infrastructure.entity.state.EntityState;
import software.blacknode.backend.infrastructure.organization.related.repository.OrganizationRelatedEntityRepository;
import software.blacknode.backend.infrastructure.project.entity.ProjectEntity;
import software.blacknode.backend.infrastructure.project.entity.repository.ProjectEntityRepository;

@Repository
@RequiredArgsConstructor
public class ProjectRepositoryImpl implements ProjectRepository, OrganizationRelatedEntityRepository<Project, ProjectEntity> {

	private final ProjectEntityMapper projectEntityMapper;
	private final ProjectEntityRepository projectEntityRepository;
	
	@Override
	public Optional<Project> findById(HUID organizationId, HUID id) {
		var projectEntityOpt = projectEntityRepository.findByIdAndOrganizationIdAndState(id.toUUID(), 
				organizationId.toUUID(), EntityState.ACTIVE);
		
		return projectEntityOpt.map(this::toDomainEntity);
	}

	@Override
	public List<Project> findAllById(HUID organizationId, List<HUID> ids) {
		var uuidIds = ids.stream().map(HUID::toUUID).toList();
		
		var projectEntities = projectEntityRepository.findAllByIdInAndOrganizationIdAndState(uuidIds, 
				organizationId.toUUID(), EntityState.ACTIVE);
		
		return projectEntities.stream()
				.map(this::toDomainEntity)
				.toList();
	}

	@Override
	public List<Project> findProjectInOrganization(HUID organizationId) {
		var projectEntities = projectEntityRepository.findAllByOrganizationIdAndState(organizationId.toUUID(), 
				EntityState.ACTIVE);
		
		return projectEntities.stream()
				.map(this::toDomainEntity)
				.toList();
	}

	@Override
	public void save(HUID organizationId, Project project) {
		project.belongsToOrganization(organizationId);
		
		var projectEntity = toInfrastructureEntity(project);
		
		projectEntityRepository.save(projectEntity);
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
