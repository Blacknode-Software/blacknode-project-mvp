package software.blacknode.backend.application.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.auth.Auth;
import software.blacknode.backend.domain.auth.meta.delete.impl.AuthDefaultDeletionMeta;
import software.blacknode.backend.domain.auth.repository.AuthRepository;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;
import software.blacknode.backend.domain.entity.modifier.impl.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;
import software.blacknode.backend.domain.exception.BlacknodeException;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthRepository repository;
	
	public Auth create(HUID accountId, CreationMeta meta) {
		var auth = new Auth(accountId);
		
		auth.create(meta);
		
		repository.save(accountId, auth);
		
		return auth;
	}
	
	public Auth modify(HUID accountId, HUID authId, ModificationMeta meta) {
		return modify(accountId, authId, List.of(meta));
	}
	
	public Auth modify(HUID accountId, HUID authId, List<ModificationMeta> meta) {
		var auth = repository.findById(accountId, authId).orElseThrow();
		
		for(var m : meta) {
			auth.modify(m);
		}
		
		repository.save(accountId, auth);
		
		return auth;
	}
	
	public void delete(HUID accountId, HUID authId) {
		var meta = AuthDefaultDeletionMeta.builder().build();
		
		delete(accountId, authId, meta);
	}
	
	public void delete(HUID accountId, HUID authId, DeletionMeta meta) {
		var auth = repository.findById(accountId, authId).orElseThrow();
		
		auth.delete(meta);
		
		repository.save(accountId, auth);
	}
	
	public Auth getOrThrow(HUID accountId, HUID authId) {
		return get(accountId,  authId).orElseThrow(() -> 
			new BlacknodeException("Auth with ID " + authId + " not found.")
		);
	}
	
	public Optional<Auth> get(HUID accountId, HUID authId) {
		return repository.findById(accountId,  authId);
	}
	
}
