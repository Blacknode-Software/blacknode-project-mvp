package software.blacknode.backend.api.controller.project.response;

import lombok.experimental.SuperBuilder;
import software.blacknode.backend.api.controller.project.response.content.ProjectResponseContent;
import software.blacknode.backend.api.controller.response.impl.BatchFetchResponse;

@SuperBuilder
public class ProjectsBatchFetchResponse extends BatchFetchResponse<ProjectsBatchFetchResponse, ProjectResponseContent> {

}
