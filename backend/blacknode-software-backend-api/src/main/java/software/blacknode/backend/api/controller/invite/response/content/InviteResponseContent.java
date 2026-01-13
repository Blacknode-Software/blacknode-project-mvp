package software.blacknode.backend.api.controller.invite.response.content;

import java.util.UUID;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class InviteResponseContent {

	private UUID id;
	
	private String email;
	
	private String token;
	
	private boolean revoked;

	private boolean expired;
	private Long expiresAt;
	
	private boolean claimed;
	private UUID claimedBy;
	private Long claimedAt; 
	
}
