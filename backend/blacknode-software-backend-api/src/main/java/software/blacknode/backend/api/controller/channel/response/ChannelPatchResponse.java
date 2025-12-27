package software.blacknode.backend.api.controller.channel.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.api.controller.channel.response.content.ChannelResponseContent;
import software.blacknode.backend.api.controller.response.impl.ResponseBySetter;

@Getter
@SuperBuilder
public class ChannelPatchResponse extends ChannelResponseContent implements ResponseBySetter<ChannelPatchResponse> {
	
	@Getter @Setter private Status status;
	@Getter @Setter private String message;
	
}
