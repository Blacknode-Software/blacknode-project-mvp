package software.blacknode.backend.infrastructure.project.entity;

import java.time.Instant;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.infrastructure.entity.version.annotation.VersionedEntity;
import software.blacknode.backend.infrastructure.organization.related.OrganizationRelatedEntity;
import software.blacknode.backend.infrastructure.project.entity.meta.ProjectEntityMeta;

@Getter
@Entity
@SuperBuilder
@Table(name = "projects")
@Access(AccessType.FIELD)
@NoArgsConstructor
@ToString
public class ProjectEntity extends OrganizationRelatedEntity {

	@NotNull
	@Column(name = "meta", length = 65_535)
	@VersionedEntity
	private ProjectEntityMeta meta;
	
	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;
	
	@Column(name = "modified_at")
	private Instant modifiedAt;
	
	@Column(name = "deleted_at")
	private Instant deletedAt;
}
