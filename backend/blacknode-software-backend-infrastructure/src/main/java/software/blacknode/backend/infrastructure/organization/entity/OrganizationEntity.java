package software.blacknode.backend.infrastructure.organization.entity;

import java.time.Instant;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.infrastructure.entity.impl.BaseInfrastructureEntity;
import software.blacknode.backend.infrastructure.entity.version.annotation.VersionedEntity;
import software.blacknode.backend.infrastructure.organization.entity.meta.OrganizationEntityMeta;

@Getter
@Entity
@SuperBuilder
@Table(name = "organizations")
@Access(AccessType.FIELD)
@NoArgsConstructor
@ToString
public class OrganizationEntity extends BaseInfrastructureEntity {
	
	@NonNull
	@Column(name = "meta", length = 65_535)
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
