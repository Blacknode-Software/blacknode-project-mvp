package software.blacknode.backend.domain.channel.meta.create;

import java.util.Optional;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;

public interface ChannelCreationMeta extends CreationMeta {

	HUID getProjectId();
	
	default Optional<String> getName() { return Optional.empty(); }
	default Optional<String> getDescription() { return Optional.empty(); }
	default Optional<String> getColor() { return Optional.empty(); }
	
}
