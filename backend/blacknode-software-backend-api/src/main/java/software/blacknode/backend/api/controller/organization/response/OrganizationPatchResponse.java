package software.blacknode.backend.api.controller.organization.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.api.controller.organization.response.content.OrganizationResponseContent;
import software.blacknode.backend.api.controller.response.impl.ResponseBySetter;

@Getter
@SuperBuilder
public class OrganizationPatchResponse extends OrganizationResponseContent implements ResponseBySetter<OrganizationResponse> {

	@Getter @Setter private Status status;
	@Getter @Setter private String message; 
	
}
	
