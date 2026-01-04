package software.blacknode.backend.infrastructure.task.entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.annotation.Nullable;
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
import software.blacknode.backend.infrastructure.organization.related.OrganizationRelatedEntity;
import software.blacknode.backend.infrastructure.task.entity.meta.TaskEntityMeta;

@Getter
@Entity
@SuperBuilder
@Table(name = "tasks")
@Access(AccessType.FIELD)
@NoArgsConstructor
@ToString
public class TaskEntity extends OrganizationRelatedEntity {

	@NotNull
	@Column(name = "meta")
	@VersionedEntity
	private TaskEntityMeta meta;
	
	@Nullable 
	@Column(name = "status_id", nullable = true)
	private UUID statusId;
	
	@NotNull
	@Column(name = "owner_member_id", nullable = false)
	private UUID ownerMemberId;
	
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
