package software.blacknode.backend.domain.view.meta.create;

import java.util.Optional;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;
import software.blacknode.backend.domain.view.View;

public interface ViewCreationMeta extends CreationMeta{

	default Optional<String> getName() { return Optional.empty(); };
	
	default Optional<View.Type> getType() { return Optional.empty(); };
	
	HUID getChannelId();
}
