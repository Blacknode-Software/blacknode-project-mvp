package software.blacknode.backend.api.controller.organization.mapper;

import java.util.UUID;

import org.mapstruct.Mapper;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.api.controller.mapper.ControllerMapper;
import software.blacknode.backend.api.controller.organization.response.content.OrganizationResponseContent;
import software.blacknode.backend.api.controller.organization.response.content.annotation.OrganizationResponseContentMapping;
import software.blacknode.backend.domain.organization.Organization;

@Mapper(componentModel = "spring")
public interface OrganizationMapper extends ControllerMapper{

	@OrganizationResponseContentMapping
	OrganizationResponseContent toResponseContent(Organization organization);
	
	default UUID map(HUID huid) {
		return huidToUUID(huid);
	}
	
}
