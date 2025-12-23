package software.blacknode.backend.api.controller.channel.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import software.blacknode.backend.api.controller.request.BaseRequest;

@Getter
@NoArgsConstructor
public class ChannelCreateRequest extends BaseRequest {

	private String name;
	private String description;
	private String color;
	
}
