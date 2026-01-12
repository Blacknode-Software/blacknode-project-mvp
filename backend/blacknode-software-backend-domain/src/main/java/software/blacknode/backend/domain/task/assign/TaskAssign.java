package software.blacknode.backend.domain.task.assign;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;
import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.entity.DomainEntity;
import software.blacknode.backend.domain.entity.modifier.impl.create.Creatable;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;
import software.blacknode.backend.domain.entity.modifier.impl.delete.Deletable;
import software.blacknode.backend.domain.entity.modifier.impl.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.task.assign.meta.TaskAssignMeta;
import software.blacknode.backend.domain.task.assign.meta.create.TaskAssignCreationMeta;

@Builder
@AllArgsConstructor(onConstructor = @__({ @Deprecated }))
@ToString
public class TaskAssign implements DomainEntity, Creatable, Deletable {

	@Getter private HUID id;
	
	@Getter private TaskAssignMeta meta;
	
	@Getter private HUID taskId;
	@Getter private HUID memberId;
	
	@Getter private Timestamp creationTimestamp;
	@Getter private Timestamp deletionTimestamp;
	
	@Getter private HUID organizationId;
	
	public TaskAssign(HUID organizationId) {
		this.organizationId = organizationId;
	}
	
	public void create(Optional<CreationMeta> meta0) {
		ensureNotCreated(meta0);
		ensureNotDeleted(meta0);
		ensureCreationMetaProvided(meta0);
		
		this.id = HUID.random();
		
		var meta = meta0.get();
		
		if(meta instanceof TaskAssignCreationMeta _meta) {
			@NonNull var taskId = _meta.getTaskId();
			@NonNull var memberId = _meta.getMemberId();
			
			var assignerId = Optional.<HUID>empty();
			
			assignerId = _meta.getAssignerId();
			
			this.taskId = taskId;
			this.memberId = memberId;
			
			this.meta = TaskAssignMeta.builder()
					.assignerId(assignerId)
					.build();
		} else throwUnsupportedCreationMeta(meta0);
		
		this.creationTimestamp = Timestamp.now();
	}
	
	public void delete(Optional<DeletionMeta> meta0) {
		ensureCreated(meta0);
		ensureNotDeleted(meta0);
		ensureDeletionMetaProvided(meta0);
		
		var meta = meta0.get();
		
		if(meta instanceof TaskAssignCreationMeta _meta) {
			// No specific deletion logic for now
		} else throwUnsupportedDeletionMeta(meta);
		
		this.deletionTimestamp = Timestamp.now();
	}
	
	public void ensureBelongsToOrganization(HUID organizationId) {
		if(!this.organizationId.equals(organizationId)) {
			throw new BlacknodeException("Task assign does not belong to the specified organization.");
		}
	}
	
	public boolean belongsToOrganization(HUID organizationId) {
		return this.organizationId.equals(organizationId);
	}
}
