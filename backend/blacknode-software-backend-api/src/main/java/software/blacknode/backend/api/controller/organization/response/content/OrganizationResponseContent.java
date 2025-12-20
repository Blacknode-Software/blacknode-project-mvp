package software.blacknode.backend.api.controller.organization.response.content;

import java.util.UUID;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class OrganizationResponseContent {

	private UUID id;
	private String name;
	
}
