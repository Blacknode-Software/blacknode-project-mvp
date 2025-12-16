package software.blacknode.backend.domain.channel.meta.create;

import lombok.Builder;
import lombok.Getter;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.modifier.create.meta.CreationMeta;

@Getter
@Builder
public class ChannelInitialCreationMeta implements CreationMeta {

	private String name;
	
	private String description;
	
	@Builder.Default
	private String color = "#FAFAFA";
	
	private HUID projectId;
}
