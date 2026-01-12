package software.blacknode.backend.api.controller.task.status;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.api.controller.organization.annotation.OrganizationHeader;
import software.blacknode.backend.api.controller.task.status.mapper.TaskStatusMapper;
import software.blacknode.backend.api.controller.task.status.request.TaskStatusBatchFetchRequest;
import software.blacknode.backend.api.controller.task.status.response.TaskStatusBatchFetchResponse;
import software.blacknode.backend.api.controller.task.status.response.TaskStatusListResponse;
import software.blacknode.backend.api.controller.task.status.response.TaskStatusResponse;
import software.blacknode.backend.api.controller.task.status.response.content.TaskStatusResponseContent;
import software.blacknode.backend.application.task.status.TaskStatusService;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;


@RestController
@RequiredArgsConstructor
@Tag(name = "Task Statuses", description = "Task status management APIs")
public class TaskStatusController {
	
	private final TaskStatusService taskStatusService;
	
	private final TaskStatusMapper taskStatusMapper;
	
	private final SessionContextHolder sessionContextHolder;
	
	@OrganizationHeader
	@Operation(summary = "Get a task status by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Task status fetched successfully") })
	@GetMapping("/statuses/{id}")
	public ResponseEntity<TaskStatusResponse> getTaskStatus(@PathVariable UUID id) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		
		var status = taskStatusService.getOrThrow(organizationId, HUID.fromUUID(id));
		
		var response = TaskStatusResponse.builder()
				.id(status.getId().toUUID())
				.name(status.getName())
				.color(status.getColor())
				.build();
		
		return response.toOkResponse("Task status fetched successfully");
	}
	
	@OrganizationHeader
	@Operation(summary = "Batch fetch task statuses by IDs")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Task statuses fetched successfully") })
	@PostMapping("/statuses/batch-fetch")
	public ResponseEntity<TaskStatusBatchFetchResponse> batchFetchTaskStatuses(@RequestBody TaskStatusBatchFetchRequest request) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		
		var ids = request.getIds().stream()
				.map(HUID::fromUUID)
				.toList();
		
		var statues = taskStatusService.getByIds(organizationId, ids);
		
		List<TaskStatusResponseContent> items = statues.stream()
				.map(status -> taskStatusMapper.map(status))
				.toList();
		
		var response = TaskStatusBatchFetchResponse.builder()
				.items(items)
				.build();
				
		
		return response.toOkResponse("Task statuses fetched successfully");
	}
	
	@OrganizationHeader
	@Operation(summary = "Get task statuses in a channel")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Task statuses in channel fetched successfully") })
	@GetMapping("/channels/{channelId}/statuses")
	public ResponseEntity<TaskStatusListResponse> taskStatusesInChannel(@PathVariable UUID channelId) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		
		var statuses = taskStatusService.getAllInChannel(organizationId, HUID.fromUUID(channelId));
		
		List<UUID> statusIds = statuses.stream()
				.map(status -> status.getId().toUUID())
				.toList();
		
		var response = TaskStatusListResponse.builder()
				.ids(statusIds)
				.build();
				
		return response.toOkResponse("Task statuses in channel fetched successfully");
	}
	
}
