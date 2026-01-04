package software.blacknode.backend.api.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import software.blacknode.backend.api.controller.auth.mapper.AuthenticateWithPasswordMapper;
import software.blacknode.backend.api.controller.auth.request.AuthenticateWithPasswordRequest;
import software.blacknode.backend.api.controller.auth.response.AuthenticateWithPasswordResponse;
import software.blacknode.backend.api.service.jwt.JwtTokenService;
import software.blacknode.backend.application.auth.usecase.AuthenticateWithPasswordUseCase;

@RestController
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication and authorization")
public class AuthController {
	
	private final JwtTokenService jwtTokenService;
	
	private final AuthenticateWithPasswordMapper authenticateWithPasswordMapper;
	private final AuthenticateWithPasswordUseCase authenticateWithPasswordUseCase;

	@Operation(summary = "Authenticate with Password", description = "Authenticate a user using their email and password.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Authentication successful") })
	@PostMapping("/auth/password")
	public ResponseEntity<AuthenticateWithPasswordResponse> authenticateWithPassword(@RequestBody AuthenticateWithPasswordRequest request) {
		var command = authenticateWithPasswordMapper.toCommand(request);
		
		var result = authenticateWithPasswordUseCase.execute(command);
		
		var accountId = result.getAccountId();
		var email = request.getEmail();
		
		var access = jwtTokenService.generateToken(accountId, email);
		
		var response = AuthenticateWithPasswordResponse.builder()
				.accessToken(access.getToken())
				.accessTokenExpiresAt(access.getExpiresAt())
				.build();
		
		return response.toOkResponse("Authenticated successfully!");
	}
	
}
