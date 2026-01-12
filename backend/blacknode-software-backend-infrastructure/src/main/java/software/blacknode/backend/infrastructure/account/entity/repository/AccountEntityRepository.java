package software.blacknode.backend.infrastructure.account.entity.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import software.blacknode.backend.infrastructure.account.entity.AccountEntity;
import software.blacknode.backend.infrastructure.entity.state.EntityState;

@Repository
public interface AccountEntityRepository extends JpaRepository<AccountEntity, UUID> {

	@Query("SELECT a FROM AccountEntity a WHERE a.id = :id AND a.state = :state")
	Optional<AccountEntity> queryByIdAndState(@Param("id") UUID id, @Param("state") EntityState state);
	
	@Query("SELECT a FROM AccountEntity a WHERE a.email = :email AND a.state = :state")
	Optional<AccountEntity> queryByEmailAndState(@Param("email") String email, @Param("state") EntityState state);

	@Query("SELECT a FROM AccountEntity a WHERE a.id IN :ids AND a.state = :state")
	List<AccountEntity> queryAllByIdInAndState(@Param("ids") List<UUID> ids, @Param("state") EntityState state);
}
