package software.blacknode.backend.application.account;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.blacknode.backend.domain.account.Account;
import software.blacknode.backend.domain.account.repository.AccountRepository;
import software.blacknode.backend.domain.modifier.create.meta.CreationMeta;

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
	
}
