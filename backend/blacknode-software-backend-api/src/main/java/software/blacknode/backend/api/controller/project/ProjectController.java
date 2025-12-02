package software.blacknode.backend.api.controller.project;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import software.blacknode.backend.api.controller.project.request.ProjectCreateRequest;
import software.blacknode.backend.api.controller.project.request.ProjectPatchRequest;
import software.blacknode.backend.api.controller.project.response.ProjectCreateResponse;
import software.blacknode.backend.api.controller.project.response.ProjectDeleteResponse;
import software.blacknode.backend.api.controller.project.response.ProjectPatchResponse;
import software.blacknode.backend.api.controller.project.response.ProjectResponse;

@RestController
public class ProjectController {

	@GetMapping("/projects/{id}")
	public ResponseEntity<ProjectResponse> getProject(UUID id) {
		return ResponseEntity.ok(null);
	}

	@GetMapping("/organization/{organizationId}/projects")
	public ResponseEntity<List<ProjectResponse>> getProjects(@PathVariable UUID organizationId) {
		return ResponseEntity.ok(null);
	}

	@PostMapping("/organization/{organizationId}/projects")
	public ResponseEntity<ProjectCreateResponse> createProject(@PathVariable UUID organizationId,
			@RequestBody ProjectCreateRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(null);
	}

	@PatchMapping("/projects/{id}")
	public ResponseEntity<ProjectPatchResponse> patchProject(@PathVariable UUID id, @RequestBody ProjectPatchRequest request) {
		return ResponseEntity.ok(null);
	}

	@DeleteMapping("/projects/{id}")
	public ResponseEntity<ProjectDeleteResponse> deleteProject(@PathVariable UUID id) {
		return ResponseEntity.ok(null);
	}

}