package software.blacknode.backend.infrastructure.invite;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.invite.Invite;
import software.blacknode.backend.domain.invite.repository.InviteRepository;
import software.blacknode.backend.infrastructure.entity.state.EntityState;
import software.blacknode.backend.infrastructure.invite.entity.InviteEntity;
import software.blacknode.backend.infrastructure.invite.entity.mapper.InviteEntityMapper;
import software.blacknode.backend.infrastructure.invite.entity.repository.InviteEntityRepository;
import software.blacknode.backend.infrastructure.organization.related.repository.OrganizationRelatedEntityRepository;

@Repository
@RequiredArgsConstructor
public class InviteRepositoryImpl implements InviteRepository, OrganizationRelatedEntityRepository<Invite, InviteEntity> {

	private final InviteEntityRepository repository;
	private final InviteEntityMapper mapper;
	
	@Override
	public Optional<Invite> findByToken(String token) {
		var invite = repository.findByTokenAndState(
				token, EntityState.ACTIVE);
	
		return invite.map(this::toDomainEntity);
	}
	
	@Override
	public Optional<Invite> findById(HUID organizationId, HUID id) {
		var invite = repository.queryByIdAndOrganizationIdAndState(
				id.toUUID(), 
				organizationId.toUUID(), 
				EntityState.ACTIVE
		);
		
		return invite.map(this::toDomainEntity);
	}

	@Override
	public List<Invite> findAllByEmail(HUID organizationId, String email) {
		var invites = repository.findAllByOrganizationIdAndState(
				organizationId.toUUID(), EntityState.ACTIVE);
	
		return invites.stream()
				.map(this::toDomainEntity)
				.filter(invite -> invite.getMeta()
						.getEmail()
						.equalsIgnoreCase(email))
				.toList();
	}

	@Override
	public List<Invite> findAll(HUID organizationId) {
		var invites = repository.findAllByOrganizationIdAndState(
				organizationId.toUUID(), EntityState.ACTIVE);
		
		return invites.stream()
				.map(this::toDomainEntity)
				.toList();
	}

	@Override
	public List<Invite> findByIds(HUID organizationId, Set<HUID> ids) {
		var uuidIds = ids.stream()
				.map(HUID::toUUID)
				.toList();
		
		var invites = repository.findAllByIdInAndOrganizationIdAndState(
				uuidIds, 
				organizationId.toUUID(), 
				EntityState.ACTIVE
		);
		
		return invites.stream()
				.map(this::toDomainEntity)
				.toList();
	}

	@Override
	public void save(HUID organizationId, Invite invite) {
		invite.ensureBelongsToOrganization(organizationId);
		
		var inviteEntity = toInfrastructureEntity(invite);
		
		repository.save(inviteEntity);
	}
	
	@Override
	public InviteEntity toInfrastructureEntity(Invite domainEntity) {
		return mapper.toInfrastructureEntity(domainEntity);
	}

	@Override
	public Invite toDomainEntity(InviteEntity infrastructureEntity) {
		return mapper.toDomainEntity(infrastructureEntity);
	}

}
