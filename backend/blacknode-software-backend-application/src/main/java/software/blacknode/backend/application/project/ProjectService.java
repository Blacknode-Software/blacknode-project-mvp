package software.blacknode.backend.application.project;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.modifier.create.meta.CreationMeta;
import software.blacknode.backend.domain.modifier.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.project.Project;
import software.blacknode.backend.domain.project.repository.ProjectRepository;

@Service
@RequiredArgsConstructor
public class ProjectService {

	private final ProjectRepository repository;
	
	public Project create(CreationMeta meta) {
		var project = new Project();
		
		project.create(meta);
		
		// TODO validate if other projects with the same name exist in the organization?
		
		repository.save(project);
		
		return project;
	}
	
	public void delete(HUID projectId, DeletionMeta meta) {
		var project = getOrThrow(projectId);
		
		project.delete(meta);
		
		repository.save(project);
	}
	
	public Project getOrThrow(HUID projectId) {
		return repository.findById(projectId)
				.orElseThrow(() -> new BlacknodeException("Project with ID " + projectId + " not found."));
	}
	
	public List<Project> getByIds(List<HUID> projectIds) {
		var projects = repository.findAllById(projectIds);
		
		return projects;
	}
	
	public List<Project> getAllInOrganization() {
		var projects = repository.findProjectInOrganization();
		
		return projects;
	}
	
}
