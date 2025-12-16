package software.blacknode.backend.domain.task;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import me.hinsinger.hinz.common.huid.HUID;
import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.modifier.create.Creatable;
import software.blacknode.backend.domain.modifier.create.meta.CreationMeta;
import software.blacknode.backend.domain.modifier.delete.Deletable;
import software.blacknode.backend.domain.modifier.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.modifier.modify.Modifiable;
import software.blacknode.backend.domain.modifier.modify.meta.ModificationMeta;
import software.blacknode.backend.domain.task.meta.TaskMeta;

public class Task implements Creatable, Modifiable, Deletable {

	@Getter private HUID id;
	@Getter private int priority;
	
	@Getter private HUID statusId;
	@Getter private HUID ownerId;
	
	@Getter private List<HUID> assignees;
	@Getter private List<HUID> attachments;
	
	@Getter private Timestamp beginAt;
	@Getter private Timestamp endAt;
	
	@Getter private TaskMeta meta;
	
	@Getter private Timestamp creationTimestamp;
	@Getter private Timestamp modificationTimestamp;
	@Getter private Timestamp deletationTimestamp;
	
	@Getter private HUID channelId;
	@Getter private HUID projectId;
	@Getter private HUID organizationId;
	
	@Override
	public void create(Optional<CreationMeta> meta0) {
		ensureNotCreated(meta0);
		ensureNotDeleted(meta0);
		ensureCreationMetaProvided(meta0);
		
		// TODO Auto-generated method stub
		
		creationTimestamp = Timestamp.now();
	}
	
	@Override
	public void modify(Optional<ModificationMeta> meta0) {
		ensureCreated(meta0);
		ensureNotDeleted(meta0);
		ensureModificationMetaProvided(meta0);
		
		modificationTimestamp = Timestamp.now();
	}
	
	@Override
	public void delete(Optional<DeletionMeta> meta0) {
		ensureCreated(meta0);
		ensureNotDeleted(meta0);
		ensureDeletionMetaProvided(meta0);
		
		deletationTimestamp = Timestamp.now();
	}

}
