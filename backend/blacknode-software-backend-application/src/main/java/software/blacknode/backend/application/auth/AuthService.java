package software.blacknode.backend.application.auth;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.blacknode.backend.domain.auth.Auth;
import software.blacknode.backend.domain.auth.repository.AuthRepository;
import software.blacknode.backend.domain.modifier.create.meta.CreationMeta;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthRepository repository;
	
	public Auth create(CreationMeta meta) {
		var auth = new Auth();
		
		auth.create(meta);
		
		repository.save(auth);
		
		return auth;
	}
}
