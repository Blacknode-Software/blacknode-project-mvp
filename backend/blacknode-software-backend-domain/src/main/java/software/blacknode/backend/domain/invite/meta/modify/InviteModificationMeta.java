package software.blacknode.backend.domain.invite.meta.modify;

import java.util.Optional;

import me.hinsinger.hinz.common.huid.HUID;
import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;

public interface InviteModificationMeta extends ModificationMeta {

	public default Optional<String> getEmail() { return Optional.empty(); };
	
	public default Optional<Timestamp> getExpiresAt() { return Optional.empty(); };
	
	public default Optional<Boolean> isRevoked() { return Optional.empty(); };
	
	public default boolean isClaimedAtSet() { return false; };
	
	public default Optional<Timestamp> getClaimedAt() { return Optional.empty(); };

	public default boolean isClaimedBySet() { return false; };
	
	public default Optional<HUID> getClaimedBy() { return Optional.empty(); };
}
