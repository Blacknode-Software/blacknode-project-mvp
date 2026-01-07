package software.blacknode.backend.api.controller.member;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import software.blacknode.backend.api.controller.BaseController;
import software.blacknode.backend.api.controller.member.mapper.MemberAssignRoleMapper;
import software.blacknode.backend.api.controller.member.request.MemberAssignRoleRequest;
import software.blacknode.backend.api.controller.organization.annotation.OrganizationHeader;
import software.blacknode.backend.api.controller.response.impl.SuccessResponse;
import software.blacknode.backend.application.member.usecase.MemberAssignChannelRoleUseCase;
import software.blacknode.backend.application.member.usecase.MemberAssignOrganizationRoleUseCase;
import software.blacknode.backend.application.member.usecase.MemberAssignProjectRoleUseCase;

@RestController
@Tag(name = "Members", description = "Member management APIs")
@RequiredArgsConstructor
public class MemberController extends BaseController {
	
	private final MemberAssignRoleMapper memberAssignRoleMapper;
	
	private final MemberAssignOrganizationRoleUseCase memberAssignOrganizationRoleUseCase;
	private final MemberAssignProjectRoleUseCase memberAssignProjectRoleUseCase;
	private final MemberAssignChannelRoleUseCase memberAssignChannelRoleUseCase;
	
	@OrganizationHeader
	@Operation(summary = "Assign Organization Role to Member", description = "Assigns a specific role to a member within the organization.")
	@ApiResponse(responseCode = "200", description = "Organization role assigned successfully.")
	@PostMapping("/ogranization/members/{memberId}/role")
	public ResponseEntity<SuccessResponse> assignOrganizationRole(@RequestBody MemberAssignRoleRequest request, @PathVariable UUID memberId) {
		var command = memberAssignRoleMapper.toOrganizationAssignCommand(request, memberId);
		
		memberAssignOrganizationRoleUseCase.execute(command);
		
		return SuccessResponse.with("Organization role assigned successfully.");
	}
	
	@OrganizationHeader
	@Operation(summary = "Assign Project Role to Member", description = "Assigns a specific role to a member within the project.")
	@ApiResponse(responseCode = "200", description = "Organization role assigned successfully.")
	@PostMapping("/projects/{projectId}/members/{memberId}/role")
	public ResponseEntity<SuccessResponse> assignProjectRole(@RequestBody MemberAssignRoleRequest request, @PathVariable UUID memberId, @PathVariable UUID projectId) {
		var command = memberAssignRoleMapper.toProjectAssignCommand(request, memberId, projectId);
		
		memberAssignProjectRoleUseCase.execute(command);
		
		return SuccessResponse.with("Project role assigned successfully.");
	}
	
	@OrganizationHeader
	@Operation(summary = "Assign Channel Role to Member", description = "Assigns a specific role to a member within the channel.")
	@ApiResponse(responseCode = "200", description = "Organization role assigned successfully.")
	@PostMapping("/channels/{channelId}/members/{memberId}/role")
	public ResponseEntity<SuccessResponse> assignChannelRole(@RequestBody MemberAssignRoleRequest request, @PathVariable UUID memberId, @PathVariable UUID channelId) {
		var command = memberAssignRoleMapper.toChannelAssignCommand(request, memberId, channelId);
		
		memberAssignChannelRoleUseCase.execute(command);
		
		return SuccessResponse.with("Channel role assigned successfully.");
	}
	
	
}