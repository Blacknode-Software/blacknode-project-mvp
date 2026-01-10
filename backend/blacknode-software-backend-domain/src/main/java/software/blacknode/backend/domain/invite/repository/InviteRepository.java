package software.blacknode.backend.domain.invite.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.invite.Invite;

public interface InviteRepository {

	Optional<Invite> findById(HUID organizationId, HUID id);
	
	Optional<Invite> findByEmail(HUID organizationId, String email);
	
	Optional<Invite> findByToken(HUID organizationId, String token);
	
	List<Invite> findAll(HUID organizationId);
	
	List<Invite> findByIds(HUID organizationId, Set<HUID> ids);
	
	void save(HUID organizationId, Invite invite);
}
