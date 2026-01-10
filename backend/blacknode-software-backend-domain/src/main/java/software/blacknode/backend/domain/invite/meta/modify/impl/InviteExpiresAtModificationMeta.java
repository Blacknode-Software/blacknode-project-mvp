package software.blacknode.backend.domain.invite.meta.modify.impl;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.invite.meta.modify.InviteModificationMeta;

@Getter
@Builder
public class InviteExpiresAtModificationMeta implements InviteModificationMeta {

	private final Timestamp expiresAt;
	
	public Optional<Timestamp> getExpiresAt() {
		return Optional.ofNullable(expiresAt);
	}
	
}
