package software.blacknode.backend.api.controller.view.mapper;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.mapper.ControllerMapper;
import software.blacknode.backend.api.controller.view.response.content.ViewResponseContent;
import software.blacknode.backend.api.controller.view.response.content.annotation.ViewResponseContentMapping;
import software.blacknode.backend.domain.view.View;

@Mapper(componentModel = "spring")
public interface ViewMapper extends ControllerMapper {

	@ViewResponseContentMapping
	ViewResponseContent map(View view);
	
}
