package software.blacknode.backend.infrastructure.view;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.view.View;
import software.blacknode.backend.domain.view.repository.ViewRepository;
import software.blacknode.backend.infrastructure.entity.state.EntityState;
import software.blacknode.backend.infrastructure.organization.related.repository.OrganizationRelatedEntityRepository;
import software.blacknode.backend.infrastructure.view.entity.ViewEntity;
import software.blacknode.backend.infrastructure.view.entity.mapper.ViewEntityMapper;
import software.blacknode.backend.infrastructure.view.entity.repository.ViewEntityRepository;

@Repository
@RequiredArgsConstructor
public class ViewRepositoryImpl implements ViewRepository, OrganizationRelatedEntityRepository<View, ViewEntity> {

	private final ViewEntityRepository repository;
	private final ViewEntityMapper mapper;
	
	@Override
	public Optional<View> findById(HUID organizationId, HUID id) {
		var view = repository.queryByIdAndOrganizationIdAndState(id.toUUID(),
				organizationId.toUUID(), EntityState.ACTIVE);
		
		return view.map(this::toDomainEntity);
	}

	@Override
	public List<View> findByIds(HUID organizationId, List<HUID> ids) {
		var uuidIds = ids.stream().map(HUID::toUUID).toList();
		
		var views = repository.queryAllByIdInAndOrganizationIdAndState(uuidIds,
				organizationId.toUUID(), EntityState.ACTIVE);
		
		return views.stream()
				.map(this::toDomainEntity)
				.toList();
	}

	@Override
	public List<View> findByChannelId(HUID organizationId, HUID channelId) {
		var views = repository.queryAllByChannelIdAndOrganizationIdAndState(
				channelId.toUUID(), organizationId.toUUID(), EntityState.ACTIVE);
		
		return views.stream()
				.map(this::toDomainEntity)
				.toList();
	}

	@Override
	public void save(HUID organizationId, View view) {
		view.ensureBelongsToOrganization(organizationId);
		
		var viewEntity = toInfrastructureEntity(view);
		
		repository.save(viewEntity);
	}

	@Override
	public ViewEntity toInfrastructureEntity(View domainEntity) {
		return mapper.toInfrastructureEntity(domainEntity);
	}

	@Override
	public View toDomainEntity(ViewEntity infrastructureEntity) {
		return mapper.toDomainEntity(infrastructureEntity);
	}

}
