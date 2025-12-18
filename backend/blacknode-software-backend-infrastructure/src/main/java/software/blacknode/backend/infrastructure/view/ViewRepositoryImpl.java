package software.blacknode.backend.infrastructure.view;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.view.View;
import software.blacknode.backend.domain.view.repository.ViewRepository;

@Repository
public class ViewRepositoryImpl implements ViewRepository {

	@Override
	public Optional<View> findById(HUID id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void save(View view) {
		// TODO Auto-generated method stub
		
	}

}
