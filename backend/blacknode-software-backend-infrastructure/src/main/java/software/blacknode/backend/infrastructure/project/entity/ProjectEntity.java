package software.blacknode.backend.infrastructure.project.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.infrastructure.organization.related.OrganizationRelatedEntity;
import software.blacknode.backend.infrastructure.project.entity.meta.ProjectMetaEntity;

@Getter
@Entity
@SuperBuilder
@Table(name = "projects")
public class ProjectEntity extends OrganizationRelatedEntity {

	@NonNull
	@Column(name = "meta")
	private ProjectMetaEntity meta;
	
	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;
	
	@Column(name = "modified_at")
	private Instant modifiedAt;
	
	@Column(name = "deleted_at")
	private Instant deletedAt;
}
