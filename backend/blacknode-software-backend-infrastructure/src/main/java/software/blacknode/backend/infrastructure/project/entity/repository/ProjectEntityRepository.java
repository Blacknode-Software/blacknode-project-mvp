package software.blacknode.backend.infrastructure.project.entity.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import software.blacknode.backend.infrastructure.entity.state.EntityState;
import software.blacknode.backend.infrastructure.project.entity.ProjectEntity;

@Repository
public interface ProjectEntityRepository extends JpaRepository<ProjectEntity, UUID> {

	Optional<ProjectEntity> findByIdAndOrganizationIdAndState(UUID id, UUID organizationId, EntityState state);
	
	List<ProjectEntity> findAllByIdInAndOrganizationIdAndState(List<UUID> ids, UUID organizationId, EntityState state);
	
	List<ProjectEntity> findAllByOrganizationIdAndState(UUID organizationId, EntityState state);
}
