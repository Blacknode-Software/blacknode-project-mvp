package software.blacknode.backend.infrastructure.role;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Repository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.role.Role;
import software.blacknode.backend.domain.role.repository.RoleRepository;
import software.blacknode.backend.infrastructure.entity.state.EntityState;
import software.blacknode.backend.infrastructure.organization.related.repository.OrganizationRelatedEntityRepository;
import software.blacknode.backend.infrastructure.role.entity.RoleEntity;
import software.blacknode.backend.infrastructure.role.entity.mapper.RoleEntityMapper;
import software.blacknode.backend.infrastructure.role.entity.repository.RoleEntityRepository;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository, OrganizationRelatedEntityRepository<Role, RoleEntity> {

	private final RoleEntityRepository repository;
	private final RoleEntityMapper mapper;
	
	
	@Override
	public Optional<Role> findById(@NonNull HUID organizationId, @NonNull HUID id) {
		var role = repository.queryByIdAndOrganizationIdAndState(id.toUUID(),
				organizationId.toUUID(), EntityState.ACTIVE);
		
		return role.map(this::toDomainEntity);
	}

	@Override
	public List<Role> findAllById(@NonNull HUID organizationId, @NonNull Set<HUID> ids) {
		var uuidIds = ids.stream()
				.map(HUID::toUUID)
				.toList();
		
		var roles = repository.queryByIdInAndOrganizationIdAndState(uuidIds,
				organizationId.toUUID(),
				EntityState.ACTIVE);
		
		return roles.stream()
				.map(this::toDomainEntity)
				.toList();
	}

	@Override
	public List<Role> findAll(@NonNull HUID organizationId) {
		var roles = repository.queryByOrganizationIdAndState(
				organizationId.toUUID(), EntityState.ACTIVE);
		
		return roles.stream()
				.map(this::toDomainEntity)
				.toList();
	}

	@Override
	public void save(@NonNull HUID organizationId, @NonNull Role role) {
		role.ensureBelongsToOrganization(organizationId);
		
		var roleEntity = toInfrastructureEntity(role);
		
		repository.save(roleEntity);
	}

	@Override
	public RoleEntity toInfrastructureEntity(Role domainEntity) {
		return mapper.toInfrastructureEntity(domainEntity);
	}

	@Override
	public Role toDomainEntity(RoleEntity infrastructureEntity) {
		return mapper.toDomainEntity(infrastructureEntity);
	}

}
