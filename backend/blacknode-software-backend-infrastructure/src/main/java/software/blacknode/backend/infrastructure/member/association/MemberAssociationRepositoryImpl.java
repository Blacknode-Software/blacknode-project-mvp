package software.blacknode.backend.infrastructure.member.association;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.member.association.MemberAssociation;
import software.blacknode.backend.domain.member.association.MemberAssociation.Scope;
import software.blacknode.backend.domain.member.association.repository.MemberAssociationRepository;
import software.blacknode.backend.infrastructure.entity.state.EntityState;
import software.blacknode.backend.infrastructure.member.association.entity.MemberAssociationEntity;
import software.blacknode.backend.infrastructure.member.association.entity.mapper.MemberAssociationEntityMapper;
import software.blacknode.backend.infrastructure.member.association.entity.repository.MemberAssociationEntityRepository;
import software.blacknode.backend.infrastructure.organization.related.repository.OrganizationRelatedEntityRepository;

@Repository
@RequiredArgsConstructor
public class MemberAssociationRepositoryImpl implements MemberAssociationRepository, OrganizationRelatedEntityRepository<MemberAssociation, software.blacknode.backend.infrastructure.member.association.entity.MemberAssociationEntity> {

	private final MemberAssociationEntityRepository repository;
	private final MemberAssociationEntityMapper mapper;
	
	@Override
	public Optional<MemberAssociation> findById(HUID organizationId, HUID id) {
		var association = repository.queryByIdAndOrganizationIdAndState(
				id.toUUID(),
				organizationId.toUUID(),
				EntityState.ACTIVE
			);
		
		return association.map(this::toDomainEntity);
	}

	@Override
	public List<MemberAssociation> findAll(HUID organizationId) {
		var associations = repository.queryAllByOrganizationIdAndState(
				organizationId.toUUID(),
				EntityState.ACTIVE
			);
		
		return associations.stream()
				.map(this::toDomainEntity)
				.toList();
	}

	@Override
	public List<MemberAssociation> findByMemberIds(HUID organizationId, Set<HUID> memberIds) {
		var uuidMemberIds = memberIds.stream()
				.map(HUID::toUUID)
				.toList();
		
		var associations = repository.queryByOrganizationIdAndMemberIdsAndState(
				organizationId.toUUID(),
				uuidMemberIds,
				EntityState.ACTIVE
			);
		
		return associations.stream()
				.map(this::toDomainEntity)
				.toList();	
	}

	@Override
	public List<MemberAssociation> findByMemberId(HUID organizationId, HUID memberId) {
		var associations = repository.queryByOrganizationIdAndMemberIdAndState(
				organizationId.toUUID(),
				memberId.toUUID(),
				EntityState.ACTIVE
			);
		
		return associations.stream()
				.map(this::toDomainEntity)
				.toList();
	}

	@Override
	public List<MemberAssociation> findByMemberIdAndScope(HUID organizationId, HUID memberId, Scope scope) {
		var associations = repository.queryByOrganizationIdAndMemberIdAndScopeAndState(
				organizationId.toUUID(),
				memberId.toUUID(),
				scope.name(),
				EntityState.ACTIVE
			);
		
		return associations.stream()
				.map(this::toDomainEntity)
				.toList();
	}

	@Override
	public Optional<MemberAssociation> findByMemberIdAndScopeIdAndScope(HUID organizationId, HUID memberId,
			HUID scopeId, Scope scope) {
		var association = repository.queryByOrganizationIdAndMemberIdAndScopeIdAndScopeAndState(
				organizationId.toUUID(),
				memberId.toUUID(),
				scopeId.toUUID(),
				scope.name(),
				EntityState.ACTIVE
			);
		
		return association.map(this::toDomainEntity);
	}
	
	@Override
	public List<MemberAssociation> findBydRoleId(HUID organizationId, HUID roleId) {
		var associations = repository.queryByOrganizationIdRoleIdAndState(
				organizationId.toUUID(),
				roleId.toUUID(),
				EntityState.ACTIVE
			);
		
		return associations.stream()
				.map(this::toDomainEntity)
				.toList();
	}

	@Override
	public void save(HUID organizationId, MemberAssociation association) {
		association.ensureBelongsToOrganization(organizationId);
		
		var associationEntity = toInfrastructureEntity(association);
		
		repository.save(associationEntity);
	}

	@Override
	public MemberAssociationEntity toInfrastructureEntity(MemberAssociation domainEntity) {
		return mapper.toInfrastructureEntity(domainEntity);
	}

	@Override
	public MemberAssociation toDomainEntity(MemberAssociationEntity infrastructureEntity) {
		return mapper.toDomainEntity(infrastructureEntity);
	}
}
