package software.blacknode.backend.api.controller.profile.mapper;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.mapper.ControllerMapper;
import software.blacknode.backend.api.controller.profile.response.content.ProfileResponseContent;
import software.blacknode.backend.api.controller.profile.response.content.annotation.ProfileResponseContentMapping;
import software.blacknode.backend.application.profile.dto.ProfileDTO;

@Mapper(componentModel = "spring")
public interface ProfileMapper extends ControllerMapper {

	@ProfileResponseContentMapping
	ProfileResponseContent toResponseContent(ProfileDTO profile);
	
}
