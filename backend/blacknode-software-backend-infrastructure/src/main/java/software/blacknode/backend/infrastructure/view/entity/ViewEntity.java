package software.blacknode.backend.infrastructure.view.entity;

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
import software.blacknode.backend.infrastructure.entity.version.annotation.VersionedEntity;
import software.blacknode.backend.infrastructure.organization.related.OrganizationRelatedEntity;
import software.blacknode.backend.infrastructure.view.entity.meta.ViewEntityMeta;

@Getter
@Entity
@SuperBuilder
@Table(name = "views")
@Access(AccessType.FIELD)
@NoArgsConstructor
@ToString
public class ViewEntity extends OrganizationRelatedEntity {

	@NotNull
	@Column(name = "meta", length = 65_535)
	@VersionedEntity
	private ViewEntityMeta meta;
	
	@NotNull
	@Column(name = "channel_id", nullable = false)
	private UUID channelId;
	
	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;
	
	@Column(name = "modified_at")
	private Instant modifiedAt;
	
	@Column(name = "deleted_at")
	private Instant deletedAt;
}
