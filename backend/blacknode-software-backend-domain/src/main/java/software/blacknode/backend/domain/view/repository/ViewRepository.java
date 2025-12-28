package software.blacknode.backend.domain.view.repository;

import java.util.List;
import java.util.Optional;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.view.View;

public interface ViewRepository {
	
	Optional<View> findById(HUID organizationId, HUID id);

	List<View> findByChannelId(HUID organizationId, HUID channelId);
	
	List<View> findByIds(HUID organizationId, List<HUID> ids);
	
	void save(HUID organizationId, View view);
}
