package software.blacknode.backend.infrastructure.member.association.entity;

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
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.infrastructure.entity.version.annotation.VersionedEntity;
import software.blacknode.backend.infrastructure.member.association.entity.meta.MemberAssociationEntityMeta;
import software.blacknode.backend.infrastructure.organization.related.OrganizationRelatedEntity;

@Getter
@Entity
@SuperBuilder
@Table(name = "member_associations")
@Access(AccessType.FIELD)
@NoArgsConstructor
@ToString
public class MemberAssociationEntity extends OrganizationRelatedEntity {

	@NotNull
	@Column(name = "meta")
	@VersionedEntity
	private MemberAssociationEntityMeta meta;
	
	@NotNull
	@Column(name = "member_id", nullable = false)
	private UUID memberId;
	
	@NotNull
	@Column(name = "entity_id", nullable = false)
	private UUID entityId;
	
	@NotNull
	@Column(name = "role_id", nullable = false)
	private UUID roleId;
	
	@NotNull
	@Column(name = "scope", nullable = false)
	private String scope;
	
	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;
	
	@Column(name = "deleted_at")
	private Instant deletedAt;
	
}
