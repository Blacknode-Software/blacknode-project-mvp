package software.blacknode.backend.api.controller.invite;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.api.controller.annotation.BearerAuth;
import software.blacknode.backend.api.controller.invite.mapper.impl.InviteClaimMapper;
import software.blacknode.backend.api.controller.invite.mapper.impl.InviteCreateMapper;
import software.blacknode.backend.api.controller.invite.mapper.impl.InviteFetchMapper;
import software.blacknode.backend.api.controller.invite.mapper.impl.InvitePreClaimFetchMapper;
import software.blacknode.backend.api.controller.invite.mapper.impl.InvitesBatchFetchMapper;
import software.blacknode.backend.api.controller.invite.mapper.impl.InvitesListMapper;
import software.blacknode.backend.api.controller.invite.request.InviteClaimRequest;
import software.blacknode.backend.api.controller.invite.request.InviteCreateRequest;
import software.blacknode.backend.api.controller.invite.request.InvitesBatchFetchRequest;
import software.blacknode.backend.api.controller.invite.response.InviteCreateResponse;
import software.blacknode.backend.api.controller.invite.response.InvitePreClaimFetchResponse;
import software.blacknode.backend.api.controller.invite.response.InviteResponse;
import software.blacknode.backend.api.controller.invite.response.InvitesBatchFetchResponse;
import software.blacknode.backend.api.controller.invite.response.InvitesListResponse;
import software.blacknode.backend.api.controller.organization.annotation.OrganizationHeader;
import software.blacknode.backend.api.controller.response.impl.SuccessResponse;
import software.blacknode.backend.application.invite.command.InviteDeleteCommand;
import software.blacknode.backend.application.invite.command.InviteFetchCommand;
import software.blacknode.backend.application.invite.command.InvitePreClaimFetchCommand;
import software.blacknode.backend.application.invite.command.InviteRevokeCommand;
import software.blacknode.backend.application.invite.command.InvitesInOrganizationCommand;
import software.blacknode.backend.application.invite.usecase.InviteClaimUseCase;
import software.blacknode.backend.application.invite.usecase.InviteCreateUseCase;
import software.blacknode.backend.application.invite.usecase.InviteDeleteUseCase;
import software.blacknode.backend.application.invite.usecase.InviteFetchUseCase;
import software.blacknode.backend.application.invite.usecase.InvitePreClaimFetchUseCase;
import software.blacknode.backend.application.invite.usecase.InviteRevokeUseCase;
import software.blacknode.backend.application.invite.usecase.InvitesBatchFetchUseCase;
import software.blacknode.backend.application.invite.usecase.InvitesInOrganizationUseCase;

@RestController
@RequiredArgsConstructor
@Tag(name = "Invites", description = "Invite management API")
public class InviteController {

	private final InviteFetchMapper inviteFetchMapper;
	private final InviteFetchUseCase inviteFetchUseCase;
	
	private final InviteCreateMapper inviteCreateMapper;
	private final InviteCreateUseCase inviteCreateUseCase;
	
	private final InvitesBatchFetchMapper invitesBatchFetchMapper;
	private final InvitesBatchFetchUseCase invitesBatchFetchUseCase;
	
	private final InvitesListMapper invitesListMapper;
	private final InvitesInOrganizationUseCase invitesInOrganizationUseCase;
	
	private final InviteDeleteUseCase inviteDeleteUseCase;
	
	private final InviteRevokeUseCase inviteRevokeUseCase;
	
	private final InvitePreClaimFetchMapper invitePreClaimFetchMapper;
	private final InvitePreClaimFetchUseCase invitePreClaimFetchUseCase;
	
	private final InviteClaimUseCase inviteClaimUseCase;
	private final InviteClaimMapper inviteClaimMapper;
	
