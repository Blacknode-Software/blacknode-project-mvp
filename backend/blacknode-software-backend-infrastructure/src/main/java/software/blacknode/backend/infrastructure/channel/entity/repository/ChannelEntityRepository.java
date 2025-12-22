package software.blacknode.backend.infrastructure.channel.entity.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import software.blacknode.backend.infrastructure.channel.entity.ChannelEntity;
import software.blacknode.backend.infrastructure.entity.state.EntityState;

public interface ChannelEntityRepository extends JpaRepository<ChannelEntity, UUID> {

	@Query("SELECT c FROM ChannelEntity c WHERE c.id = :id AND c.organizationId = :organizationId AND c.state = :state")
	Optional<ChannelEntity> queryByIdAndOrganizationIdAndState(@Param("id") UUID id, @Param("organizationId") UUID organizationId, @Param("state") EntityState state);
	
	@Query("SELECT c FROM ChannelEntity c WHERE c.id IN :ids AND c.organizationId = :organizationId AND c.state = :state")
	List<ChannelEntity> queryAllByIdInAndOrganizationIdAndState(@Param("ids") List<UUID> ids, @Param("organizationId") UUID organizationId, @Param("state") EntityState state);
	
	@Query("SELECT c FROM ChannelEntity c WHERE c.projectId = :projectId AND c.organizationId = :organizationId AND c.state = :state")
	List<ChannelEntity> queryAllByProjectIdAndOrganizationIdAndState(@Param("projectId") UUID projectId, @Param("organizationId") UUID organizationId, @Param("state") EntityState state);
	
}
