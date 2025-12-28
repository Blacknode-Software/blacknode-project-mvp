package software.blacknode.backend.infrastructure.view;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.view.View;
import software.blacknode.backend.domain.view.repository.ViewRepository;

@Repository
public class ViewRepositoryImpl implements ViewRepository {

	@Override
	public Optional<View> findById(HUID organizationId, HUID id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<View> findByChannelId(HUID organizationId, HUID channelId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<View> findByIds(HUID organizationId, List<HUID> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(HUID organizationId, View view) {
		// TODO Auto-generated method stub
		
	}

}
