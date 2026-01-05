package software.blacknode.backend.application.account;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.blacknode.backend.domain.account.Account;
import software.blacknode.backend.domain.account.repository.AccountRepository;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;

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
	
	public Optional<Account> getByEmail(String email) {
		return repository.findByEmail(email);
	}
	
}
