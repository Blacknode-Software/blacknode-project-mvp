package software.blacknode.backend.application.task.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.access.impl.ChannelAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.task.TaskService;
import software.blacknode.backend.application.task.command.TaskCreateCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;
import software.blacknode.backend.domain.task.meta.create.impl.TaskDefaultCreationMeta;

@Service
@RequiredArgsConstructor
public class TaskCreateUseCase implements ResultExecutionUseCase<TaskCreateCommand, TaskCreateUseCase.Result> {

	private final ChannelAccessControl channelAccessControl;
	private final TaskService taskService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public Result execute(TaskCreateCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var channelId = command.getChannelId();
		
		channelAccessControl.ensureMemberHasChannelAccess(organizationId, memberId, 
				channelId, AccessLevel.WRITE);
		
		var title = command.getTitle();
		var description = command.getDescription();
		
		var priority = command.getPriority();
		
		var beginAtTimestamp = command.getBeginAt();
		var endAtTimestamp = command.getEndAt();
		
		var statusId = command.getStatusId();
		
		var meta = TaskDefaultCreationMeta.builder()
				.title(title)
				.description(description)
				.priority(priority)
				.beginAtTimestamp(beginAtTimestamp)
				.endAtTimestamp(endAtTimestamp)
				.statusId(statusId)
				.ownerMemberId(memberId)
				.channelId(channelId)
				.build();
		
		var task = taskService.create(organizationId, meta);

		
		return Result.builder()
				.taskId(task.getId())
				.build();
	}

	@Getter
	@Builder
	@ToString
	public static class Result {
		
		private final HUID taskId;
		
	}

}
