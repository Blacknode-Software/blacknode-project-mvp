package software.blacknode.backend.api.controller.organization.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import software.blacknode.backend.api.controller.request.PatchRequest;

@Getter
@NoArgsConstructor
@ToString
public class OrganizationPatchRequest extends PatchRequest {

	private String name;
	
}
