package software.blacknode.backend.infrastructure.project.entity;

import java.util.UUID;

import software.blacknode.backend.infrastructure.project.entity.meta.ProjectMetaEntity.ProjectMetaEntity_V001;

public class ProjectEntity {

	private UUID id;
	
	private String meta = new ProjectMetaEntity_V001().getClass().getSimpleName();
	
	private UUID organizationId;
	
}
