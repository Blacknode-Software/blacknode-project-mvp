package software.blacknode.backend.domain.account.repository;

import java.util.Optional;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.account.Account;

public interface AccountRepository {
	
	Optional<Account> findByEmail(String email);

	Optional<Account> findById(HUID id);

	void save(Account account);
	
}
