package software.blacknode.backend.infrastructure.auth.entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.domain.auth.method.converter.model.AuthMethodSerializedModel;
import software.blacknode.backend.infrastructure.auth.entity.converter.AuthMethodSerializedModelConverter;
import software.blacknode.backend.infrastructure.auth.entity.meta.AuthEntityMeta;
import software.blacknode.backend.infrastructure.entity.impl.BaseInfrastructureEntity;
import software.blacknode.backend.infrastructure.entity.version.annotation.VersionedEntity;

@Getter
@Entity
@SuperBuilder
@Table(name = "auths")
@Access(AccessType.FIELD)
@NoArgsConstructor
public class AuthEntity extends BaseInfrastructureEntity {
	
	@NotNull
	@Column(name = "account_id", nullable = false)
	private UUID accountId;
	
	@NotNull
	@Column(name = "meta")
	@VersionedEntity
	private AuthEntityMeta meta;
	
	@NotNull
	@Column(name = "method", columnDefinition = "jsonb", nullable = false)
	@Convert(converter = AuthMethodSerializedModelConverter.class)
	private AuthMethodSerializedModel method;
	
	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;
	
	@Column(name = "modified_at")
	private Instant modifiedAt;
	
	@Column(name = "deleted_at")
	private Instant deletedAt;
}
