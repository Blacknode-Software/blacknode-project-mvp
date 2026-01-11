package software.blacknode.backend.api.controller.profile;

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
import software.blacknode.backend.api.controller.profile.mapper.ProfilesBatchFetchMapper;
import software.blacknode.backend.api.controller.profile.mapper.impl.ProfileFetchMapper;
import software.blacknode.backend.api.controller.profile.request.ProfilesBatchFetchRequest;
import software.blacknode.backend.api.controller.profile.response.ProfileResponse;
import software.blacknode.backend.api.controller.profile.response.ProfilesBatchFetchResponse;
import software.blacknode.backend.application.profile.command.ProfileFetchCommand;
import software.blacknode.backend.application.profile.usecase.ProfileFetchUseCase;
import software.blacknode.backend.application.profile.usecase.ProfilesBatchFetchUseCase;

@BearerAuth
@RestController
@RequiredArgsConstructor
@Tag(name = "Profiles", description = "Profile management APIs")
public class ProfileController {
	
	private final ProfileFetchMapper profileFetchMapper;
	private final ProfileFetchUseCase profileFetchUseCase;
	
	private final ProfilesBatchFetchMapper profilesBatchFetchMapper;
	private final ProfilesBatchFetchUseCase profilesBatchFetchUseCase;
	
	@OrganizationHeader
	@Operation(summary = "Fetch Profile", description = "Fetch a profile by member ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Profile fetched" ) })
	@GetMapping("/profiles/{memberId}")
	public ResponseEntity<ProfileResponse> getProfile(@PathVariable UUID memberId) {
		var command = ProfileFetchCommand.builder()
				.memberId(HUID.fromUUID(memberId))
				.build();
		
		var result = profileFetchUseCase.execute(command);
		
		var response = profileFetchMapper.toResponse(result);
		
		return response.toOkResponse("Profile fetched successfully");
	}
	
	@OrganizationHeader
	@Operation(summary = "Fetch Profiles Batch", description = "Fetch multiple profiles by their member IDs")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Profiles batch fetched" ) })
	@PostMapping("/profiles/batch")
	public ResponseEntity<ProfilesBatchFetchResponse> getProfilesBatch(@RequestBody ProfilesBatchFetchRequest request) {
		var command = profilesBatchFetchMapper.toCommand(request);
		
		var result = profilesBatchFetchUseCase.execute(command);
		
		var response = profilesBatchFetchMapper.toResponse(result);
		
		return response.toOkResponse("Profiles batch fetched successfully");
	}

}
