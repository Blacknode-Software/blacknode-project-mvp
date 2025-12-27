package software.blacknode.backend.api.controller.task;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import software.blacknode.backend.api.controller.organization.annotation.OrganizationHeader;
import software.blacknode.backend.api.controller.task.mapper.impl.TaskCreateMapper;
import software.blacknode.backend.api.controller.task.request.TaskCreateRequest;
import software.blacknode.backend.api.controller.task.response.TaskCreateResponse;
import software.blacknode.backend.application.task.usecase.TaskCreateUseCase;

@Hidden
@RestController
@RequiredArgsConstructor
public class TaskController {
	
	private final TaskCreateMapper taskCreateMapper;
	private final TaskCreateUseCase taskCreateUseCase;
	
	@OrganizationHeader
	@Operation(summary = "Create a new task")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Task created") })
	@PostMapping("/channels/{channelId}/tasks")
	public ResponseEntity<TaskCreateResponse> createTask(@PathVariable UUID channelId, @RequestBody TaskCreateRequest request) {
		var command = taskCreateMapper.toCommand(request, channelId);
		
		var result = taskCreateUseCase.execute(command);
		
		var response = taskCreateMapper.toResponse(result);
		
		return response.toSuccessResponse("Task created successfully", HttpStatus.CREATED);
	}
}
