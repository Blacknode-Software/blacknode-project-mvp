package software.blacknode.backend.application.channel;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.channel.Channel;
import software.blacknode.backend.domain.channel.meta.delete.impl.ChannelDefaultDeletionMeta;
import software.blacknode.backend.domain.channel.repository.ChannelRepository;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;
import software.blacknode.backend.domain.entity.modifier.impl.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;
import software.blacknode.backend.domain.exception.BlacknodeException;

@Transactional
@Service
@RequiredArgsConstructor
public class ChannelService {

	private final ChannelRepository repository;
	
	public Channel create(HUID organizationId, CreationMeta meta) {
		var channel = new Channel(organizationId);
		
		channel.create(meta);
		
		repository.save(organizationId, channel);
		
		return channel;
	}
	
	public Channel modify(HUID organizationId, HUID channelId, ModificationMeta meta) {
		return modify(organizationId, channelId, List.of(meta));
	}
	
	public Channel modify(HUID organizationId, HUID channelId, List<ModificationMeta> metas) {
		var channel = getOrThrow(organizationId, channelId);
		
		for(var meta : metas) {
			channel.modify(meta);
		}
		
		repository.save(organizationId, channel);
		
		return channel;
	}
	
	
	public void delete(HUID organizationId, HUID channelId) {
		var meta = ChannelDefaultDeletionMeta.builder().build();
		
		delete(organizationId, channelId, meta);
	}
	
	public void delete(HUID organizationId, HUID channelId, DeletionMeta meta) {
		var channel = getOrThrow(organizationId, channelId);
		
		channel.delete(meta);
		
		repository.save(organizationId, channel);
	}
	
	public Optional<Channel> get(HUID organizationId, HUID channelId) {
		return repository.findById(organizationId, channelId);
	}
	
	public Channel getOrThrow(HUID organizationId, HUID channelId) {
		return get(organizationId, channelId).orElseThrow(() -> 
			new BlacknodeException("Channel with ID " + channelId + " not found.")
		);
	}
	
	public List<Channel> getByIds(HUID organizationId, List<HUID> channelIds) {
		return repository.findByIds(organizationId, channelIds);
	}
	
	public List<Channel> getAllInProject(HUID organizationId, HUID projectId) {
		return repository.findAllByProjectId(organizationId, projectId);
	}
	
}
