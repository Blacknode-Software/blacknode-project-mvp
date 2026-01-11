package software.blacknode.backend.application.profile.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ProfileDTO {

	@NonNull
	private final String displayName;
	
	@NonNull
	private final String email;
	
}
