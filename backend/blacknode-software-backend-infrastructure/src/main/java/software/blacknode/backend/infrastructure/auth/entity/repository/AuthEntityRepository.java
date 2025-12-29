package software.blacknode.backend.infrastructure.auth.entity.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import software.blacknode.backend.infrastructure.auth.entity.AuthEntity;
import software.blacknode.backend.infrastructure.entity.state.EntityState;

@Repository
public interface AuthEntityRepository extends JpaRepository<AuthEntity, UUID> {

	@Query("SELECT a FROM AuthEntity a WHERE a.id = :id AND a.accountId = :accountId AND a.state = :state")
	Optional<AuthEntity> queryByIdAndAccountIdAndState(@Param("id") UUID id, @Param("accountId") UUID accountId, @Param("state") EntityState state);
	
	@Query("SELECT a FROM AuthEntity a WHERE a.accountId = :accountId AND a.state = :state")
	List<AuthEntity> queryAllByAccountIdAndState(UUID accountId, @Param("state") EntityState state);
	
}
