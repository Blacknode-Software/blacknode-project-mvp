package software.blacknode.backend.api.controller.task.status;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.api.controller.organization.annotation.OrganizationHeader;
import software.blacknode.backend.api.controller.task.status.request.TaskStatusBatchFetchRequest;
import software.blacknode.backend.api.controller.task.status.response.TaskStatusBatchFetchResponse;
import software.blacknode.backend.api.controller.task.status.response.TaskStatusResponse;
import software.blacknode.backend.api.controller.task.status.response.content.TaskStatusResponseContent;
import software.blacknode.backend.application.task.status.TaskStatusService;
import software.blacknode.backend.domain.context.SessionContext;


@RestController
@RequiredArgsConstructor
public class TaskStatusController {
	
	private final TaskStatusService taskStatusService;
	
	@Autowired
	private SessionContext sessionContext;
	
	@OrganizationHeader
	@Operation(summary = "Get a task status by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Task status fetched successfully") })
	@GetMapping("/statuses/{id}")
	public ResponseEntity<TaskStatusResponse> getTaskStatus(@PathVariable UUID id) {
		var organizationId = sessionContext.getOrganizationId();
		
		var status = taskStatusService.getOrThrow(organizationId, HUID.fromUUID(id));
		
		var response = TaskStatusResponse.builder()
				.id(status.getId().toUUID())
				.name(status.getName())
				.color(status.getColor())
				.build();
		
		return response.toOkResponse("Task status fetched successfully");
	}
	
	@PostMapping("/statuses/batch-fetch")
	public ResponseEntity<TaskStatusBatchFetchResponse> batchFetchTaskStatuses(@RequestBody TaskStatusBatchFetchRequest request) {
		var organizationId = sessionContext.getOrganizationId();
		
		var ids = request.getIds().stream()
				.map(HUID::fromUUID)
				.toList();
		
		var statues = taskStatusService.getByIds(organizationId, ids);
		
		List<TaskStatusResponseContent> items = statues.stream()
				.map(status -> TaskStatusResponseContent.builder()
						.id(status.getId().toUUID())
						.name(status.getName())
						.color(status.getColor())
						.build())
				.map((TaskStatusResponseContent status) -> status)
				.toList();
		
		var response = TaskStatusBatchFetchResponse.builder()
				.items(items)
				.build();
				
		
		return response.toOkResponse("Task statuses fetched successfully");
	}
	
}
