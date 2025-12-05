package software.blacknode.backend.domain.resource.repository;

import java.util.Optional;

import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.domain.resource.Resource;

public interface ResourceRepository {

	Optional<Resource> findById(HUID id);

	void save(Resource resource);
}
