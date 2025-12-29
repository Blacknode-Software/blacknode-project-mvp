package software.blacknode.backend.api.controller.task;

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
import software.blacknode.backend.api.controller.annotation.DisplayPatchOperations;
import software.blacknode.backend.api.controller.organization.annotation.OrganizationHeader;
import software.blacknode.backend.api.controller.response.impl.SuccessResponse;
import software.blacknode.backend.api.controller.task.mapper.impl.TaskCreateMapper;
import software.blacknode.backend.api.controller.task.mapper.impl.TaskFetchMapper;
import software.blacknode.backend.api.controller.task.mapper.impl.TaskPatchMapper;
import software.blacknode.backend.api.controller.task.mapper.impl.TasksBatchFetchMapper;
import software.blacknode.backend.api.controller.task.mapper.impl.TasksInChannelMapper;
import software.blacknode.backend.api.controller.task.request.TaskCreateRequest;
import software.blacknode.backend.api.controller.task.request.TaskPatchRequest;
import software.blacknode.backend.api.controller.task.request.TasksBatchFetchRequest;
import software.blacknode.backend.api.controller.task.response.TaskCreateResponse;
import software.blacknode.backend.api.controller.task.response.TaskPatchResponse;
import software.blacknode.backend.api.controller.task.response.TaskResponse;
import software.blacknode.backend.api.controller.task.response.TasksBatchFetchResponse;
import software.blacknode.backend.api.controller.task.response.TasksListResponse;
import software.blacknode.backend.application.task.command.TaskDeleteCommand;
import software.blacknode.backend.application.task.command.TaskFetchCommand;
import software.blacknode.backend.application.task.command.TasksInChannelCommand;
import software.blacknode.backend.application.task.usecase.TaskCreateUseCase;
import software.blacknode.backend.application.task.usecase.TaskDeleteUseCase;
import software.blacknode.backend.application.task.usecase.TaskFetchUseCase;
import software.blacknode.backend.application.task.usecase.TaskPatchUseCase;
import software.blacknode.backend.application.task.usecase.TaskPatchUseCase.TaskPatchOperation;
import software.blacknode.backend.application.task.usecase.TasksBatchFetchUseCase;
import software.blacknode.backend.application.task.usecase.TasksInChannelUseCase;

@RestController
@RequiredArgsConstructor
@Tag(name = "Tasks", description = "Task management APIs")
public class TaskController {
	
	private final TaskFetchMapper taskFetchMapper;
	private final TaskFetchUseCase taskFetchUseCase;
	
	private final TasksBatchFetchMapper tasksBatchMapper;
	private final TasksBatchFetchUseCase tasksBatchFetchUseCase;
	
	private final TasksInChannelMapper tasksInChannelMapper;
	private final TasksInChannelUseCase tasksInChannelUseCase;
	
	private final TaskCreateMapper taskCreateMapper;
	private final TaskCreateUseCase taskCreateUseCase;
	
	private final TaskPatchMapper taskPatchMapper;
	private final TaskPatchUseCase taskPatchUseCase;
	
	
	private final TaskDeleteUseCase taskDeleteUseCase;
	
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
	@Operation(summary = "Patch an existing task", description = "Update specific fields of an existing task.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Task updated") })
	@DisplayPatchOperations(TaskPatchOperation.class)
	@PatchMapping("/tasks/{id}")
	public ResponseEntity<TaskPatchResponse> patchTask(@PathVariable UUID id, @RequestBody TaskPatchRequest request) {
		var command = taskPatchMapper.toCommand(request, id);
		
		var result = taskPatchUseCase.execute(command);
		
		var response = taskPatchMapper.toResponse(result);
		
		return response.toOkResponse("Task updated successfully");
	}
	
	@OrganizationHeader
	@Operation(summary = "Get tasks by a batch of IDs")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Found tasks") })
	@PostMapping("/tasks/batch-fetch")
	public ResponseEntity<TasksBatchFetchResponse> getTasksBatch(@RequestBody TasksBatchFetchRequest request) {
		var command = tasksBatchMapper.toCommand(request);
		
		var result = tasksBatchFetchUseCase.execute(command);
		
		var response = tasksBatchMapper.toResponse(result);
		
		return response.toOkResponse("Tasks fetched successfully");
	}
	
	@OrganizationHeader
	@Operation(summary = "Delete a task")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Task deleted") })
	@DeleteMapping("/tasks/{id}")
	public ResponseEntity<SuccessResponse> deleteTask(@PathVariable UUID id) {
		var command = TaskDeleteCommand.builder()
				.taskId(HUID.fromUUID(id))
				.build();
		
		taskDeleteUseCase.execute(command);
		
		return SuccessResponse.with("Task deleted successfully");
	}
	
	@OrganizationHeader
	@Operation(summary = "Get tasks for a specific channel")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Tasks for channel fetched") })
	@GetMapping("/channels/{channelId}/tasks")
	public ResponseEntity<TasksListResponse> getTasksForChannel(@PathVariable UUID channelId) {
	    var command = TasksInChannelCommand.builder()
	            .channelId(HUID.fromUUID(channelId))
	            .build();
	    
	    var result = tasksInChannelUseCase.execute(command);
	    
	    var response = tasksInChannelMapper.toResponse(result);
	    
	    return response.toOkResponse("Tasks for channel fetched successfully");
	}
}
