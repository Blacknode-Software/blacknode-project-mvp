package software.blacknode.backend.domain.invite.meta.create.impl;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.invite.meta.create.InviteCreationMeta;

@Getter
@Builder
@ToString
public class InviteMemberCreationMeta implements InviteCreationMeta {

	private final Timestamp expiresAt;

	private final String email;
	
	public Optional<Timestamp> getExpiresAt() {
		return Optional.ofNullable(expiresAt);
	}
	
}

