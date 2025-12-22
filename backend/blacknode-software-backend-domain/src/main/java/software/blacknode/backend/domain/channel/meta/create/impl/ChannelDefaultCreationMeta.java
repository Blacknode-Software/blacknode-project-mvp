package software.blacknode.backend.domain.channel.meta.create.impl;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.channel.meta.create.ChannelCreationMeta;

@Getter
@Builder
@ToString
public class ChannelDefaultCreationMeta implements ChannelCreationMeta {

	private final String name;
	
	private final String description;
	
	private final String color;
	
	private final HUID projectId;
	
	public Optional<String> getName() {
		return Optional.ofNullable(name);
	}
	
	public Optional<String> getDescription() {
		return Optional.ofNullable(description);
	}
	
	public Optional<String> getColor() {
		return Optional.ofNullable(color);
	}

}
