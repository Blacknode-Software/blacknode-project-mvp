package software.blacknode.backend.infrastructure.resource;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.domain.resource.Resource;
import software.blacknode.backend.domain.resource.repository.ResourceRepository;

@Repository
public class ResourceRepositoryImpl implements ResourceRepository {

	@Override
	public Optional<Resource> findById(HUID id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void save(Resource resource) {
		// TODO Auto-generated method stub
		
	}

}
