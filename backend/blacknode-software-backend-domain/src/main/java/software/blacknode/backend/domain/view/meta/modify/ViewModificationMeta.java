package software.blacknode.backend.domain.view.meta.modify;

import java.util.Optional;

import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;
import software.blacknode.backend.domain.view.View;

public interface ViewModificationMeta extends ModificationMeta {
	
	default Optional<String> getName() { return Optional.empty(); };
	
	default Optional<View.Type> getType() { return Optional.empty(); };
	
}
