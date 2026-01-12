package software.blacknode.backend.application.profile.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;

@Getter
@Builder
@ToString
public class ProfileDTO {

	@NonNull
	private final HUID memberId;
	
	@NonNull
	private final String displayName;
	
	@NonNull
	private final String email;
	
}
