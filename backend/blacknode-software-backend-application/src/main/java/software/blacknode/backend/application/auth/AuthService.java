package software.blacknode.backend.application.auth;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.auth.Auth;
import software.blacknode.backend.domain.auth.repository.AuthRepository;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthRepository repository;
	
	public Auth create(HUID accountId, CreationMeta meta) {
		var auth = new Auth(accountId);
		
		auth.create(meta);
		
		repository.save(auth);
		
		return auth;
	}
}
