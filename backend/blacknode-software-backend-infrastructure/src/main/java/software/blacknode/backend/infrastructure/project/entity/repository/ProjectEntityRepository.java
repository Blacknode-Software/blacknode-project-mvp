package software.blacknode.backend.infrastructure.project.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.infrastructure.project.entity.ProjectEntity;

@Repository
public interface ProjectEntityRepository extends JpaRepository<ProjectEntity, HUID> {

}
