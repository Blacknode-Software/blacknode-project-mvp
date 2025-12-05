package software.blacknode.backend.domain.view.repository;

import java.util.Optional;

import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.domain.view.View;

public interface ViewRepository {

	Optional<View> findById(HUID id);

	void save(View view);
}
