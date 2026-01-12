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
import software.blacknode.backend.api.controller.task.assign.mapper.impl.TasksAssignsListMapper;
import software.blacknode.backend.api.controller.task.assign.request.TaskAssignMemberRequest;
import software.blacknode.backend.api.controller.task.assign.request.TaskUnassignMemberRequest;
import software.blacknode.backend.api.controller.task.assign.request.TasksAssignsBatchFetchRequest;
import software.blacknode.backend.api.controller.task.assign.request.TasksAssignsOfMembersRequest;
import software.blacknode.backend.api.controller.task.assign.request.TasksAssignsOfTasksRequest;
import software.blacknode.backend.api.controller.task.assign.response.TaskAssignResponse;
import software.blacknode.backend.api.controller.task.assign.response.TasksAssignsBatchFetchResponse;
import software.blacknode.backend.api.controller.task.assign.response.TasksAssignsListResponse;
import software.blacknode.backend.application.task.assign.command.TaskAssignFetchCommand;
import software.blacknode.backend.application.task.assign.command.TasksAssignsOfMemberCommand;
import software.blacknode.backend.application.task.assign.command.TasksAssignsOfTaskCommand;
import software.blacknode.backend.application.task.assign.usecase.TaskAssignFetchUseCase;
import software.blacknode.backend.application.task.assign.usecase.TaskAssignMemberUseCase;
import software.blacknode.backend.application.task.assign.usecase.TaskUnassignMemberUseCase;
import software.blacknode.backend.application.task.assign.usecase.TasksAssignsBatchFetchUseCase;
import software.blacknode.backend.application.task.assign.usecase.TasksAssignsOfMemberUseCase;
import software.blacknode.backend.application.task.assign.usecase.TasksAssignsOfMembersUseCase;
import software.blacknode.backend.application.task.assign.usecase.TasksAssignsOfTaskUseCase;
import software.blacknode.backend.application.task.assign.usecase.TasksAssignsOfTasksUseCase;

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
	
	private final TasksAssignsListMapper taskAssignsListMapper;
	
	private final TasksAssignsOfMemberUseCase tasksAssignsOfMemberUseCase;
	private final TasksAssignsOfMembersUseCase tasksAssignsOfMembersUseCase;

	private final TasksAssignsOfTaskUseCase tasksAssignsOfTaskUseCase;
	private final TasksAssignsOfTasksUseCase tasksAssignsOfTasksUseCase;
	
	@OrganizationHeader
	@Operation(summary = "Get Task Assignment", description = "Fetch a specific task assignment by its ID")
	@ApiResponses(value = @ApiResponse(responseCode = "200", description = "Task assignment fetched successfully"))
	@GetMapping("/tasks/assigns/{id}")
	public ResponseEntity<TaskAssignResponse> getTaskAssign(@PathVariable UUID id) {
		var command = TaskAssignFetchCommand.builder()
				.assignId(HUID.fromUUID(id))
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
	
	@OrganizationHeader
	@Operation(summary = "Get Task Assignments of a Task", description = "Fetch all task assignments associated with a specific task")
	@ApiResponses(value = @ApiResponse(responseCode = "200", description = "Task assignments fetched successfully"))
	@GetMapping("/tasks/{taskId}/assigns")
	public ResponseEntity<TasksAssignsListResponse> getTaskAssigns(@PathVariable UUID taskId) {
		var command = TasksAssignsOfTaskCommand.builder()
				.taskId(HUID.fromUUID(taskId))
				.build();
		
		var result = tasksAssignsOfTaskUseCase.execute(command);
		
		var response = taskAssignsListMapper.toAssignsOfTaskResponse(result);
		
		return response.toOkResponse("Task assignments fetched successfully");
	}
	
	@OrganizationHeader
	@Operation(summary = "Get Task Assignments of Multiple Tasks", description = "Fetch task assignments for multiple tasks")
	@ApiResponses(value = @ApiResponse(responseCode = "200", description = "Task assignments fetched successfully"))
	@PostMapping("/tasks/assigns")
	public ResponseEntity<TasksAssignsListResponse> getTasksAssigns(@RequestBody TasksAssignsOfTasksRequest request) {
		var command = taskAssignsListMapper.toCommand(request);
		
		var result = tasksAssignsOfTasksUseCase.execute(command);
		
		var response = taskAssignsListMapper.toAssignsOfTasksResponse(result);
		
		return response.toOkResponse("Tasks assignments fetched successfully");
	}
	
	@OrganizationHeader
	@Operation(summary = "Get Task Assignments of a Member", description = "Fetch all task assignments associated with a specific member")
	@ApiResponses(value = @ApiResponse(responseCode = "200", description = "Member task assignments fetched successfully"))
	@GetMapping("/members/{memberId}/assigns")
	public ResponseEntity<TasksAssignsListResponse> getMemberAssigns(@PathVariable UUID memberId) {
		var command = TasksAssignsOfMemberCommand.builder()
				.memberId(HUID.fromUUID(memberId))
				.build();
		
		var result = tasksAssignsOfMemberUseCase.execute(command);
		
		var response = taskAssignsListMapper.toAssingsOfMemberResponse(result);
		
		return response.toOkResponse("Member task assignments fetched successfully");
	}
	
	@OrganizationHeader
	@Operation(summary = "Get Task Assignments of Members", description = "Fetch all task assignments associated with a list of members")
	@ApiResponses(value = @ApiResponse(responseCode = "200", description = "Members task assignments fetched successfully"))
	@PostMapping("/members/assigns")
	public ResponseEntity<TasksAssignsListResponse> getMembersAssigns(@RequestBody TasksAssignsOfMembersRequest request) {
		var command = taskAssignsListMapper.toCommand(request);
		
		var result = tasksAssignsOfMembersUseCase.execute(command);
		
		var response = taskAssignsListMapper.toAssingsOfMembersResponse(result);
		
		return response.toOkResponse("Members task assignments fetched successfully");
	}
	
	
}
