package software.blacknode.backend.domain.channel.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.channel.Channel;

public interface ChannelRepository {

	Optional<Channel> findById(HUID organizationId, HUID id);
	
	List<Channel> findByIds(HUID organizationId, Set<HUID> ids);
	
	List<Channel> findAllByProjectId(HUID organizationId, HUID projectId);

	void save(HUID organizationId, Channel channel);
}
