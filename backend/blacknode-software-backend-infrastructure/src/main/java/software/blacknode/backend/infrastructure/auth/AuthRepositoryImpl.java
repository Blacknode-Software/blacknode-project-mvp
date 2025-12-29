package software.blacknode.backend.infrastructure.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.auth.Auth;
import software.blacknode.backend.domain.auth.repository.AuthRepository;
import software.blacknode.backend.infrastructure.auth.entity.AuthEntity;
import software.blacknode.backend.infrastructure.auth.entity.mapper.AuthEntityMapper;
import software.blacknode.backend.infrastructure.auth.entity.repository.AuthEntityRepository;
import software.blacknode.backend.infrastructure.entity.state.EntityState;
import software.blacknode.backend.infrastructure.repository.InfrastructureRepository;

@Repository
@RequiredArgsConstructor
public class AuthRepositoryImpl implements AuthRepository, InfrastructureRepository<Auth, AuthEntity>  {

	private final AuthEntityRepository repository;
	private final AuthEntityMapper mapper;
	
	@Override
	public Optional<Auth> findById(HUID accountId, HUID id) {
		var auth = repository.queryByIdAndAccountIdAndState(id.toUUID(), 
				accountId.toUUID(), EntityState.ACTIVE);
		
		return auth.map(this::toDomainEntity);
	}

	@Override
	public List<Auth> findAllByAccountId(HUID accountId) {
		var auths = repository.queryAllByAccountIdAndState(
				accountId.toUUID(), EntityState.ACTIVE);
		
		return auths.stream()
				.map(this::toDomainEntity)
				.toList();
	}

	@Override
	public List<Auth> findAllByAccountIdAndTypeId(HUID accountId, HUID typeId) {
		var auths = repository.queryAllByAccountIdAndState(
				accountId.toUUID(), EntityState.ACTIVE);
		
		// TODO optimize query to filter by typeId in the database query
		
		return auths.stream()
				.map(this::toDomainEntity)
				.filter(auth -> auth.getMethod().getType().getId().equals(typeId))
				.toList();
	}

	@Override
	public void save(HUID accountId, Auth auth) {
		auth.ensureBelongsToAccount(accountId);
		
		var authEntity = toInfrastructureEntity(auth);
		
		repository.save(authEntity);
	}

	@Override
	public AuthEntity toInfrastructureEntity(Auth domainEntity) {
		return mapper.toInfrastructureEntity(domainEntity);
	}

	@Override
	public Auth toDomainEntity(AuthEntity infrastructureEntity) {
		return mapper.toDomainEntity(infrastructureEntity);
	}


}
