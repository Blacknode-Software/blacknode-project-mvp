package software.blacknode.backend.api.controller.task;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import software.blacknode.backend.api.controller.task.request.TaskBatchRequest;
import software.blacknode.backend.api.controller.task.request.TaskCreateRequest;
import software.blacknode.backend.api.controller.task.request.TaskPatchRequest;
import software.blacknode.backend.api.controller.task.response.TaskBatchResponse;
import software.blacknode.backend.api.controller.task.response.TaskCreateResponse;
import software.blacknode.backend.api.controller.task.response.TaskListResponse;
import software.blacknode.backend.api.controller.task.response.TaskPatchResponse;
import software.blacknode.backend.api.controller.task.response.TaskResponse;

@RestController
public class TaskController {
	
	@GetMapping("/tasks/{id}")
	public ResponseEntity<TaskResponse> getTask(@PathVariable UUID id) {
		return null;
	}
		
	@GetMapping("/channel/{channelId}/tasks")
	public ResponseEntity<TaskListResponse> getTasks(@PathVariable UUID channelId) {
		return null;
	}
	
	@PostMapping("/tasks/batch")
	public ResponseEntity<TaskBatchResponse> getTasksBatch(@RequestBody TaskBatchRequest request) {
		return null;
	}
	
	@PostMapping("/channel/{id}/tasks")
	public ResponseEntity<TaskCreateResponse> createTask(@PathVariable UUID channelId, @RequestBody TaskCreateRequest request) {
		return null;
	}
	
	@PatchMapping("/tasks/{id}")
	public ResponseEntity<TaskPatchResponse> patchTask(@PathVariable UUID id, @RequestBody TaskPatchRequest request) {
		return null;
	}
}
