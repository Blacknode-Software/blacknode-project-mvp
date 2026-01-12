package software.blacknode.backend.api.controller.role.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.api.controller.response.impl.BatchFetchResponse;
import software.blacknode.backend.api.controller.role.response.content.RoleResponseContent;

@Getter
@SuperBuilder
public class RolesBatchFetchResponse extends BatchFetchResponse<RolesBatchFetchResponse, RoleResponseContent> {
	
}
