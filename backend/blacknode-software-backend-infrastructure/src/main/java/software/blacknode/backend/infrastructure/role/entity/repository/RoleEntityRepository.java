package software.blacknode.backend.infrastructure.role.entity.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import software.blacknode.backend.infrastructure.entity.state.EntityState;
import software.blacknode.backend.infrastructure.role.entity.RoleEntity;

public interface RoleEntityRepository extends JpaRepository<RoleEntity, UUID> {

	@Query("SELECT r FROM RoleEntity r WHERE r.id = :id AND r.organizationId = :organizationId AND r.state = :state")
	Optional<RoleEntity> queryByIdAndOrganizationIdAndState(
			@Param("id") UUID id, 
			@Param("organizationId") UUID organizationId, 
			@Param("state") EntityState state
	);
	
	@Query("SELECT r FROM RoleEntity r WHERE r.organizationId = :organizationId AND r.state = :state")
	List<RoleEntity> queryByOrganizationIdAndState(
			@Param("organizationId") UUID organizationId,
			@Param("state") EntityState state
	);

	@Query("SELECT r FROM RoleEntity r WHERE r.id IN :ids AND r.organizationId = :organizationId AND r.state = :state")
	List<RoleEntity> queryByIdInAndOrganizationIdAndState(
			@Param("ids") List<UUID> ids,
			@Param("organizationId") UUID organizationId,
			@Param("state") EntityState state
	);
	
	
	
}
