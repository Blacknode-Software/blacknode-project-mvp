package software.blacknode.backend.application.task.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import software.blacknode.backend.application.access.impl.TaskAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.patch.impl.PatchOperationEnum;
import software.blacknode.backend.application.task.TaskService;
import software.blacknode.backend.application.task.command.TaskPatchCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;
import software.blacknode.backend.domain.task.Task;
import software.blacknode.backend.domain.task.meta.modify.impl.TaskBeginAtTimestampModificationMeta;
import software.blacknode.backend.domain.task.meta.modify.impl.TaskDescriptionModificationMeta;
import software.blacknode.backend.domain.task.meta.modify.impl.TaskPriorityModificationMeta;
import software.blacknode.backend.domain.task.meta.modify.impl.TaskStatusIdModificationMeta;
import software.blacknode.backend.domain.task.meta.modify.impl.TaskTitleModificationMeta;

@Service
@RequiredArgsConstructor
public class TaskPatchUseCase implements ResultExecutionUseCase<TaskPatchCommand, TaskPatchUseCase.Result> {
	
	private final TaskAccessControl taskAccessControl;
	private final TaskService taskService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public Result execute(TaskPatchCommand command) {
		sessionContextHolder.ensureOrganizationScoped();
		
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var taskId = command.getId();
		
		taskAccessControl.ensureMemberHasTaskAccess(organizationId, memberId, 
				taskId, AccessLevel.WRITE);
		
		var operations = command.getOperations();
		
		var modifications = ModificationMeta.emptyList();
		
		if(TaskPatchOperation.TITLE.isIn(operations)) {
			var title = command.getTitle();
			
			var meta = TaskTitleModificationMeta.builder()
					.title(title)
					.build();
			
			modifications.add(meta);
		}
		
		if(TaskPatchOperation.DESCRIPTION.isIn(operations)) {
			var description = command.getDescription();
			var meta = TaskDescriptionModificationMeta.builder()
					.description(description)
					.build();
			
			modifications.add(meta);
		}
		
		if(TaskPatchOperation.PRIORITY.isIn(operations)) {
			var priority = command.getPriority();
			
			var meta = TaskPriorityModificationMeta.builder()
					.priority(priority)
					.build();
			
			modifications.add(meta);
		}
		
		if(TaskPatchOperation.BEGIN_AT_TIMESTAMP.isIn(operations)) {
			var beginAtTimestamp = command.getBeginAt();
			
			var meta = TaskBeginAtTimestampModificationMeta.builder()
					.beginAt(beginAtTimestamp)
					.build();
			
			modifications.add(meta);
		}
		
		if(TaskPatchOperation.END_AT_TIMESTAMP.isIn(operations)) {
			var endAtTimestamp = command.getEndAt();
			
			var meta = TaskBeginAtTimestampModificationMeta.builder()
					.beginAt(endAtTimestamp)
					.build();
			
			modifications.add(meta);
		}
		
		if(TaskPatchOperation.STATUS_ID.isIn(operations)) {
			var statusId = command.getStatusId();
			
			var meta = TaskStatusIdModificationMeta.builder()
					.statusId(statusId)
					.build();
			
			modifications.add(meta);
		}
		
		var task = taskService.modify(organizationId, taskId, modifications);
		
		return Result.builder()
				.task(task)
				.build();
	}
	
	@Getter
	@Builder
	@ToString
	public static class Result {
		
		private final Task task;
		
	}
	
	public static enum TaskPatchOperation implements PatchOperationEnum {
		
		TITLE,
		DESCRIPTION,
		PRIORITY,
		
		BEGIN_AT_TIMESTAMP,
		END_AT_TIMESTAMP,
		
		STATUS_ID,
		
	}


}
