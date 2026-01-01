package software.blacknode.backend.infrastructure.channel.entity;

import java.time.Instant;
import java.util.UUID;

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
import software.blacknode.backend.infrastructure.channel.entity.meta.ChannelEntityMeta;
import software.blacknode.backend.infrastructure.entity.version.annotation.VersionedEntity;
import software.blacknode.backend.infrastructure.organization.related.OrganizationRelatedEntity;

@Getter
@Entity
@SuperBuilder
@Table(name = "channels")
@Access(AccessType.FIELD)
@NoArgsConstructor
@ToString
public class ChannelEntity extends OrganizationRelatedEntity {

	@NotNull
	@Column(name = "project_id", nullable = false)
	private UUID projectId;
	
	@NotNull
	@Column(name = "meta")
	@VersionedEntity
	private ChannelEntityMeta meta;
	
	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;
	
	@Column(name = "modified_at")
	private Instant modifiedAt;
	
	@Column(name = "deleted_at")
	private Instant deletedAt;
}
