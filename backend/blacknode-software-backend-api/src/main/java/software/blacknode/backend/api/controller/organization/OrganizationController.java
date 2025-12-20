package software.blacknode.backend.api.controller.organization;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import software.blacknode.backend.api.controller.BaseController;
import software.blacknode.backend.api.controller.organization.request.OrganizationPatchRequest;
import software.blacknode.backend.api.controller.organization.response.OrganizationResponse;

@Hidden
@RestController
@Tag(name = "Organizations", description = "Organization management APIs")
public class OrganizationController extends BaseController {

	@Operation(summary = "Get an organization by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Found the organization"),
			@ApiResponse(responseCode = "404", description = "Organization not found") })
	@GetMapping("/organizations/{organizationId}")
	public ResponseEntity<OrganizationResponse> getOrganization(@PathVariable UUID id) {
		return ResponseEntity.ok(null);
	}
	
	@Operation(summary = "Get all organizations")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Found organizations") })
	@GetMapping("/organizations")
	public ResponseEntity<List<OrganizationResponse>> getOrganizations() {
		return ResponseEntity.ok(null);
	}
	
	@Operation(summary = "Update an existing organization")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Organization updated"),
			@ApiResponse(responseCode = "404", description = "Organization not found") })
	@PatchMapping("/organizations/{organizationId}")
	public ResponseEntity<OrganizationResponse> patchOrganization(@PathVariable UUID id, @RequestBody OrganizationPatchRequest request) {
		return ResponseEntity.ok(null);
	}
	
}