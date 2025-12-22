package software.blacknode.backend.application.project;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;
import software.blacknode.backend.domain.entity.modifier.impl.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.project.Project;
import software.blacknode.backend.domain.project.meta.delete.impl.ProjectDefaultDeletionMeta;
import software.blacknode.backend.domain.project.repository.ProjectRepository;

@Service
@RequiredArgsConstructor
public class ProjectService {

	private final ProjectRepository repository;
	
	public Project create(HUID organizationId, CreationMeta meta) {
		var project = new Project(organizationId);
		
		project.create(meta);
		
		// TODO validate if other projects with the same name exist in the organization?
		
		repository.save(organizationId, project);
		
		return project;
	}
	
	public Project modify(HUID organizationId, HUID projectId, ModificationMeta meta) {
		return modify(organizationId, projectId, List.of(meta));
	}
	
	public Project modify(HUID organizationId, HUID projectId, List<ModificationMeta> metas) {
		var project = getOrThrow(organizationId, projectId);
		
		// add validation if needed
		
		for (var meta : metas) {
			project.modify(meta);
		}
		
		repository.save(organizationId, project);
		
		return project;
	}
	
	public void delete(HUID organizationId, HUID projectId) {
		var meta = ProjectDefaultDeletionMeta.builder().build();
		
		delete(organizationId, projectId, meta);
	}
	
	public void delete(HUID organizationId, HUID projectId, DeletionMeta meta) {
		var project = getOrThrow(organizationId, projectId);
		
		project.delete(meta);
		
		repository.save(organizationId, project);
	}
	
	public Project getOrThrow(HUID organizationId, HUID projectId) {
		return repository.findById(organizationId, projectId)
				.orElseThrow(() -> new BlacknodeException("Project with ID " + projectId + " not found."));
	}
	
	public List<Project> getByIds(HUID organizationId, List<HUID> projectIds) {
		var projects = repository.findAllById(organizationId, projectIds);
		
		return projects;
	}
	
	public List<Project> getAll(HUID organizationId) {
		var projects = repository.findProjectInOrganization(organizationId);
		
		return projects;
	}
}
