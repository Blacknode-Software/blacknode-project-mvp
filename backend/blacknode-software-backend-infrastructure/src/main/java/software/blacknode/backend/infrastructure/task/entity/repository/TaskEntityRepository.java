package software.blacknode.backend.infrastructure.task.entity.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import software.blacknode.backend.infrastructure.entity.state.EntityState;
import software.blacknode.backend.infrastructure.task.entity.TaskEntity;

public interface TaskEntityRepository extends JpaRepository<TaskEntity, UUID> {

	@Query("SELECT t FROM TaskEntity t WHERE t.id = :id AND t.organizationId = :organizationId AND t.state = :state")
	Optional<TaskEntity> queryByIdAndOrganizationIdAndState(@Param("id") UUID id, @Param("organizationId") UUID organizationId, @Param("state") EntityState state);
	
	@Query("SELECT t FROM TaskEntity t WHERE t.id IN :ids AND t.organizationId = :organizationId AND t.state = :state")
	List<TaskEntity> queryAllByChannelIdAndOrganizationIdAndState(@Param("channelId") UUID channelId, @Param("organizationId") UUID organizationId, @Param("state") EntityState state);

	@Query("SELECT t FROM TaskEntity t WHERE t.id IN :ids AND t.organizationId = :organizationId AND t.state = :state")
	List<TaskEntity> queryAllByIdInAndOrganizationIdAndState(@Param("ids") List<UUID> ids, @Param("organizationId") UUID organizationId, @Param("state") EntityState state);
}
