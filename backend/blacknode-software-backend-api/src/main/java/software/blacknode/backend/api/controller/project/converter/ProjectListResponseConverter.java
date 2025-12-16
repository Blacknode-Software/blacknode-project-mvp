package software.blacknode.backend.api.controller.project.converter;

import java.util.List;

import org.springframework.stereotype.Component;

import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.api.controller.converter.BaseResponseConverter;
import software.blacknode.backend.api.controller.project.response.ProjectListResponse;

@Component
public class ProjectListResponseConverter implements BaseResponseConverter<List<HUID>, ProjectListResponse> {

	@Override
	public ProjectListResponse convert(List<HUID> source) {
		var response = ProjectListResponse.builder()
				.ids(source.stream()
						.map(HUID::toUUID)
						.toList())
				.build();
		
		return response;
	}

}
