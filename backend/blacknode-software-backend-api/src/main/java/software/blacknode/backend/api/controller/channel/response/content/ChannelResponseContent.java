package software.blacknode.backend.api.controller.channel.response.content;

import java.util.UUID;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ChannelResponseContent {

	private UUID id;
	private String name;
	private String description;
	private String color;
	
}
