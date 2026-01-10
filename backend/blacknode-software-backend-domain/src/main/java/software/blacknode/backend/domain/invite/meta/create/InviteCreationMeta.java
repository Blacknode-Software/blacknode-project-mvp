package software.blacknode.backend.domain.invite.meta.create;

import java.util.Optional;

import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;

public interface InviteCreationMeta extends CreationMeta {

	public Optional<Timestamp> getExpiresAt();

	public String getEmail();
	
}
