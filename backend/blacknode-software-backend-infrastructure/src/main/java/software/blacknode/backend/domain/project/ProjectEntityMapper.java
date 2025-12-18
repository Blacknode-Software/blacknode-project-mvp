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
		var id = infrastructureEntity.getId();
		var organizationId = infrastructureEntity.getOrganizationId();
		
		var meta = infrastructureEntity.getMeta();

		var name = meta.getName();
		var description = meta.getDescription();
		var color = meta.getColor();
	
		var createdAt = infrastructureEntity.getCreatedAt();
		var modfiedAt = infrastructureEntity.getModifiedAt();
		var deletedAt = infrastructureEntity.getDeletedAt();
		
		var domainMeta = ProjectMeta.builder()
				.name(name)
				.description(description)
				.color(color)
				.build();
		
		return Project.builder()
				.id(HUID.fromUUID(id))
				.meta(domainMeta)
				.organizationId(HUID.fromUUID(organizationId))
				.modificationTimestamp(Timestamp.fromDate(modfiedAt))
				.build();
	}

	@Override
	public ProjectEntity toInfrastructureEntity(Project domainEntity) {
		var id = domainEntity.getId();
		var organizationId = domainEntity.getOrganizationId();
		
		var meta = domainEntity.getMeta();

		var name = meta.getName();
		var description = meta.getDescription();
		var color = meta.getColor();
		
		var infrastructureMeta = ProjectMetaEntity.builder()
				.name(name)
				.description(description)
				.color(color)
				.build();
		
		return ProjectEntity.builder()
				.id(id.toUUID())
				.organizationId(organizationId.toUUID())
				.meta(infrastructureMeta)
				.build();
	}

}
