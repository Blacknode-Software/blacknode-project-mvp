package software.blacknode.backend.domain.auth.repository;

import java.util.Optional;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.auth.Auth;

public interface AuthRepository {

	Optional<Auth> findById(HUID accountId, HUID id);

	void save(HUID accountId, Auth auth);
}
