package software.blacknode.backend.infrastructure.account;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.account.Account;
import software.blacknode.backend.domain.account.repository.AccountRepository;
import software.blacknode.backend.infrastructure.account.entity.AccountEntity;
import software.blacknode.backend.infrastructure.account.entity.mapper.AccountEntityMapper;
import software.blacknode.backend.infrastructure.account.entity.repository.AccountEntityRepository;
import software.blacknode.backend.infrastructure.entity.state.EntityState;
import software.blacknode.backend.infrastructure.repository.InfrastructureRepository;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository, InfrastructureRepository<Account, AccountEntity> {

	private final AccountEntityRepository repository;
	private final AccountEntityMapper mapper;
	
	@Override
	public Optional<Account> findById(HUID id) {
		var account = repository.queryByIdAndState(id.toUUID(), EntityState.ACTIVE);
	
		return account.map(this::toDomainEntity);
	}

	@Override
	public Optional<Account> findByEmail(String email) {
		var account = repository.queryByEmailAndState(email, EntityState.ACTIVE);
		
		return account.map(this::toDomainEntity);
	}
	
	@Override
	public void save(Account account) {
		var accountEntity = toInfrastructureEntity(account);
		
		repository.save(accountEntity);
	}
	
	public AccountEntity toInfrastructureEntity(Account domainEntity) {
		return mapper.toInfrastructureEntity(domainEntity);
	}

	@Override
	public Account toDomainEntity(AccountEntity infrastructureEntity) {
		return mapper.toDomainEntity(infrastructureEntity);
	}

}
