package software.blacknode.backend.infrastructure.account.entity.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import software.blacknode.backend.infrastructure.account.entity.AccountEntity;

public interface AccountEntityRepository extends JpaRepository<AccountEntity, UUID> {

	@Query("SELECT a FROM AccountEntity a WHERE a.id = :id AND a.state = :state")
	Optional<AccountEntity> queryByIdAndState(@Param("id") UUID id, @Param("state") String state);
	
	@Query("SELECT a FROM AccountEntity a WHERE a.email = :email AND a.state = :state")
	Optional<AccountEntity> queryByEmailAndState(@Param("email") String email, @Param("state") String state);
}
