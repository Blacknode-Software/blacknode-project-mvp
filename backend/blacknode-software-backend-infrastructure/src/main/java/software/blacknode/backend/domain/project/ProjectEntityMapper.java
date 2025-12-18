package software.blacknode.backend.domain.project;

import org.springframework.stereotype.Component;

import me.hinsinger.hinz.common.huid.HUID;
import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.project.meta.ProjectMeta;
import software.blacknode.backend.infrastructure.entity.mapper.EntityMapper;
import software.blacknode.backend.infrastructure.project.entity.ProjectEntity;
import software.blacknode.backend.infrastructure.project.entity.meta.ProjectMetaEntity;

@Component
public class ProjectEntityMapper implements EntityMapper<Project, ProjectEntity>{

	@Override
	public Project toDomainEntity(ProjectEntity infrastructureEntity) {
		var id = uuidToHUID(infrastructureEntity.getId());
		var organizationId = uuidToHUID(infrastructureEntity.getOrganizationId());
		
		var meta = infrastructureEntity.getMeta();

		var name = meta.getName();
		var description = meta.getDescription();
		var color = meta.getColor();
	
		var domainMeta = ProjectMeta.builder()
				.name(name)
				.description(description)
				.color(color)
				.build();
		
		var creationTimestamp = instantToTimestamp(infrastructureEntity.getCreatedAt());
		var modificationTimestamp = instantToTimestamp(infrastructureEntity.getModifiedAt());
		var deletionTimestamp = instantToTimestamp(infrastructureEntity.getDeletedAt());
		
		return Project.builder()
				.id(id)
				.meta(domainMeta)
				.organizationId(organizationId)
				.creationTimestamp(creationTimestamp)
				.modificationTimestamp(modificationTimestamp)
				.deletationTimestamp(deletionTimestamp)
				.build();
	}

	@Override
	public ProjectEntity toInfrastructureEntity(Project domainEntity) {
		var id = huidToUUID(domainEntity.getId());
		var organizationId = huidToUUID(domainEntity.getOrganizationId());
		
		var meta = domainEntity.getMeta();

		var name = meta.getName();
		var description = meta.getDescription();
		var color = meta.getColor();
		
		var infrastructureMeta = ProjectMetaEntity.builder()
				.name(name)
				.description(description)
				.color(color)
				.build();
		
		var createdAt = timestampToInstant(domainEntity.getCreationTimestamp());
		var modifiedAt = timestampToInstant(domainEntity.getModificationTimestamp());
		var deletedAt = timestampToInstant(domainEntity.getDeletationTimestamp());
		
		return ProjectEntity.builder()
				.id(id)
				.organizationId(organizationId)
				.meta(infrastructureMeta)
				.createdAt(createdAt)
				.modifiedAt(modifiedAt)
				.deletedAt(deletedAt)
				.build();
	}

}
