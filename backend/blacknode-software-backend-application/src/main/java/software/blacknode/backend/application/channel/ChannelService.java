package software.blacknode.backend.application.channel;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.domain.channel.Channel;
import software.blacknode.backend.domain.channel.repository.ChannelRepository;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.modifier.create.meta.CreationMeta;

@Service
@RequiredArgsConstructor
public class ChannelService {

	private final ChannelRepository repository;
	
	public Channel getOrThrow(HUID channelId) {
		return repository.findById(channelId).
				orElseThrow(() -> new BlacknodeException("Channel with ID " + channelId + " not found."));
	}
	
	public List<Channel> getByIds(List<HUID> channelIds) {
		return repository.findAllById(channelIds);
	}
	
	public Channel create(CreationMeta meta) {
		var channel = new Channel();
		
		channel.create(meta);
		
		repository.save(channel);
		
		return channel;
	}
}
