package software.blacknode.backend.domain.channel.meta;

import lombok.Builder;
import lombok.Getter;
import lombok.With;

@Getter
@With
@Builder
public class ChannelMeta {

	@Builder.Default
	private String name = "Unknown Channel";
	
	@Builder.Default
	private String description = "Unknown description";
	
	@Builder.Default
	private String color = "#FAFAFA";
}
