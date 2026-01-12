package software.blacknode.backend.api.controller.task.assign;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import software.blacknode.backend.api.controller.organization.annotation.OrganizationHeader;
import software.blacknode.backend.api.controller.response.impl.SuccessResponse;
import software.blacknode.backend.api.controller.task.assign.mapper.impl.TaskAssignMemberMapper;
import software.blacknode.backend.api.controller.task.assign.request.TaskAssignMemberRequest;
import software.blacknode.backend.application.task.assign.usecase.TaskAssignMemberUseCase;

@RestController
@RequiredArgsConstructor
@Tag(name = "Task Assignments", description = "Task assignment management APIs")
public class TaskAssignController {

	private final TaskAssignMemberMapper taskAssignMapper;
	private final TaskAssignMemberUseCase taskAssignUseCase;
	
	
	
	@OrganizationHeader
	@Operation(summary = "Assign Task", description = "Assign a task to a member")
	@ApiResponses(value = @ApiResponse(responseCode = "200", description = "Task assigned successfully"))
	@PostMapping("/tasks/{taskId}/assigns")
	public ResponseEntity<SuccessResponse> assignTask(@PathVariable UUID taskId, @RequestBody TaskAssignMemberRequest request) {
		var command = taskAssignMapper.toCommand(request, taskId);
		
		taskAssignUseCase.execute(command);
		
		return SuccessResponse.with("Task assigned successfully");
	}
	
	
}
