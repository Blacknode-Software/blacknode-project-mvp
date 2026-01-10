package software.blacknode.backend.api.controller.role;

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
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.api.controller.annotation.BearerAuth;
import software.blacknode.backend.api.controller.annotation.DisplayPatchOperations;
import software.blacknode.backend.api.controller.organization.annotation.OrganizationHeader;
import software.blacknode.backend.api.controller.response.impl.SuccessResponse;
import software.blacknode.backend.api.controller.role.mapper.impl.RoleCreateMapper;
import software.blacknode.backend.api.controller.role.mapper.impl.RoleFetchMapper;
import software.blacknode.backend.api.controller.role.mapper.impl.RolePatchMapper;
import software.blacknode.backend.api.controller.role.mapper.impl.RolesBatchFetchMapper;
import software.blacknode.backend.api.controller.role.mapper.impl.RolesListMapper;
import software.blacknode.backend.api.controller.role.request.RoleCreateRequest;
import software.blacknode.backend.api.controller.role.request.RolePatchRequest;
import software.blacknode.backend.api.controller.role.request.RolesBatchFetchRequest;
import software.blacknode.backend.api.controller.role.response.RoleCreateResponse;
import software.blacknode.backend.api.controller.role.response.RolePatchResponse;
import software.blacknode.backend.api.controller.role.response.RoleResponse;
import software.blacknode.backend.api.controller.role.response.RolesBatchFetchResponse;
import software.blacknode.backend.api.controller.role.response.RolesListResponse;
import software.blacknode.backend.application.role.command.RoleDeleteCommand;
import software.blacknode.backend.application.role.command.RoleFetchCommand;
import software.blacknode.backend.application.role.command.RolesInOrganizationCommand;
import software.blacknode.backend.application.role.usecase.RoleCreateUseCase;
import software.blacknode.backend.application.role.usecase.RoleDeleteUseCase;
import software.blacknode.backend.application.role.usecase.RoleFetchUseCase;
import software.blacknode.backend.application.role.usecase.RolePatchUseCase;
import software.blacknode.backend.application.role.usecase.RolePatchUseCase.RolePatchOperation;
import software.blacknode.backend.application.role.usecase.RolesBatchFetchUseCase;
import software.blacknode.backend.application.role.usecase.RolesInOrganizationUseCase;

@BearerAuth
@RestController
@RequiredArgsConstructor
@Tag(name = "Roles", description = "Role managment APIs")
public class RoleController {

	private final RoleFetchMapper roleFetchMapper;
	private final RoleFetchUseCase roleFetchUseCase;
	
	private final RolePatchMapper rolePatchMapper;
	private final RolePatchUseCase rolePatchUseCase;
	
	private final RoleCreateMapper roleCreateMapper;
	private final RoleCreateUseCase roleCreateUseCase;
	
	private final RolesBatchFetchMapper rolesBatchFetchMapper;
	private final RolesBatchFetchUseCase rolesBatchFetchUseCase;
	
	private final RoleDeleteUseCase roleDeleteUseCase;
	
	private final RolesListMapper rolesListMapper;
	private final RolesInOrganizationUseCase rolesInOrganizationUseCase;
	
	@OrganizationHeader
	@Operation(summary = "Fetch Role", description = "Fetch a role by its ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Role fetched successfully") })
	@GetMapping("/roles/{id}")
	public ResponseEntity<RoleResponse> getRole(@PathVariable UUID id) {
		var command = RoleFetchCommand.builder()
				.roleId(HUID.fromUUID(id))
				.build();
		
		var result = roleFetchUseCase.execute(command);
		
		var response = roleFetchMapper.toResponse(result);
		
		return response.toOkResponse("Role fetched successfully");
	}
	
	@OrganizationHeader
	@Operation(summary = "Create Role", description = "Create a new role in the organization")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Role created successfully") })
	@PostMapping("/organization/roles")
	public ResponseEntity<RoleCreateResponse> createRole(@RequestBody RoleCreateRequest request) {
		var command = roleCreateMapper.toCommand(request);
		
		var result = roleCreateUseCase.execute(command);
		
		var response = roleCreateMapper.toResponse(result);
		
		return response.toSuccessResponse("Role created successfully", HttpStatus.CREATED);
	}
	
	@OrganizationHeader
	@Operation(summary = "Update Role", description = "Update a role by its ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Role updated successfully") })
	@DisplayPatchOperations(RolePatchOperation.class)
	@PatchMapping("/roles/{id}")
	public ResponseEntity<RolePatchResponse> patchRole(@PathVariable UUID id, @RequestBody RolePatchRequest request) {
		var command = rolePatchMapper.toCommand(request, id);
		
		var result = rolePatchUseCase.execute(command);
		
		var response = rolePatchMapper.toResponse(result);
		
		return response.toOkResponse("Role updated successfully");
	}
	
	@OrganizationHeader
	@Operation(summary = "Batch Fetch Roles", description = "Fetch multiple roles by their IDs")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Roles fetched successfully") })
	@PostMapping("/roles/batch-fetch")
	public ResponseEntity<RolesBatchFetchResponse> getRolesBatch(@RequestBody RolesBatchFetchRequest request) {
		var command = rolesBatchFetchMapper.toCommand(request);
		
		var result = rolesBatchFetchUseCase.execute(command);
		
		var response = rolesBatchFetchMapper.toResponse(result);
		
		return response.toOkResponse("Roles fetched successfully");
	}
	
	@OrganizationHeader
	@Operation(summary = "Delete Role", description = "Delete a role by its ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Role deleted successfully") })
	@DeleteMapping("/roles/{id}")
	public ResponseEntity<SuccessResponse> deleteRole(@PathVariable UUID id) {
		var command = RoleDeleteCommand.builder()
				.roleId(HUID.fromUUID(id))
				.build();
		
		roleDeleteUseCase.execute(command);
		
		return SuccessResponse.with("Role deleted successfully");
	}
	
	@OrganizationHeader
	@Operation(summary = "Get Organization Roles", description = "Fetch all roles in the organization")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Organization roles fetched successfully") })
	@GetMapping("/organization/roles")
	public ResponseEntity<RolesListResponse> getOrganizationRoles() {
		var command = RolesInOrganizationCommand.builder()
				.build();
		
		var result = rolesInOrganizationUseCase.execute(command);
		
		var response = rolesListMapper.toOrganizationResponse(result);
		
		return response.toOkResponse("Organization roles fetched successfully");
	}
}
