package software.blacknode.backend.api.controller.project;

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
import lombok.RequiredArgsConstructor;
import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.api.controller.BaseController;
import software.blacknode.backend.api.controller.annotation.InvalidInputResponse;
import software.blacknode.backend.api.controller.annotation.NotFoundResponse;
import software.blacknode.backend.api.controller.organization.annotation.OrganizationHeader;
import software.blacknode.backend.api.controller.project.converter.ProjectCreateRequestConverter;
import software.blacknode.backend.api.controller.project.converter.ProjectCreateResponseConverter;
import software.blacknode.backend.api.controller.project.converter.ProjectListResponseConverter;
import software.blacknode.backend.api.controller.project.converter.ProjectResponseConverter;
import software.blacknode.backend.api.controller.project.request.ProjectCreateRequest;
import software.blacknode.backend.api.controller.project.request.ProjectPatchRequest;
import software.blacknode.backend.api.controller.project.response.ProjectCreateResponse;
import software.blacknode.backend.api.controller.project.response.ProjectDeleteResponse;
import software.blacknode.backend.api.controller.project.response.ProjectListResponse;
import software.blacknode.backend.api.controller.project.response.ProjectPatchResponse;
import software.blacknode.backend.api.controller.project.response.ProjectResponse;
import software.blacknode.backend.application.project.command.ProjectFetchCommand;
import software.blacknode.backend.application.project.command.ProjectsInOrganizationFetchCommand;
import software.blacknode.backend.application.project.usecase.ProjectCreateUseCase;
import software.blacknode.backend.application.project.usecase.ProjectFetchUseCase;
import software.blacknode.backend.application.project.usecase.ProjectsInOrganizationFetchUseCase;

@Tag(name = "Projects", description = "Project management APIs")
@RestController
@RequiredArgsConstructor
public class ProjectController extends BaseController {
	
	private final ProjectCreateRequestConverter projectCreateRequestConverter;
	private final ProjectCreateResponseConverter projectCreateResponseConverter;
	private final ProjectCreateUseCase projectCreateUseCase;
	
	private final ProjectResponseConverter projectResponseConverter;
	private final ProjectFetchUseCase projectFetchUseCase;
	
	private final ProjectListResponseConverter projectListResponseConverter;
	private final ProjectsInOrganizationFetchUseCase projectsInOrganizationFetchUseCase;
	
	@OrganizationHeader
	@Operation(summary = "Create a new project", description = "Creates a new project within the specified organization.")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Project created") })
	@InvalidInputResponse
	@PostMapping("/projects")
	public ResponseEntity<ProjectCreateResponse> createProject(@PathVariable UUID organizationId,
			@RequestBody ProjectCreateRequest request) {
		
		var command = projectCreateRequestConverter.convert(request);
		
		var result = projectCreateUseCase.execute(command);
		
		var response = projectCreateResponseConverter.convert(result);
		
		return response.toSuccessResponse(HttpStatus.CREATED);
	}

	@OrganizationHeader
	@Operation(summary = "Get a project by ID", description = "Fetches a project by its unique identifier.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Found the project")})
	@NotFoundResponse
	@InvalidInputResponse
	@GetMapping("/projects/{id}")
	public ResponseEntity<ProjectResponse> getProject(@PathVariable UUID id) {
		var command = ProjectFetchCommand.builder()
				.projectId(HUID.fromUUID(id))
				.build();
		
		var result = projectFetchUseCase.execute(command);
		
		var response = projectResponseConverter.convert(result);

		return response.toOkResponse("Successfully fetched projects.");
	}

	@OrganizationHeader
	@Operation(summary = "Get all projects for an organization")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Found projects") })
	@InvalidInputResponse
	@GetMapping("/projects")
	public ResponseEntity<ProjectListResponse> getProjects() {
		var command = ProjectsInOrganizationFetchCommand.builder()
				.build();
		
		var result = projectsInOrganizationFetchUseCase.execute(command);
		
		var response = projectListResponseConverter.convert(result.getProjectsIds());
		
		return response.toOkResponse("Successfully fetched projects.");
	}

	@OrganizationHeader
	@Operation(summary = "Update an existing project")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Project updated")})
	@InvalidInputResponse
	@NotFoundResponse
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