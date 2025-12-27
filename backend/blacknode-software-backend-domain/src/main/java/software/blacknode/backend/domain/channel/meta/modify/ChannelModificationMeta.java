package software.blacknode.backend.domain.channel.meta.modify;

import java.util.Optional;

import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;

public interface ChannelModificationMeta extends ModificationMeta {
	
	default Optional<String> getName() { return Optional.empty(); }
	default Optional<String> getDescription() { return Optional.empty(); }
	default Optional<String> getColor() { return Optional.empty(); }

}
