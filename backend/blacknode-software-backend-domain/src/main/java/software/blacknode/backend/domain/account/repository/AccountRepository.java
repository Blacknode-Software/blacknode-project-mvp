package software.blacknode.backend.domain.account.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.account.Account;

public interface AccountRepository {
	
	Optional<Account> findByEmail(String email);

	Optional<Account> findById(HUID id);
	
	List<Account> findByIds(Set<HUID> ids);

	void save(Account account);
	
}
