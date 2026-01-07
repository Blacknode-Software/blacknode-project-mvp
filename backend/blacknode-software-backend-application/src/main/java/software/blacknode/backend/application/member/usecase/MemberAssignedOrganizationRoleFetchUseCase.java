package software.blacknode.backend.application.member.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import software.blacknode.backend.application.member.MemberService;
import software.blacknode.backend.application.member.association.MemberAssociationService;
import software.blacknode.backend.application.member.command.MemberAssignedOrganizationRoleFetchCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.member.association.MemberAssociation;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class MemberAssignedOrganizationRoleFetchUseCase implements ResultExecutionUseCase<MemberAssignedOrganizationRoleFetchCommand, MemberAssignedOrganizationRoleFetchUseCase.Result> {

	private final MemberAssociationService memberAssociationService;
	
	private final MemberService memberService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public Result execute(MemberAssignedOrganizationRoleFetchCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var assigneeId = command.getMemberId();
		var assignee = memberService.getOrThrow(organizationId, assigneeId);
		
		var association = memberAssociationService.getMemberOrganizationAssociationOrThrow(organizationId, assigneeId);
		
		return Result.builder()
				.association(association)
				.build();
	}
	
	@Getter
	@Builder
	@ToString
	public static class Result {
		
		@NonNull
		private final MemberAssociation association;
		
	}
}
