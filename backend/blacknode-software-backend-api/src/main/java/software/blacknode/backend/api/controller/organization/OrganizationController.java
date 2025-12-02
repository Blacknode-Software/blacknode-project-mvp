package software.blacknode.backend.api.controller.organization;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import software.blacknode.backend.api.controller.organization.request.OrganizationPatchRequest;
import software.blacknode.backend.api.controller.organization.response.OrganizationResponse;
import software.blacknode.backend.api.controller.organization.response.OrganizationSettingsResponse;

@RestController
public class OrganizationController {

	@GetMapping("/organizations/{organizationId}")
	public ResponseEntity<OrganizationResponse> getOrganization(@PathVariable UUID id) {
		return ResponseEntity.ok(null);
	}
	
	@GetMapping("/organizations")
	public ResponseEntity<List<OrganizationResponse>> getOrganizations() {
		return ResponseEntity.ok(null);
	}
	
	
	@GetMapping("/organizations/{organizationId}/settings")
	public ResponseEntity<OrganizationSettingsResponse> getOrganizationSettings(@PathVariable UUID id) {
		return ResponseEntity.ok(null);
	}
	
	@PatchMapping("/organizations/{organizationId}")
	public ResponseEntity<OrganizationResponse> patchOrganization(@PathVariable UUID id, @RequestBody OrganizationPatchRequest request) {
		return ResponseEntity.ok(null);
	}
	
}