package software.blacknode.backend.application.member.usecase;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.access.AccessControlService;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.member.MemberService;
import software.blacknode.backend.application.member.command.MembersInProjectCommand;
import software.blacknode.backend.application.project.ProjectService;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class MembersInProjectUseCase implements ResultExecutionUseCase<MembersInProjectCommand, MembersInProjectUseCase.Result> {

	private final AccessControlService accessControlService;
	
	private final MemberService memberService;
	
	private final ProjectService projectService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public Result execute(MembersInProjectCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
	
		var projectId = command.getProjectId();
		var project = projectService.getOrThrow(organizationId, projectId);
		
		accessControlService.ensureMemberHasProjectAccess(memberId, project, AccessLevel.READ);
		
		var members = memberService.getAll(organizationId)
				.stream()
				.filter(m -> accessControlService.hasAccessToProject(
						m, project, AccessLevel.READ))
				.map(member -> member.getId())
				.toList();
		
		return Result.builder()
				.memberIds(members)
				.build();
	}
	
	@Getter
	@Builder
	@ToString
	public static class Result {
		
		@NonNull
		private final List<HUID> memberIds;
		
	}

}
