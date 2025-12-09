package software.blacknode.backend.application.project;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.project.Project;
import software.blacknode.backend.domain.project.repository.ProjectRepository;

@Service
@RequiredArgsConstructor
public class ProjectService {

	private final ProjectRepository repository;
	
	public Project getProjectOrThrow(HUID projectId) {
		return repository.findById(projectId)
				.orElseThrow(() -> new BlacknodeException("Project with ID " + projectId + " not found."));
	}
	
	
}
