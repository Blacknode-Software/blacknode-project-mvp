package software.blacknode.backend.infrastructure.channel;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.channel.Channel;
import software.blacknode.backend.domain.channel.repository.ChannelRepository;
import software.blacknode.backend.infrastructure.channel.entity.ChannelEntity;
import software.blacknode.backend.infrastructure.channel.entity.mapper.ChannelEntityMapper;
import software.blacknode.backend.infrastructure.channel.entity.repository.ChannelEntityRepository;
import software.blacknode.backend.infrastructure.entity.state.EntityState;
import software.blacknode.backend.infrastructure.organization.related.repository.OrganizationRelatedEntityRepository;

@Repository
@RequiredArgsConstructor
public class ChannelRepositoryImpl implements ChannelRepository, OrganizationRelatedEntityRepository<Channel, ChannelEntity> {

	private final ChannelEntityRepository repository;
	private final ChannelEntityMapper mapper;
	
	@Override
	public Optional<Channel> findById(@NonNull HUID organizationId, @NonNull HUID id) {
		var channel = repository.queryByIdAndOrganizationIdAndState(id.toUUID(), 
				organizationId.toUUID(), EntityState.ACTIVE);
		
		return channel.map(this::toDomainEntity);
	}

	@Override
	public List<Channel> findByIds(@NonNull HUID organizationId, @NonNull List<HUID> ids) {
		var uuidList = ids.stream().map(HUID::toUUID).toList();
		
		var channels = repository.queryAllByIdInAndOrganizationIdAndState(uuidList, 
				organizationId.toUUID(), EntityState.ACTIVE);
		
		return channels.stream()
				.map(this::toDomainEntity)
				.toList();
	}

	@Override
	public List<Channel> findAllByProjectId(@NonNull HUID organizationId, @NonNull HUID projectId) {
		var channels = repository.queryAllByProjectIdAndOrganizationIdAndState(
				projectId.toUUID(), organizationId.toUUID(), EntityState.ACTIVE);
		
		return channels.stream()
				.map(this::toDomainEntity)
				.toList();
	}

	@Override
	public void save(@NonNull HUID organizationId, @NonNull Channel channel) {
		channel.ensureBelongsToOrganization(organizationId);
		
		var channelEntity = toInfrastructureEntity(channel);
		
		repository.save(channelEntity);
	}

	@Override
	public ChannelEntity toInfrastructureEntity(Channel domainEntity) {
		return mapper.toInfrastructureEntity(domainEntity);
	}

	@Override
	public Channel toDomainEntity(ChannelEntity infrastructureEntity) {
		return mapper.toDomainEntity(infrastructureEntity);
	}

}
