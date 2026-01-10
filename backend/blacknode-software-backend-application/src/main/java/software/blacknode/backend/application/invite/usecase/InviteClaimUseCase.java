package software.blacknode.backend.application.invite.usecase;

import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.account.AccountService;
import software.blacknode.backend.application.invite.InviteService;
import software.blacknode.backend.application.invite.command.InviteClaimCommand;
import software.blacknode.backend.application.member.MemberService;
import software.blacknode.backend.application.member.association.MemberAssociationService;
import software.blacknode.backend.application.role.RoleService;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.account.meta.create.impl.AccountInviteCreationMeta;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.member.association.meta.create.impl.MemberOrganizationAssociationCreationMeta;
import software.blacknode.backend.domain.member.meta.create.impl.MemberInviteCreationMeta;

@Service
@RequiredArgsConstructor
public class InviteClaimUseCase implements ResultExecutionUseCase<InviteClaimCommand, InviteClaimUseCase.Result> {

	private final AccountService accountService;
	
	private final InviteService inviteService;
	
	private final MemberService memberService;
	
	private final RoleService roleService;
	
	private final MemberAssociationService memberAssociationService;
	
	@Override
	public Result execute(InviteClaimCommand command) {
		var token = command.getToken();
		
		var invite = inviteService.getByToken(token).orElseThrow(() -> 
					new BlacknodeException("Invite not found for token: " + token));
		
		var organizationId = invite.getOrganizationId();
		var email = invite.getMeta().getEmail();
		
		if(invite.isClaimed()) {
			throw new BlacknodeException("Invite already claimed for token: " + token);
		}
		
		if(invite.isExpired()) {
			throw new BlacknodeException("Invite expired for token: " + token);
		}
		
		if(invite.isRevoked()) {
			throw new BlacknodeException("Invite revoked for token: " + token);
		}
		
		/* implement account linking later */
		if(accountService.getByEmail(email).isPresent()) {
			throw new BlacknodeException("Account already exists for email: " + email);
		}
		
		var firstName = command.getFirstName();
		var lastName = command.getLastName();
		
		var accountMeta = AccountInviteCreationMeta.builder()
				.email(email)
				.firstName(firstName)
				.lastName(lastName)
				.build();
		
		var account = accountService.create(accountMeta);
		
		var memberMeta = MemberInviteCreationMeta.builder()
				.accountId(account.getId())
				.build();
		
		var member = memberService.create(organizationId, memberMeta);
		
		var roles = roleService.getOrganizationRoles(organizationId);
		
		var defaultRole = roles.stream()
				.filter((r) -> r.getMeta().isByDefaultAssigned())
				.findFirst()
				.orElseThrow(() -> new BlacknodeException("Default role not found for organization: " + organizationId));
		
		var associationMeta = MemberOrganizationAssociationCreationMeta.builder()
				.organizationId(organizationId)
				.memberId(member.getId())
				.roleId(defaultRole.getId())
				.build();
		
		var association = memberAssociationService.create(organizationId, associationMeta);
		
		return Result.builder()
				.accountId(account.getId())
				.email(email)
				.build();
	}

	@Getter
	@Builder
	public static class Result {

		@NonNull private final HUID accountId;
		@NonNull private final String email;
		
	}

}
