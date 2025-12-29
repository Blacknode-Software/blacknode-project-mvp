package software.blacknode.backend.domain.auth.repository;

import java.util.List;
import java.util.Optional;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.auth.Auth;

public interface AuthRepository {

	Optional<Auth> findById(HUID accountId, HUID id);

	List<Auth> findAllByAccountId(HUID accountId);
	
	List<Auth> findAllByAccountIdAndTypeId(HUID accountId, HUID typeId);
	
	void save(HUID accountId, Auth auth);
	
}
