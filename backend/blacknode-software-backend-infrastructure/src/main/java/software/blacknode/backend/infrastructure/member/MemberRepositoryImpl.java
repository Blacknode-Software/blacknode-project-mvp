package software.blacknode.backend.infrastructure.member;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.member.Member;
import software.blacknode.backend.domain.member.repository.MemberRepository;
import software.blacknode.backend.infrastructure.entity.state.EntityState;
import software.blacknode.backend.infrastructure.member.entity.MemberEntity;
import software.blacknode.backend.infrastructure.member.entity.mapper.MemberEntityMapper;
import software.blacknode.backend.infrastructure.member.entity.repository.MemberEntityRepository;
import software.blacknode.backend.infrastructure.organization.related.repository.OrganizationRelatedEntityRepository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository, OrganizationRelatedEntityRepository<Member, MemberEntity> {
	
	private final MemberEntityRepository repository;
	private final MemberEntityMapper mapper;
	
	@Override
	public Optional<Member> findById(HUID organizationId, HUID id) {
		var member = repository.queryByIdAndOrganizationIdAndState(
				organizationId.toUUID(),
				id.toUUID(),
				EntityState.ACTIVE
			);
	
		return member.map(this::toDomainEntity);	
	}

	@Override
	public List<Member> findByIds(HUID organizationId, List<HUID> ids) {
		var uuidIds = ids.stream().map(HUID::toUUID).toList();
		
		var members = repository.queryAllByIdInOrganizationIdAndState(
				organizationId.toUUID(),
				uuidIds,
				EntityState.ACTIVE
			);
		
		return members.stream().map(this::toDomainEntity).toList();
	}

	@Override
	public List<Member> findByOrganizationId(HUID organizationId) {
		var members = repository.queryByOrganizationIdAndState(
				organizationId.toUUID(),
				EntityState.ACTIVE
			);
		
		return members.stream().map(this::toDomainEntity).toList();
	}

	@Override
	public Optional<Member> findByAccountId(HUID organizationId, HUID accountId) {
		var member = repository.queryByOrganizationIdAndAccountIdAndState(
				organizationId.toUUID(),
				accountId.toUUID(),
				EntityState.ACTIVE
			);
		
		return member.map(this::toDomainEntity);
	}

	@Override
	public void save(HUID organizationId, Member member) {
		member.ensureBelongsToOrganization(organizationId);
		
		var memberEntity = toInfrastructureEntity(member);
		
		repository.save(memberEntity);
	}

	@Override
	public MemberEntity toInfrastructureEntity(Member domainEntity) {
		return mapper.toInfrastructureEntity(domainEntity);
	}

	@Override
	public Member toDomainEntity(MemberEntity infrastructureEntity) {
		return mapper.toDomainEntity(infrastructureEntity);
	}


}
