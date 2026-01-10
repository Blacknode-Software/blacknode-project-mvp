package software.blacknode.backend.application.task.usecase;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.access.impl.ChannelAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.task.TaskService;
import software.blacknode.backend.application.task.command.TasksInChannelCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class TasksInChannelUseCase implements ResultExecutionUseCase<TasksInChannelCommand, TasksInChannelUseCase.Result> {
	
	private final ChannelAccessControl channelAccessControl;	
	
	private final TaskService taskService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public TasksInChannelUseCase.Result execute(TasksInChannelCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var channelId = command.getChannelId();
		
		channelAccessControl.ensureMemberHasChannelAccess(organizationId, memberId, 
				channelId, AccessLevel.READ);
		
		var tasks = taskService.getAllInChannel(organizationId, channelId);
		
		var tasksIds = tasks.stream()
				/* CURRENT VERSION: By default tasks are shown for all members of the channel - this check is not needed
				.filter(task -> accessControlService.hasAccessToTask(organizationId, memberId, task.getId(), 
						AccessControlService.AccessLevel.READ)) */
				.map(task -> task.getId())
				.toList();
		
		return Result.builder()
				.tasksIds(tasksIds)
				.build();
	}

	@Getter
	@Builder
	@ToString
	public static class Result {
		
		@NonNull
		private final List<HUID> tasksIds;
		
	}

}
