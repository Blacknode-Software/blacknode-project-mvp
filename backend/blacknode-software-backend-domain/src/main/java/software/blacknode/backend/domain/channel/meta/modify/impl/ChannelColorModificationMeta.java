package software.blacknode.backend.domain.channel.meta.modify.impl;

import java.util.Optional;

import lombok.Builder;
import lombok.ToString;
import software.blacknode.backend.domain.channel.meta.modify.ChannelModificationMeta;

@Builder
@ToString
public class ChannelColorModificationMeta implements ChannelModificationMeta {
	
	private final String color;
	
	@Override
	public Optional<String> getColor() {
		return Optional.ofNullable(color);
	}
}
