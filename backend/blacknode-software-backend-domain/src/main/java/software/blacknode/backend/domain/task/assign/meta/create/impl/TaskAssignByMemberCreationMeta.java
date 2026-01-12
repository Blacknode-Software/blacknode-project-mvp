package software.blacknode.backend.domain.task.assign.meta.create.impl;

import java.util.Optional;

import lombok.Builder;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.task.assign.meta.create.TaskAssignCreationMeta;

@Builder
@ToString
public class TaskAssignByMemberCreationMeta implements TaskAssignCreationMeta {

	private final HUID assignerId;
	private final HUID assigneeId;
	private final HUID taskId;
	
	@Override
	public HUID getMemberId() {
		return assigneeId;
	}
	
	@Override
	public HUID getTaskId() {
		return taskId;
	}
	
	@Override
	public Optional<HUID> getAssignerId() {
		return Optional.ofNullable(assignerId);
	}
	
}
