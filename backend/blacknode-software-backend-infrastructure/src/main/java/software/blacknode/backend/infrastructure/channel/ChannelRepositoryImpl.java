package software.blacknode.backend.infrastructure.channel;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.channel.Channel;
import software.blacknode.backend.domain.channel.repository.ChannelRepository;

@Repository
public class ChannelRepositoryImpl implements ChannelRepository {

	@Override
	public Optional<Channel> findById(HUID organizationId, HUID id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<Channel> findAllById(HUID organizationId, Iterable<HUID> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Channel> findByProjectId(HUID organizationId, HUID projectId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(HUID organizationId, Channel channel) {
		// TODO Auto-generated method stub
		
	}

}
