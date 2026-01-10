package software.blacknode.backend.domain.task;

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
import software.blacknode.backend.domain.entity.modifier.impl.modify.Modifiable;
import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.task.meta.TaskMeta;
import software.blacknode.backend.domain.task.meta.create.TaskCreationMeta;
import software.blacknode.backend.domain.task.meta.delete.TaskDeletionMeta;
import software.blacknode.backend.domain.task.meta.modify.TaskModificationMeta;

@Builder
@AllArgsConstructor(onConstructor = @__({ @Deprecated }))
@ToString
public class Task implements DomainEntity, Creatable, Modifiable, Deletable {

	@Getter private HUID id;
	
	@Getter private TaskMeta meta;
	
	@Getter private Timestamp creationTimestamp;
	@Getter private Timestamp modificationTimestamp;
	@Getter private Timestamp deletionTimestamp;
	
	@Getter private Optional<HUID> statusId;
	
	@Getter private HUID channelId;
	@Getter private HUID ownerMemberId;
	@Getter private final HUID organizationId;
	
	public Task(HUID organizationId) {
		this.organizationId = organizationId;
	}
	
	@Override
	public void create(Optional<CreationMeta> meta0) {
		ensureNotCreated(meta0);
		ensureNotDeleted(meta0);
		ensureCreationMetaProvided(meta0);
		
		this.id = HUID.random();
		
		var meta = meta0.get();
		
		if(meta instanceof TaskCreationMeta _meta) {
			@NonNull var channelId = _meta.getChannelId();
			@NonNull var ownerMemberId = _meta.getOwnerMemberId();
			
			var status = _meta.getStatusId();
			
			var title = _meta.getTitle().orElse("Untitled Task");
			var description = _meta.getDescription().orElse("");
			
			var priority = _meta.getPriority();
			
			// TODO validate timestamps (beginAt <= endAt)
			var beginAt = _meta.getBeginAtTimestamp();
			var endAt = _meta.getEndAtTimestamp();
			
			this.channelId = channelId;
			this.ownerMemberId = ownerMemberId;
			
			this.statusId = status;
			
			this.meta = TaskMeta.builder()
					.title(title)
					.description(description)
					.priority(priority)
					.beginAt(beginAt)
					.endAt(endAt)
					.build();
			
		} else throwUnsupportedCreationMeta(meta);
		
		creationTimestamp = Timestamp.now();
	}
	
	@Override
	public void modify(Optional<ModificationMeta> meta0) {
		ensureCreated(meta0);
		ensureNotDeleted(meta0);
		ensureModificationMetaProvided(meta0);
		
		var meta = meta0.get();
		
		if(meta instanceof TaskModificationMeta _meta) {
			var updated = this.meta;
			
			updated = _meta.getTitle().map(updated::withTitle).orElse(updated);
		    updated = _meta.getDescription().map(updated::withDescription).orElse(updated);
		    
		    if(_meta.isPrioritySet()) {
		    	updated = updated.withPriority(_meta.getPriority());
		    }
		    
		    if(_meta.isBeginAtTimestampSet()) {
		    	updated = updated.withBeginAt(_meta.getBeginAtTimestamp());
		    }
		    
		    if(_meta.isEndAtTimestampSet()) {
		    	updated = updated.withEndAt(_meta.getEndAtTimestamp());
		    }
		    
		    this.statusId = _meta.getStatusId().or(() -> this.statusId);
		    
		    this.meta = updated;
		    
		} else throwUnsupportedModificationMeta(meta);
		
		modificationTimestamp = Timestamp.now();
	}
	
	@Override
	public void delete(Optional<DeletionMeta> meta0) {
		ensureCreated(meta0);
		ensureNotDeleted(meta0);
		ensureDeletionMetaProvided(meta0);
		
		var meta = meta0.get();
		
		if(meta instanceof TaskDeletionMeta) {
			// No specific deletion meta for Task yet
		} else throwUnsupportedDeletionMeta(meta);
		
		deletionTimestamp = Timestamp.now();
	}

	public void ensureBelongsToOrganization(HUID organizationId) {
		if(!belognsToOrganization(organizationId)) {
			throw new BlacknodeException("Task does not belong to organization with ID: " + organizationId);
		}
	}
	
	public boolean belognsToOrganization(HUID organizationId) {
		return this.organizationId.equals(organizationId);
	}
	
	public void ensureOwnedByMember(HUID memberId) {
		if(!isOwnedByMember(memberId)) {
			throw new BlacknodeException("Task is not owned by member with ID: " + memberId);
		}
	}
	
	public boolean isOwnedByMember(HUID memberId) {
		return this.ownerMemberId.equals(memberId);
	}
}
