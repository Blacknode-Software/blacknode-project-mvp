package software.blacknode.backend.domain.invite.meta.modify.impl;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import me.hinsinger.hinz.common.huid.HUID;
import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.invite.meta.modify.InviteModificationMeta;

@Getter
@Builder
public class InviteClaimModificationMeta implements InviteModificationMeta{

	@NonNull
	private final HUID memberId;
	
	@Override
	public boolean isClaimedBySet() {
		return true;
	}
	
	@Override
	public boolean isClaimedAtSet() {
		return true;
	}
	
	@Override
	public Optional<HUID> getClaimedBy() {
		return Optional.of(memberId);
	}
	
	@Override
	public Optional<Timestamp> getClaimedAt() {
		return Optional.of(Timestamp.now());
	}
	
}
