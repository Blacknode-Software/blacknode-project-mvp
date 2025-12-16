package software.blacknode.backend.api.controller.project.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.api.controller.project.response.content.ProjectResponseContent;
import software.blacknode.backend.api.controller.response.impl.ResponseBySetter;

@SuperBuilder
public class ProjectResponse extends ProjectResponseContent implements ResponseBySetter<ProjectResponse> {
	
	@Getter @Setter private Status status;
	@Getter @Setter private String message;
	
}
