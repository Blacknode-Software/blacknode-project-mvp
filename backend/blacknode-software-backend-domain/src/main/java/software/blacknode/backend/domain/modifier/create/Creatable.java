package software.blacknode.backend.domain.modifier.create;

import java.util.Optional;

import me.hinsinger.projects.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.modifier.create.meta.CreationMeta;

public interface Creatable {

    Timestamp getCreationTimestamp();
    
    void create(Optional<CreationMeta> meta);

    default void create(CreationMeta meta) {
		create(Optional.of(meta));
	}
    
    default void create() {
    	create(Optional.empty());
    }

}