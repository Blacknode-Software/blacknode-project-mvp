package software.blacknode.backend.domain.project.repository;

import java.util.Optional;

import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.domain.project.Project;

public interface ProjectRepository {

	Optional<Project> findById(HUID id);

	void save(Project project);
}
