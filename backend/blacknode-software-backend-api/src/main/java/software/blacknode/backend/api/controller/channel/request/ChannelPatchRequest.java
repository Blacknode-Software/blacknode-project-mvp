package software.blacknode.backend.api.controller.channel.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import software.blacknode.backend.api.controller.request.PatchRequest;

@Getter
@NoArgsConstructor
public class ChannelPatchRequest extends PatchRequest {

	private String name;
	private String description;
	private String color;
	
}
