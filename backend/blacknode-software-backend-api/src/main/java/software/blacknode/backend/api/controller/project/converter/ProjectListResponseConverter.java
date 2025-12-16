package software.blacknode.backend.api.controller.project.converter;

import java.util.List;

import org.springframework.stereotype.Component;

import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.api.controller.converter.BaseResponseConverter;
import software.blacknode.backend.api.controller.project.response.ProjectsListResponse;

@Component
public class ProjectListResponseConverter implements BaseResponseConverter<List<HUID>, ProjectsListResponse> {

	@Override
	public ProjectsListResponse convert(List<HUID> source) {
		var response = ProjectsListResponse.builder()
				.ids(source.stream()
						.map(HUID::toUUID)
						.toList())
				.build();
		
		return response;
	}

}
