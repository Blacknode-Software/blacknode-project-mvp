package software.blacknode.backend.infrastructure.invite.entity;

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
import software.blacknode.backend.infrastructure.invite.entity.meta.InviteEntityMeta;
import software.blacknode.backend.infrastructure.organization.related.OrganizationRelatedEntity;

@Getter
@Entity
@SuperBuilder
@Table(name = "invites")
@Access(AccessType.FIELD)
@NoArgsConstructor
@ToString
public class InviteEntity extends OrganizationRelatedEntity {

	@NotNull
	@Column(name = "token", nullable = false, unique = true)
	private String token;
	
	@NotNull
	@Column(name = "meta", length = 65_535)
	@VersionedEntity
	private InviteEntityMeta meta;
	
	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;
	
	@Column(name = "modified_at")
	private Instant modifiedAt;
	
	@Column(name = "deleted_at")
	private Instant deletedAt;
	
}