	@BearerAuth
	@OrganizationHeader
	@Operation(summary = "Fetch Invite", description = "Fetch an invite by its ID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Invite fetched") })
	@GetMapping("/invites/{id}")
	public ResponseEntity<InviteResponse> getInvite(@PathVariable("id") UUID id) {
		var command = InviteFetchCommand.builder()
				.inviteId(HUID.fromUUID(id))
				.build();
		
		var result = inviteFetchUseCase.execute(command);
		
		var response = inviteFetchMapper.toResponse(result);
		
		return response.toOkResponse("Invite fetched successfully.");
	}
	
	@BearerAuth
	@OrganizationHeader
	@Operation(summary = "Create Invite", description = "Create a new invite.")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Invite created") })
	@PostMapping("/organization/invites")
	public ResponseEntity<InviteCreateResponse> createInvite(@RequestBody InviteCreateRequest request) {
		var command = inviteCreateMapper.toCommand(request);
		
		var result = inviteCreateUseCase.execute(command);
		
		var response = inviteCreateMapper.toResponse(result);
		
		return response.toSuccessResponse("Invite created successfully.", HttpStatus.CREATED);
	}
	
	@BearerAuth
	@OrganizationHeader
	@Operation(summary = "Batch Fetch Invites", description = "Fetch multiple invites by their IDs.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Invites fetched") })
	@PostMapping("/invites/batch-fetch")
	public ResponseEntity<InvitesBatchFetchResponse> batchFetchInvites(@RequestBody InvitesBatchFetchRequest request) {
		var command = invitesBatchFetchMapper.toCommand(request);
		
		var result = invitesBatchFetchUseCase.execute(command);
		
		var response = invitesBatchFetchMapper.toResponse(result);
		
		return response.toOkResponse("Invites fetched successfully.");
	}
	
	@BearerAuth
	@OrganizationHeader
	@Operation(summary = "Get Organization Invites", description = "Fetch all invites for the current organization.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Organization invites fetched") })
	@GetMapping("/organization/invites")
	public ResponseEntity<InvitesListResponse> getOrganizationInvites() {
		var command = InvitesInOrganizationCommand.builder()
				.build();
		
		var result = invitesInOrganizationUseCase.execute(command);
		
		var response = invitesListMapper.toResponse(result);
		
		return response.toOkResponse("Organization invites fetched successfully.");
	}
	
	@BearerAuth
	@OrganizationHeader
	@Operation(summary = "Delete Invite", description = "Delete an invite by its ID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Invite deleted") })
	@DeleteMapping("/invites/{id}")
	public ResponseEntity<SuccessResponse> deleteInvite(@PathVariable("id") UUID id) {
		var command = InviteDeleteCommand.builder()
				.inviteId(HUID.fromUUID(id))
				.build();
		
		inviteDeleteUseCase.execute(command);
		
		return SuccessResponse.with("Invite deleted successfully.");
	}
	
	@BearerAuth
	@OrganizationHeader
	@Operation(summary = "Revoke Invite", description = "Revoke an invite by its ID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Invite revoked") })
	@PostMapping("/invites/{id}/revoke")
	public ResponseEntity<SuccessResponse> revokeInvite(@PathVariable("id") UUID id) {
		var command = InviteRevokeCommand.builder()
				.inviteId(HUID.fromUUID(id))
				.build();
		
		inviteRevokeUseCase.execute(command);
		
		return SuccessResponse.with("Invite revoked successfully.");
	}
	
	
	@Operation(summary = "Get Invite Info", description = "Fetch pre-claim information about an invite using its token.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Invite info fetched") })
	@GetMapping("/invites/pre-claim/{token}/info")
	public ResponseEntity<InvitePreClaimFetchResponse> getInviteInfo(@PathVariable("token") String token) {
		var command = InvitePreClaimFetchCommand.builder()
				.token(token)
				.build();
		
		var result = invitePreClaimFetchUseCase.execute(command);
		
		var response = invitePreClaimFetchMapper.toResponse(result);
		
		return response.toOkResponse("Invite info fetched successfully.");
	}

	@Operation(summary = "Claim Invite", description = "Claim an invite using its token.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Invite claimed") })
	@PostMapping("/invites/claim")
	public ResponseEntity<SuccessResponse> claimInvite(@RequestBody InviteClaimRequest request) {
		var command = inviteClaimMapper.toCommand(request);
		
		inviteClaimUseCase.execute(command);
		
		return SuccessResponse.with("Invite claimed successfully.");
	}
	
}
