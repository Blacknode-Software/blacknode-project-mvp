package software.blacknode.backend.infrastructure.project.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.infrastructure.project.entity.ProjectEntity;

public interface ProjectEntityRepository extends JpaRepository<ProjectEntity, HUID> {

}
