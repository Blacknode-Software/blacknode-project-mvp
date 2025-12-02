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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import software.blacknode.backend.api.controller.BaseController;
import software.blacknode.backend.api.controller.project.request.ProjectCreateRequest;
import software.blacknode.backend.api.controller.project.request.ProjectPatchRequest;
import software.blacknode.backend.api.controller.project.response.ProjectCreateResponse;
import software.blacknode.backend.api.controller.project.response.ProjectDeleteResponse;
import software.blacknode.backend.api.controller.project.response.ProjectPatchResponse;
import software.blacknode.backend.api.controller.project.response.ProjectResponse;

@RestController
@Tag(name = "Projects", description = "Project management APIs")
public class ProjectController extends BaseController {

	@Operation(summary = "Get a project by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Found the project"),
			@ApiResponse(responseCode = "404", description = "Project not found") })
	@GetMapping("/projects/{id}")
	public ResponseEntity<ProjectResponse> getProject(UUID id) {
		return ResponseEntity.ok(null);
	}

	@Operation(summary = "Get all projects for an organization")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Found projects") })
	@GetMapping("/organization/{organizationId}/projects")
	public ResponseEntity<List<ProjectResponse>> getProjects(@PathVariable UUID organizationId) {
		return ResponseEntity.ok(null);
	}

	@Operation(summary = "Create a new project")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Project created"),
			@ApiResponse(responseCode = "400", description = "Invalid input") })
	@PostMapping("/organization/{organizationId}/projects")
	public ResponseEntity<ProjectCreateResponse> createProject(@PathVariable UUID organizationId,
			@RequestBody ProjectCreateRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(null);
	}

	@Operation(summary = "Update an existing project")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Project updated"),
			@ApiResponse(responseCode = "404", description = "Project not found") })
	@PatchMapping("/projects/{id}")
	public ResponseEntity<ProjectPatchResponse> patchProject(@PathVariable UUID id,
			@RequestBody ProjectPatchRequest request) {
		return ResponseEntity.ok(null);
	}

	@Operation(summary = "Delete a project")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Project deleted"),
			@ApiResponse(responseCode = "404", description = "Project not found") })
	@DeleteMapping("/projects/{id}")
	public ResponseEntity<ProjectDeleteResponse> deleteProject(@PathVariable UUID id) {
		return ResponseEntity.ok(null);
	}

}