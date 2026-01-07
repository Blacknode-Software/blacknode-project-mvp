package software.blacknode.backend.infrastructure.account.entity;

import java.sql.Timestamp;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.infrastructure.account.entity.meta.AccountEntityMeta;
import software.blacknode.backend.infrastructure.entity.impl.BaseInfrastructureEntity;
import software.blacknode.backend.infrastructure.entity.version.annotation.VersionedEntity;

@Getter
@Entity
@SuperBuilder
@Table(name = "accounts")
@NoArgsConstructor
@ToString
public class AccountEntity extends BaseInfrastructureEntity {

	@Column(name = "email", nullable = false, unique = true)
	private String email;
	
	@NotNull
	@Column(name = "meta", length = 65_535)
	@VersionedEntity
	private AccountEntityMeta meta;
	
	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;
	
	@Column(name = "modified_at")
	private Instant modifiedAt;
	
	@Column(name = "deleted_at")
	private Instant deletedAt;
	
}
