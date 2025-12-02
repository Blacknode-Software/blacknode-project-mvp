package software.blacknode.backend.api.controller.member;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import software.blacknode.backend.api.controller.BaseController;
import software.blacknode.backend.api.controller.member.response.MemberDeleteResponse;
import software.blacknode.backend.api.controller.member.response.MemberResponse;

@RestController
@Tag(name = "Members", description = "Member management APIs")
public class MemberController extends BaseController {

	@Operation(summary = "Create a new member")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Member created"),
			@ApiResponse(responseCode = "400", description = "Invalid input") })
	@PostMapping("/organization/{organizationId}/members")
	public ResponseEntity<MemberResponse> createMember(@PathVariable UUID organizationId, @RequestBody MemberResponse request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(null);
	}
	
	@Operation(summary = "Get a member by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Found the member"),
			@ApiResponse(responseCode = "404", description = "Member not found") })
	@GetMapping("/members/{memberId}")
	public ResponseEntity<MemberResponse> getMember(@PathVariable UUID id) {
		return ResponseEntity.ok(null);
	}
	
	@Operation(summary = "Delete a member")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Member deleted"),
			@ApiResponse(responseCode = "404", description = "Member not found") })
	@DeleteMapping("/members/{memberId}")
	public ResponseEntity<MemberDeleteResponse> deleteMember(@PathVariable UUID id) {
		return ResponseEntity.ok(null);
	}
	
}