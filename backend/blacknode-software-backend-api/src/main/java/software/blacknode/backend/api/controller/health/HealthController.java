package software.blacknode.backend.api.controller.health;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import software.blacknode.backend.api.controller.health.mapper.HealthFetchMapper;
import software.blacknode.backend.api.controller.health.response.HealthResponse;
import software.blacknode.backend.api.controller.organization.annotation.OrganizationHeader;
import software.blacknode.backend.application.health.commnad.HealthFetchCommand;
import software.blacknode.backend.application.health.usecase.HealthFetchUseCase;

@RestController
@RequiredArgsConstructor
@Tag(name = "Health", description = "Health Check API")
public class HealthController {

	private final HealthFetchMapper healthFetchMapper;
	
	private final HealthFetchUseCase healthFetchUseCase;
	
	@Operation(summary = "Fetch Health", description = "Fetch the health status of the application")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Health fetched successfully"), })
	@GetMapping("/health")
	public ResponseEntity<HealthResponse> fetchHealth() {
		var command = HealthFetchCommand.builder().build();
		
		var result = healthFetchUseCase.execute(command);
		
		var response = healthFetchMapper.toResponse(result);
		
		return response.toOkResponse("Health fetched successfully");
	}
	
}
