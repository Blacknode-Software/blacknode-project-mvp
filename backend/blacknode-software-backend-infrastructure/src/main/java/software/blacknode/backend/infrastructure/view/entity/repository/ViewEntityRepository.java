package software.blacknode.backend.infrastructure.view.entity.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import software.blacknode.backend.infrastructure.entity.state.EntityState;
import software.blacknode.backend.infrastructure.view.entity.ViewEntity;

public interface ViewEntityRepository extends JpaRepository<ViewEntity, UUID> {

	@Query("SELECT v FROM ViewEntity v WHERE v.id = :id AND v.organizationId = :organizationId AND v.state = :state")
	Optional<ViewEntity> queryByIdAndOrganizationIdAndState(@Param("id") UUID id, @Param("organizationId") UUID organizationId, @Param("state") EntityState state);
	
	@Query("SELECT v FROM ViewEntity v WHERE v.channelId = :channelId AND v.organizationId = :organizationId AND v.state = :state")
	List<ViewEntity> queryAllByChannelIdAndOrganizationIdAndState(@Param("channelId") UUID channelId, @Param("organizationId") UUID organizationId, @Param("state") EntityState state);

	@Query("SELECT v FROM ViewEntity v WHERE v.id IN :ids AND v.organizationId = :organizationId AND v.state = :state")
	List<ViewEntity> queryAllByIdInAndOrganizationIdAndState(@Param("ids") List<UUID> ids, @Param("organizationId") UUID organizationId, @Param("state") EntityState state);
	
}
