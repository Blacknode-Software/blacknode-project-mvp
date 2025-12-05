package software.blacknode.backend.api.controller.setup;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import software.blacknode.backend.api.controller.setup.converter.InitialSetupRequestConverter;
import software.blacknode.backend.api.controller.setup.converter.InitialSetupResponseConverter;
import software.blacknode.backend.api.controller.setup.request.InitialSetupRequest;
import software.blacknode.backend.api.controller.setup.response.InitialSetupResponse;
import software.blacknode.backend.application.setup.usecase.InitialSetupUseCase;

@RequiredArgsConstructor
@RestController	
public class SetupController {
	
	private final InitialSetupUseCase initialSetupUseCase;
	private final InitialSetupRequestConverter initialSetupRequestConverter;
	private final InitialSetupResponseConverter initialSetupResponseConverter;

	public ResponseEntity<InitialSetupResponse> setup(@RequestBody InitialSetupRequest request) {
		var command = initialSetupRequestConverter.convert(request);
		
		var result = initialSetupUseCase.execute(command);
		
		var response = initialSetupResponseConverter.convert(result);
		
		return ResponseEntity.ok(response);
	}
	

}
