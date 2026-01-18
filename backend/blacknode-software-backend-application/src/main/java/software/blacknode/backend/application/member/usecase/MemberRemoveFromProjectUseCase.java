package software.blacknode.backend.application.member.usecase;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import software.blacknode.backend.application.access.impl.ProjectAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.member.MemberService;
import software.blacknode.backend.application.member.command.MemberRemoveFromProjectCommand;
import software.blacknode.backend.application.project.ProjectService;
import software.blacknode.backend.application.shared.SharedDeletionService;
import software.blacknode.backend.application.usecase.ExecutionUseCase;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class MemberRemoveFromProjectUseCase implements ExecutionUseCase<MemberRemoveFromProjectCommand> {

	private final ProjectAccessControl projectAccessControl;
	
	private final ProjectService projectService;
	
	private final MemberService memberService;
	
	private final SharedDeletionService sharedDeletionService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public void execute(MemberRemoveFromProjectCommand command) {
		sessionContextHolder.ensureOrganizationScoped();
		
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var projectId = command.getProjectId();
		var project = projectService.getOrThrow(organizationId, projectId);
		
		var targetMemberId = command.getMemberId();
		var targetMember = memberService.getOrThrow(organizationId, targetMemberId);
		
		projectAccessControl.ensureMemberHasProjectAccess(memberId, project, AccessLevel.MANAGE);
		
		projectAccessControl.ensureMemberHasProjectAccess(targetMember, project, AccessLevel.READ);
		
		sharedDeletionService.deleteMemberFromProject(organizationId, targetMemberId, projectId);
	}
}
