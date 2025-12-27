package software.blacknode.backend.api.controller.task;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import software.blacknode.backend.api.controller.task.request.TaskCreateRequest;
import software.blacknode.backend.api.controller.task.response.TaskCreateResponse;

@Hidden
@RestController
@RequiredArgsConstructor
public class TaskController {
	
	@PostMapping("/tasks")
	public ResponseEntity<TaskCreateResponse> createTask(@RequestBody TaskCreateRequest request) {
		
		
		
		return ResponseEntity.ok().build();
	}
}
