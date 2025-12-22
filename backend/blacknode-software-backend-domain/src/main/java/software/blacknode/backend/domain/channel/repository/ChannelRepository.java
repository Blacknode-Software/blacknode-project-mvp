package software.blacknode.backend.domain.channel.repository;

import java.util.List;
import java.util.Optional;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.channel.Channel;

public interface ChannelRepository {

	Optional<Channel> findById(HUID organizationId, HUID id);
	
	List<Channel> findAllById(HUID organizationId, Iterable<HUID> ids);
	
	List<Channel> findByProjectId(HUID organizationId, HUID projectId);

	void save(HUID organizationId, Channel channel);
}
