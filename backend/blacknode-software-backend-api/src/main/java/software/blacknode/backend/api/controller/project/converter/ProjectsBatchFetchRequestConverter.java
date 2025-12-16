package software.blacknode.backend.api.controller.project.converter;

import org.springframework.stereotype.Component;

import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.api.controller.converter.BaseRequestConverter;
import software.blacknode.backend.api.controller.project.request.ProjectsBatchFetchRequest;
import software.blacknode.backend.application.project.command.ProjectsBatchFetchCommand;

@Component
public class ProjectsBatchFetchRequestConverter implements BaseRequestConverter<ProjectsBatchFetchRequest, ProjectsBatchFetchCommand> {

	@Override
	public ProjectsBatchFetchCommand convert(ProjectsBatchFetchRequest request) {
		var projectIds = request.getIdentifiers().stream()
				.map(HUID::fromUUID)
				.toList();
		
		return ProjectsBatchFetchCommand.builder()
				.projectIds(projectIds)
				.build();
	}

}
