package software.blacknode.backend.infrastructure.account;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.account.Account;
import software.blacknode.backend.domain.account.repository.AccountRepository;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

	@Override
	public Optional<Account> findById(HUID id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void save(Account account) {
		// TODO Auto-generated method stub
		
	}

}
