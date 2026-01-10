package software.blacknode.backend.infrastructure.organization;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.organization.Organization;
import software.blacknode.backend.domain.organization.repository.OrganizationRepository;
import software.blacknode.backend.infrastructure.entity.state.EntityState;
import software.blacknode.backend.infrastructure.organization.entity.OrganizationEntity;
import software.blacknode.backend.infrastructure.organization.entity.mapper.OrganizationEntityMapper;
import software.blacknode.backend.infrastructure.organization.entity.repository.OrganizationEntityRepository;
import software.blacknode.backend.infrastructure.repository.InfrastructureRepository;

@Repository
@RequiredArgsConstructor
public class OrganizationRepositoryImpl implements OrganizationRepository, InfrastructureRepository<Organization, OrganizationEntity> {

	private final OrganizationEntityMapper organizationEntityMapper;
	private final OrganizationEntityRepository organizationEntityRepository;
	
	@Override
	public Optional<Organization> findById(@NonNull HUID id) {
		var organization = organizationEntityRepository.queryByIdAndState(id.toUUID(), EntityState.ACTIVE);
		
		return organization.map(this::toDomainEntity);
	}
	
	@Override
	public List<Organization> findAll() {
		var organizations = organizationEntityRepository.queryAllByState(EntityState.ACTIVE);
		
		return organizations.stream()
				.map(this::toDomainEntity)
				.toList();
	}

	@Override
	public void save(@NonNull Organization organization) {
		var organizationEntity = toInfrastructureEntity(organization);
		
		organizationEntityRepository.save(organizationEntity);
	}

	@Override
	public OrganizationEntity toInfrastructureEntity(Organization domainEntity) {
		return organizationEntityMapper.toInfrastructureEntity(domainEntity);
	}

	@Override
	public Organization toDomainEntity(OrganizationEntity infrastructureEntity) {
		return organizationEntityMapper.toDomainEntity(infrastructureEntity);
	}

}
