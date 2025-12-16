package software.blacknode.backend.api.controller.setup;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import software.blacknode.backend.api.controller.annotation.InvalidInputResponse;
import software.blacknode.backend.api.controller.annotation.NotFoundResponse;
import software.blacknode.backend.api.controller.organization.annotation.OrganizationHeader;
import software.blacknode.backend.api.controller.setup.converter.InitialSetupRequestConverter;
import software.blacknode.backend.api.controller.setup.converter.InitialSetupResponseConverter;
import software.blacknode.backend.api.controller.setup.request.InitialSetupRequest;
import software.blacknode.backend.api.controller.setup.response.InitialSetupResponse;
import software.blacknode.backend.application.setup.usecase.InitialSetupUseCase;

@RequiredArgsConstructor
@RestController	
@Tag(name = "Setup", description = "Endpoints related to application setup")
public class SetupController {
	
	private final InitialSetupUseCase initialSetupUseCase;
	private final InitialSetupRequestConverter initialSetupRequestConverter;
	private final InitialSetupResponseConverter initialSetupResponseConverter;

	
	@OrganizationHeader
	@Operation(summary = "Initial setup of the application", description = "Performs the initial setup process for the application using the provided setup request.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Setup completed successfully") })
	@InvalidInputResponse
	@PostMapping("/setup")
	public ResponseEntity<InitialSetupResponse> setup(@RequestBody InitialSetupRequest request) {
		var command = initialSetupRequestConverter.convert(request);
		
		var result = initialSetupUseCase.execute(command);
		
		var response = initialSetupResponseConverter.convert(result);
		
		return response.toOkResponse("Setup completed successfully");
	}
	

}