package software.blacknode.backend.application.channel;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.channel.Channel;
import software.blacknode.backend.domain.channel.repository.ChannelRepository;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;
import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;
import software.blacknode.backend.domain.exception.BlacknodeException;

@Service
@RequiredArgsConstructor
public class ChannelService {

	private final ChannelRepository repository;
	
	public Channel create(HUID organizationId, CreationMeta meta) {
		var channel = new Channel(organizationId);
		
		channel.create(meta);
		
		repository.save(channel);
		
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
		
		repository.save(channel);
		
		return channel;
	}
	
	public Channel getOrThrow(HUID organizationId, HUID channelId) {
		return repository.findById(channelId).
				orElseThrow(() -> new BlacknodeException("Channel with ID " + channelId + " not found."));
	}
	
	public List<Channel> getByIds(HUID organizationId, List<HUID> channelIds) {
		return repository.findAllById(channelIds);
	}
	
}
