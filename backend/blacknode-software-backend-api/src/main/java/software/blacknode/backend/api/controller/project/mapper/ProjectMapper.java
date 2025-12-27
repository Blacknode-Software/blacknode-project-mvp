package software.blacknode.backend.api.controller.project.mapper;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.mapper.ControllerMapper;
import software.blacknode.backend.api.controller.project.response.content.ProjectResponseContent;
import software.blacknode.backend.api.controller.project.response.content.annotation.ProjectResponseContentMapping;
import software.blacknode.backend.domain.project.Project;

@Mapper(componentModel = "spring")
public interface ProjectMapper extends ControllerMapper {

	@ProjectResponseContentMapping
    ProjectResponseContent map(Project project);
    
}
