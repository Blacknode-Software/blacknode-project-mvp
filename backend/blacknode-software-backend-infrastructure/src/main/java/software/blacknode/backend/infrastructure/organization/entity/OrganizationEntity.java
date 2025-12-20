package software.blacknode.backend.infrastructure.organization.entity;

import java.time.Instant;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.infrastructure.entity.impl.BaseEntity;
import software.blacknode.backend.infrastructure.entity.version.annotation.VersionedEntity;
import software.blacknode.backend.infrastructure.organization.entity.meta.OrganizationEntityMeta;
import software.blacknode.backend.infrastructure.organization.entity.settings.OrganizationEntitySettings;

@Getter
@Entity
@SuperBuilder
@Table(name = "organizations")
@Access(AccessType.FIELD)
public class OrganizationEntity extends BaseEntity {
	
	@NonNull
	@Column(name = "meta")
	@VersionedEntity
	private OrganizationEntityMeta meta;
	
//	@NonNull
//	@Column(name = "settings")
//	@VersionedEntity
//	private OrganizationEntitySettings settings;
	
	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;
	
	@Column(name = "modified_at")
	private Instant modifiedAt;
	
	@Column(name = "deleted_at")
	private Instant deletedAt;
}
