package software.blacknode.backend.infrastructure.project.entity.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import software.blacknode.backend.infrastructure.entity.state.EntityState;
import software.blacknode.backend.infrastructure.project.entity.ProjectEntity;

@Repository
public interface ProjectEntityRepository extends JpaRepository<ProjectEntity, UUID> {

	@Query("SELECT p FROM ProjectEntity p WHERE p.id = :id AND p.organizationId = :organizationId AND p.state = :state")
	Optional<ProjectEntity> queryByIdAndOrganizationIdAndState(@Param("id") UUID id, @Param("organizationId") UUID organizationId, @Param("state") EntityState state);
	
	@Query("SELECT p FROM ProjectEntity p WHERE p.id IN :ids AND p.organizationId = :organizationId AND p.state = :state")
	List<ProjectEntity> queryAllByIdInAndOrganizationIdAndState(@Param("ids") List<UUID> ids, @Param("organizationId") UUID organizationId, @Param("state") EntityState state);
	
	@Query("SELECT p FROM ProjectEntity p WHERE p.organizationId = :organizationId AND p.state = :state")
	List<ProjectEntity> queryAllByOrganizationIdAndState(@Param("organizationId") UUID organizationId, @Param("state") EntityState state);
}
