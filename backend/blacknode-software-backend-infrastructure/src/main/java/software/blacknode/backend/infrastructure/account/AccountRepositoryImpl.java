package software.blacknode.backend.infrastructure.account;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Repository;

import lombok.NonNull;
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
	public Optional<Account> findById(@NonNull HUID id) {
		var account = repository.queryByIdAndState(id.toUUID(), EntityState.ACTIVE);
	
		return account.map(this::toDomainEntity);
	}

	@Override
	public Optional<Account> findByEmail(@NonNull String email) {
		var account = repository.queryByEmailAndState(email, EntityState.ACTIVE);
		
		return account.map(this::toDomainEntity);
	}
	
	public List<Account> findByIds(@NonNull Set<HUID> ids) {
		var uuidIds = ids.stream().map(HUID::toUUID).toList();
		
		var accounts = repository.queryAllByIdInAndState(uuidIds, EntityState.ACTIVE);
		
		return accounts.stream().map(this::toDomainEntity).toList();
	}
	
	@Override
	public void save(@NonNull Account account) {
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
