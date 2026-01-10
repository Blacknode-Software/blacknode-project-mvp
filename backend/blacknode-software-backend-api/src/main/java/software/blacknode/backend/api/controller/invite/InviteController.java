package software.blacknode.backend.api.controller.invite;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import software.blacknode.backend.api.controller.invite.mapper.impl.InviteCreateMapper;
import software.blacknode.backend.api.controller.invite.mapper.impl.InviteFetchMapper;
import software.blacknode.backend.api.controller.invite.mapper.impl.InvitesBatchFetchMapper;
import software.blacknode.backend.api.controller.invite.request.InviteCreateRequest;
import software.blacknode.backend.api.controller.invite.request.InvitesBatchFetchRequest;
import software.blacknode.backend.api.controller.invite.response.InviteCreateResponse;
import software.blacknode.backend.api.controller.invite.response.InviteResponse;
import software.blacknode.backend.api.controller.invite.response.InvitesBatchFetchResponse;
import software.blacknode.backend.api.controller.organization.annotation.OrganizationHeader;
import software.blacknode.backend.application.invite.command.InviteFetchCommand;
import software.blacknode.backend.application.invite.usecase.InviteCreateUseCase;
import software.blacknode.backend.application.invite.usecase.InviteFetchUseCase;
import software.blacknode.backend.application.invite.usecase.InvitesBatchFetchUseCase;

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
	
	@OrganizationHeader
	@Operation(summary = "Fetch Invite", description = "Fetch an invite by its ID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Invite fetched") })
	@GetMapping("/invites/{id}")
	public ResponseEntity<InviteResponse> getInvite(@PathVariable("id") HUID id) {
		var command = InviteFetchCommand.builder()
				.inviteId(id)
				.build();
		
		var result = inviteFetchUseCase.execute(command);
		
		var response = inviteFetchMapper.toResponse(result);
		
		return response.toOkResponse("Invite fetched successfully.");
	}
	
	@OrganizationHeader
	@Operation(summary = "Create Invite", description = "Create a new invite.")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Invite created") })
	@PostMapping("/invites")
	public ResponseEntity<InviteCreateResponse> createInvite(@RequestBody InviteCreateRequest request) {
		var command = inviteCreateMapper.toCommand(request);
		
		var result = inviteCreateUseCase.execute(command);
		
		var response = inviteCreateMapper.toResponse(result);
		
		return response.toSuccessResponse("Invite created successfully.", HttpStatus.CREATED);
	}
	
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
	
	
	
	
}
