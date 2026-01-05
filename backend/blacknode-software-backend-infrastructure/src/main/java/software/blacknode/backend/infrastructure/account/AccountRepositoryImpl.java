package software.blacknode.backend.infrastructure.account;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.account.Account;
import software.blacknode.backend.domain.account.repository.AccountRepository;
import software.blacknode.backend.infrastructure.account.entity.AccountEntity;
import software.blacknode.backend.infrastructure.account.entity.repository.AccountEntityRepository;
import software.blacknode.backend.infrastructure.repository.InfrastructureRepository;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository, InfrastructureRepository<Account, AccountEntity> {

	private final AccountEntityRepository accountEntityRepository;
	
	@Override
	public Optional<Account> findById(HUID id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void save(Account account) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<Account> findByEmail(String email) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}
	
	public AccountEntity toInfrastructureEntity(Account domainEntity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account toDomainEntity(AccountEntity infrastructureEntity) {
		// TODO Auto-generated method stub
		return null;
	}

}
