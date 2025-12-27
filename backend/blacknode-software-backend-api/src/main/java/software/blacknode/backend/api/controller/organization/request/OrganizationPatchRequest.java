package software.blacknode.backend.api.controller.organization.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import software.blacknode.backend.api.controller.request.PatchRequest;

@Getter
@AllArgsConstructor
public class OrganizationPatchRequest extends PatchRequest {

	private String name;
	
}
