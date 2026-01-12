package software.blacknode.backend.api.controller.profile.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.api.controller.profile.response.content.ProfileResponseContent;
import software.blacknode.backend.api.controller.response.impl.ResponseBySetter;

@Getter
@SuperBuilder
public class ProfileResponse extends ProfileResponseContent implements ResponseBySetter<ProfileResponse> {
	
	@Getter @Setter private Status status;
	@Getter @Setter private String message;
	
}
