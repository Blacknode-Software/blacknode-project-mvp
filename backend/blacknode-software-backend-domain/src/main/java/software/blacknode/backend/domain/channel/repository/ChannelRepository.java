package software.blacknode.backend.domain.channel.repository;

import java.util.List;
import java.util.Optional;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.channel.Channel;

public interface ChannelRepository {

	Optional<Channel> findById(HUID id);
	
	List<Channel> findAllById(Iterable<HUID> ids);

	void save(Channel channel);
}
