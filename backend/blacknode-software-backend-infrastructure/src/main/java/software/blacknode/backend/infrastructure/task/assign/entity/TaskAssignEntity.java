package software.blacknode.backend.infrastructure.task.assign.entity;

import java.time.Instant;

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
import software.blacknode.backend.infrastructure.task.assign.entity.meta.TaskAssignEntityMeta;

@Getter
@Entity
@SuperBuilder
@Table(name = "task_assigns")
@Access(AccessType.FIELD)
@NoArgsConstructor
@ToString
public class TaskAssignEntity extends OrganizationRelatedEntity {

	@NotNull
	@Column(name = "meta", length = 65_535)
	@VersionedEntity
	private TaskAssignEntityMeta meta;
	
	@NotNull
	@Column(name = "member_id", nullable = false)
	private HUID memberId;
	
	@NotNull
	@Column(name = "task_id", nullable = false)
	private HUID taskId;
	
	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;
	
	@Column(name = "deleted_at")
	private Instant deletedAt;
	
}
