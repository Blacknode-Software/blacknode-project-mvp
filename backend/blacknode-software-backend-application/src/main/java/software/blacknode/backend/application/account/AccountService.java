package software.blacknode.backend.application.account;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.account.Account;
import software.blacknode.backend.domain.account.meta.delete.impl.AccountDefaultDeletionMeta;
import software.blacknode.backend.domain.account.repository.AccountRepository;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;
import software.blacknode.backend.domain.entity.modifier.impl.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;
import software.blacknode.backend.domain.exception.BlacknodeException;

@Service
@RequiredArgsConstructor
public class AccountService {

	private final AccountRepository repository;
	
	public Account create(CreationMeta meta) {
		var account = new Account();
		
		account.create(meta);
		
		repository.save(account);
		
		return account;
	}
	
	public Account modify(HUID id, ModificationMeta meta) {
		return modify(id, List.of(meta));
	}
	
	public Account modify(HUID id, List<ModificationMeta> meta) {
		var account = getOrThrow(id);
		
		for(var m : meta) {
			account.modify(m);
		}
		
		repository.save(account);
		
		return account;
	}
	
	public void delete(HUID id) {
		var meta = AccountDefaultDeletionMeta.builder().build();
		
		delete(id, meta);
	}
	
	public void delete(HUID id, DeletionMeta meta) {
		var account = getOrThrow(id);
		
		account.delete(meta);
		
		repository.save(account);
	}
	
	public Account getOrThrow(HUID id) {
		return repository.findById(id).orElseThrow(() -> new BlacknodeException("Account with ID " + id + " not found."));
	}
	
	public Optional<Account> get(HUID id) {
		return repository.findById(id);
	}
	
	public Optional<Account> getByEmail(String email) {
		return repository.findByEmail(email);
	}
	
}
