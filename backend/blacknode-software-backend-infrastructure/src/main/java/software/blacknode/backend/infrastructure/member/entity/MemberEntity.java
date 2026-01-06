package software.blacknode.backend.infrastructure.member.entity;

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
import software.blacknode.backend.infrastructure.member.entity.meta.MemberEntityMeta;
import software.blacknode.backend.infrastructure.organization.related.OrganizationRelatedEntity;

@Getter
@Entity
@SuperBuilder
@Table(name = "members")
@Access(AccessType.FIELD)
@NoArgsConstructor
@ToString 
public class MemberEntity extends OrganizationRelatedEntity {

	@NotNull
	@Column(name = "meta")
	@VersionedEntity
	private MemberEntityMeta meta;
	
	@NotNull
	@Column(name = "account_id")
	private UUID accountId;
	
	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;
	
	@Column(name = "modified_at")
	private Instant modifiedAt;
	
	@Column(name = "deleted_at")
	private Instant deletedAt;
	
}
