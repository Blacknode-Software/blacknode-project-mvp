package software.blacknode.backend.domain.channel.meta.modify.impl;

import java.util.Optional;

import lombok.Builder;
import software.blacknode.backend.domain.channel.meta.modify.ChannelModificationMeta;

@Builder
public class ChannelDescriptionModificationMeta implements ChannelModificationMeta {

	private final String description;

	@Override
	public Optional<String> getDescription() {
		return Optional.ofNullable(description);
	}

}
