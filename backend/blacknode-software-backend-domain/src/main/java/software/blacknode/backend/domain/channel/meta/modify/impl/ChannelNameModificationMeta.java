package software.blacknode.backend.domain.channel.meta.modify.impl;

import java.util.Optional;

import lombok.Builder;
import software.blacknode.backend.domain.channel.meta.modify.ChannelModificationMeta;

@Builder
public class ChannelNameModificationMeta implements ChannelModificationMeta {
	
	private final String name;
	
	@Override
	public Optional<String> getName() {
		return Optional.ofNullable(name);
	}

}
