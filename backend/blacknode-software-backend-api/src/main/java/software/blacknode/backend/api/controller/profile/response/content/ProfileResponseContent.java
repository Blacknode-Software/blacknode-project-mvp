package software.blacknode.backend.api.controller.profile.response.content;

import java.util.UUID;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ProfileResponseContent {

	private UUID memberId;
	
	private String displayName;
	
	private String email;
	
}
