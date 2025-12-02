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

import software.blacknode.backend.api.controller.member.response.MemberDeleteResponse;
import software.blacknode.backend.api.controller.member.response.MemberResponse;

@RestController
public class MemberController {

	@PostMapping("/organization/{organizationId}/members")
	public ResponseEntity<MemberResponse> createMember(@PathVariable UUID organizationId, @RequestBody MemberResponse request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(null);
	}
	
	@GetMapping("/members/{memberId}")
	public ResponseEntity<MemberResponse> getMember(@PathVariable UUID id) {
		return ResponseEntity.ok(null);
	}
	
	@DeleteMapping("/members/{memberId}")
	public ResponseEntity<MemberDeleteResponse> deleteMember(@PathVariable UUID id) {
		return ResponseEntity.ok(null);
	}
	
}