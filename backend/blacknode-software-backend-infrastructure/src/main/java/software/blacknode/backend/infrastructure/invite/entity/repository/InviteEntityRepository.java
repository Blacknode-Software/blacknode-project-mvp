package software.blacknode.backend.infrastructure.invite.entity.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import software.blacknode.backend.infrastructure.entity.state.EntityState;
import software.blacknode.backend.infrastructure.invite.entity.InviteEntity;

public interface InviteEntityRepository extends JpaRepository<InviteEntity, UUID> {

	@Query("SELECT i FROM InviteEntity i WHERE i.id = :id AND i.organizationId = :organizationId AND i.state = :state")
	Optional<InviteEntity> queryByIdAndOrganizationIdAndState(
			@Param("id") UUID id, 
			@Param("organizationId") UUID organizationId, 
			@Param("state") EntityState state
	);
	
	@Query("SELECT i FROM InviteEntity i WHERE i.token = :token AND i.state = :state")
	Optional<InviteEntity> findByTokenAndState(
			@Param("token") String token,
			@Param("state") EntityState state
			);
	
	@Query("SELECT i FROM InviteEntity i WHERE i.organizationId = :organizationId AND i.state = :state")
	List<InviteEntity> findAllByOrganizationIdAndState(
			@Param("organizationId") UUID organizationId, 
			@Param("state") EntityState state
	);
	
	@Query("SELECT i FROM InviteEntity i WHERE i.id IN :ids AND i.organizationId = :organizationId AND i.state = :state")
	List<InviteEntity> findAllByIdInAndOrganizationIdAndState(
			@Param("ids") List<UUID> ids, 
			@Param("organizationId") UUID organizationId, 
			@Param("state") EntityState state
	);
	
}
