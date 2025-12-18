package software.blacknode.backend.infrastructure.auth;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.auth.Auth;
import software.blacknode.backend.domain.auth.repository.AuthRepository;

@Repository
public class AuthRepositoryImpl implements AuthRepository {

	@Override
	public Optional<Auth> findById(HUID id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void save(Auth auth) {
		// TODO Auto-generated method stub
		
	}

}
