package software.blacknode.backend.infrastructure.organization.entity.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import software.blacknode.backend.infrastructure.entity.state.EntityState;
import software.blacknode.backend.infrastructure.organization.entity.OrganizationEntity;

public interface OrganizationEntityRepository extends JpaRepository<OrganizationEntity, UUID>{

	@Query("SELECT p FROM OrganizationEntity p WHERE p.id = :id AND p.state = :state")
	Optional<OrganizationEntity> queryByIdAndState(@Param("id") UUID id, @Param("state") EntityState state);
	
	@Query("SELECT p FROM OrganizationEntity p WHERE p.state = :state")
	List<OrganizationEntity> queryAllByState(@Param("state") EntityState state);

}
