package software.blacknode.backend.api.controller.organization;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import software.blacknode.backend.api.controller.BaseController;
import software.blacknode.backend.api.controller.annotation.BearerAuth;
import software.blacknode.backend.api.controller.annotation.DisplayPatchOperations;
import software.blacknode.backend.api.controller.organization.annotation.OrganizationHeader;
import software.blacknode.backend.api.controller.organization.mapper.impl.OrganizationFetchMapper;
import software.blacknode.backend.api.controller.organization.mapper.impl.OrganizationPatchMapper;
import software.blacknode.backend.api.controller.organization.request.OrganizationPatchRequest;
import software.blacknode.backend.api.controller.organization.response.OrganizationPatchResponse;
import software.blacknode.backend.api.controller.organization.response.OrganizationResponse;
import software.blacknode.backend.application.organization.command.OrganizationFetchCommand;
import software.blacknode.backend.application.organization.usecase.OrganizationFetchUseCase;
import software.blacknode.backend.application.organization.usecase.OrganizationPatchUseCase;
import software.blacknode.backend.application.organization.usecase.OrganizationPatchUseCase.OrganizationPatchOperation;

@BearerAuth
@RestController
@RequiredArgsConstructor
@Tag(name = "Organization", description = "Organization management APIs")
public class OrganizationController extends BaseController {

	private final OrganizationFetchMapper organizationFetchMapper;
	private final OrganizationFetchUseCase organizationFetchUseCase;
	
	private final OrganizationPatchMapper organizationPatchMapper;
	private final OrganizationPatchUseCase organizationPatchUseCase;
	
	@OrganizationHeader
	@Operation(summary = "Get organization details")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Organization details"),
			@ApiResponse(responseCode = "404", description = "Organization not found") })
	@GetMapping("/organization")
	public ResponseEntity<OrganizationResponse> getOrganization() {
		var command = OrganizationFetchCommand.builder().build();
		
		var result = organizationFetchUseCase.execute(command);
		
		var response = organizationFetchMapper.toResponse(result);
		
		return response.toOkResponse("Organization fetched successfully!");
	}
	
//	@Operation(summary = "Get all organizations")
//	@ApiResponses(value = { 
//			@ApiResponse(responseCode = "200", description = "Found organizations") })
//	@GetMapping("/organizations")
//	public ResponseEntity<List<OrganizationResponse>> getOrganizations() {
//		return ResponseEntity.ok(null);
//	}
	
	@OrganizationHeader
	@Operation(summary = "Update organization", description = "Update organization details.")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Organization updated"),
			@ApiResponse(responseCode = "400", description = "Invalid request"),
			@ApiResponse(responseCode = "404", description = "Organization not found") })
	@DisplayPatchOperations(OrganizationPatchOperation.class)
	@PatchMapping("/organization")
	public ResponseEntity<OrganizationPatchResponse> patchOrganization(@RequestBody OrganizationPatchRequest request) {
		var command = organizationPatchMapper.toCommand(request);
		
		var result = organizationPatchUseCase.execute(command);
		
		var response = organizationPatchMapper.toResponse(result);
		
		return response.toOkResponse("Organization updated successfully!");
	}
	
}
