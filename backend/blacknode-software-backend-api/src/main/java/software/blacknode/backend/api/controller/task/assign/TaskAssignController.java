package software.blacknode.backend.api.controller.task.assign;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
import software.blacknode.backend.api.controller.organization.annotation.OrganizationHeader;
import software.blacknode.backend.api.controller.response.impl.SuccessResponse;
import software.blacknode.backend.api.controller.task.assign.mapper.impl.TaskAssignFetchMapper;
import software.blacknode.backend.api.controller.task.assign.mapper.impl.TaskAssignMemberMapper;
import software.blacknode.backend.api.controller.task.assign.mapper.impl.TaskUnassignMemberMapper;
import software.blacknode.backend.api.controller.task.assign.mapper.impl.TasksAssignsBatchFetchMapper;
import software.blacknode.backend.api.controller.task.assign.request.TaskAssignMemberRequest;
import software.blacknode.backend.api.controller.task.assign.request.TaskUnassignMemberRequest;
import software.blacknode.backend.api.controller.task.assign.request.TasksAssignsBatchFetchRequest;
import software.blacknode.backend.api.controller.task.assign.response.TaskAssignResponse;
import software.blacknode.backend.api.controller.task.assign.response.TasksAssignsBatchFetchResponse;
import software.blacknode.backend.application.task.assign.command.TaskAssignFetchCommand;
import software.blacknode.backend.application.task.assign.usecase.TaskAssignFetchUseCase;
import software.blacknode.backend.application.task.assign.usecase.TaskAssignMemberUseCase;
import software.blacknode.backend.application.task.assign.usecase.TaskUnassignMemberUseCase;
import software.blacknode.backend.application.task.assign.usecase.TasksAssignsBatchFetchUseCase;

@BearerAuth
@RestController
@RequiredArgsConstructor
@Tag(name = "Task Assignments", description = "Task assignment management APIs")
public class TaskAssignController {
	
	private final TaskAssignFetchMapper taskAssignFetchMapper;
	private final TaskAssignFetchUseCase taskAssignFetchUseCase;
	
	private final TaskAssignMemberMapper taskAssignMapper;
	private final TaskAssignMemberUseCase taskAssignUseCase;
	
	private final TaskUnassignMemberMapper taskUnassignMapper;
	private final TaskUnassignMemberUseCase taskUnassignUseCase;
	
	private final TasksAssignsBatchFetchMapper tasksAssignsBatchFetchMapper;
	private final TasksAssignsBatchFetchUseCase tasksAssignsBatchFetchUseCase;
	
	@OrganizationHeader
	@Operation(summary = "Get Task Assignment", description = "Fetch a specific task assignment by its ID")
	@ApiResponses(value = @ApiResponse(responseCode = "200", description = "Task assignment fetched successfully"))
	@GetMapping("/tasks/assigns/{assignId}")
	public ResponseEntity<TaskAssignResponse> getTaskAssign(@PathVariable UUID assignId) {
		var command = TaskAssignFetchCommand.builder()
				.assignId(HUID.fromUUID(assignId))
				.build();
		
		var result = taskAssignFetchUseCase.execute(command);
		
		var response = taskAssignFetchMapper.toResponse(result);
		
		return response.toOkResponse("Task assignment fetched successfully");
	}
	
	@OrganizationHeader
	@Operation(summary = "Assign Task", description = "Assign a task to a member")
	@ApiResponses(value = @ApiResponse(responseCode = "200", description = "Task assigned successfully"))
	@PostMapping("/tasks/{taskId}/assign")
	public ResponseEntity<SuccessResponse> assignTask(@PathVariable UUID taskId, @RequestBody TaskAssignMemberRequest request) {
		var command = taskAssignMapper.toCommand(request, taskId);
		
		taskAssignUseCase.execute(command);
		
		return SuccessResponse.with("Task assigned successfully");
	}
	
	@OrganizationHeader
	@Operation(summary = "Unassign Task", description = "Unassign a task from a member")
	@ApiResponses(value = @ApiResponse(responseCode = "200", description = "Task unassigned successfully"))
	@PostMapping("/tasks/{taskId}/unassign")
	public ResponseEntity<SuccessResponse> unassignTask(@PathVariable UUID taskId, @RequestBody TaskUnassignMemberRequest request) {
		var command = taskUnassignMapper.toCommand(request, taskId);
		
		taskUnassignUseCase.execute(command);
		
		return SuccessResponse.with("Task unassigned successfully");
	}
	
	@OrganizationHeader
	@Operation(summary = "Batch Fetch Task Assignments", description = "Fetch multiple task assignments by their IDs")
	@ApiResponses(value = @ApiResponse(responseCode = "200", description = "Task assignments fetched successfully"))
	@PostMapping("/tasks/assigns/batch-fetch")
	public ResponseEntity<TasksAssignsBatchFetchResponse> getTasksAssignsBatch(@RequestBody TasksAssignsBatchFetchRequest request) {
		var command = tasksAssignsBatchFetchMapper.toCommand(request);
		
		var result = tasksAssignsBatchFetchUseCase.execute(command);
		
		var response = tasksAssignsBatchFetchMapper.toResponse(result);
		
		return response.toOkResponse("Task assignments fetched successfully");
	}
	
	
	
	
}
