package software.blacknode.backend.api.controller.task;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.api.controller.annotation.DisplayPatchOperations;
import software.blacknode.backend.api.controller.organization.annotation.OrganizationHeader;
import software.blacknode.backend.api.controller.task.mapper.impl.TaskCreateMapper;
import software.blacknode.backend.api.controller.task.mapper.impl.TaskFetchMapper;
import software.blacknode.backend.api.controller.task.mapper.impl.TaskPatchMapper;
import software.blacknode.backend.api.controller.task.request.TaskCreateRequest;
import software.blacknode.backend.api.controller.task.request.TaskPatchRequest;
import software.blacknode.backend.api.controller.task.response.TaskCreateResponse;
import software.blacknode.backend.api.controller.task.response.TaskPatchResponse;
import software.blacknode.backend.api.controller.task.response.TaskResponse;
import software.blacknode.backend.application.task.command.TaskFetchCommand;
import software.blacknode.backend.application.task.usecase.TaskCreateUseCase;
import software.blacknode.backend.application.task.usecase.TaskFetchUseCase;
import software.blacknode.backend.application.task.usecase.TaskPatchUseCase;
import software.blacknode.backend.application.task.usecase.TaskPatchUseCase.TaskPatchOperation;

@Hidden
@RestController
@RequiredArgsConstructor
public class TaskController {
	
	private final TaskFetchMapper taskFetchMapper;
	private final TaskFetchUseCase taskFetchUseCase;
	
	private final TaskCreateMapper taskCreateMapper;
	private final TaskCreateUseCase taskCreateUseCase;
	
	private final TaskPatchMapper taskPatchMapper;
	private final TaskPatchUseCase taskPatchUseCase;
	
	@OrganizationHeader
	@Operation(summary = "Fetch a task by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Task fetched") })
	@GetMapping("/tasks/{id}")
	public ResponseEntity<TaskResponse> getTask(UUID id) {
		var command = TaskFetchCommand.builder()
				.taskId(HUID.fromUUID(id))
				.build();
		
		var result = taskFetchUseCase.execute(command);
		
		var response = taskFetchMapper.toResponse(result);
		
		return response.toOkResponse("Task fetched successfully");
	}
	
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
	
	@OrganizationHeader
	@Operation(summary = "Patch an existing task")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Task updated") })
	@DisplayPatchOperations(TaskPatchOperation.class)
	@PatchMapping("/tasks/{id}")
	public ResponseEntity<TaskPatchResponse> patchTask(@PathVariable UUID id, @RequestBody TaskPatchRequest request) {
		var command = taskPatchMapper.toCommand(request, id);
		
		var result = taskPatchUseCase.execute(command);
		
		var response = taskPatchMapper.toResponse(result);
		
		return response.toOkResponse("Task updated successfully");
	}
}
