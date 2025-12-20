package software.blacknode.backend.api.controller.project.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.mapper.ControllerMapper;
import software.blacknode.backend.api.controller.project.response.content.ProjectResponseContent;
import software.blacknode.backend.api.controller.project.response.content.annotation.ProjectResponseContentMapping;
import software.blacknode.backend.domain.project.Project;

@Mapper(componentModel = "spring")
public interface ProjectMapper extends ControllerMapper {

	@ProjectResponseContentMapping
    ProjectResponseContent toResponseContent(Project project);

    default Map<UUID, ProjectResponseContent> toResponseContentMap(List<Project> projects) {
        if (projects == null) {
            return new HashMap<>();
        }
        return projects.stream()
            .collect(Collectors.toMap(
                p -> p.getId().toUUID(),
                this::toResponseContent
            ));
    }
    
}
