package software.blacknode.backend.api.controller.channel.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import software.blacknode.backend.api.controller.request.PatchRequest;

@Getter
@NoArgsConstructor
@ToString
public class ChannelPatchRequest extends PatchRequest {

	private String name;
	private String description;
	private String color;
	
}
