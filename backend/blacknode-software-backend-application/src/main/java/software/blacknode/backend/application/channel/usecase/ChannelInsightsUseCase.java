package software.blacknode.backend.application.channel.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import software.blacknode.backend.application.access.impl.ChannelAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.channel.ChannelService;
import software.blacknode.backend.application.channel.command.ChannelIsightsCommand;
import software.blacknode.backend.application.task.TaskService;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;
import software.blacknode.backend.domain.task.status.TaskStatus;

@Service
@RequiredArgsConstructor
public class ChannelInsightsUseCase implements ResultExecutionUseCase<ChannelIsightsCommand, ChannelInsightsUseCase.Result> {

	private final ChannelAccessControl channelAccessControl;
	
	private final ChannelService channelService;
	
	private final TaskService taskService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public Result execute(ChannelIsightsCommand command) {
		sessionContextHolder.ensureOrganizationScoped();
		
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var channelId = command.getChannelId();
		
		var channel = channelService.getOrThrow(organizationId, channelId);
		
		channelAccessControl.ensureMemberHasChannelAccess(
				memberId, channel, AccessLevel.READ);
		
		var tasks = taskService.getAllInChannel(organizationId, channelId);
		
		var totalTasksCount = tasks.size();
		
		var completedTasksCount = tasks.stream()
				.filter(task -> task.hasStatus(TaskStatus.STATUS_DONE))
				.count();
		
		var incompleteTasksCount = totalTasksCount - completedTasksCount;
	
		var overdueTasksCount = tasks.stream()
				.filter(task -> !task.hasStatus(TaskStatus.STATUS_DONE))
				.filter(task -> task.isOverdue())
				.count();
		
		return Result.builder()
				.tasksTotal(totalTasksCount)
				.tasksCompleted((int) completedTasksCount)
				.tasksIncomplete((int) incompleteTasksCount)
				.tasksOverdue((int) overdueTasksCount)
				.build();
	}

	@Getter
	@Builder
	@ToString
	public static class Result {

		private final int tasksTotal;
		private final int tasksCompleted;
		private final int tasksIncomplete;
		private final int tasksOverdue;
		
	}

	
	
}
