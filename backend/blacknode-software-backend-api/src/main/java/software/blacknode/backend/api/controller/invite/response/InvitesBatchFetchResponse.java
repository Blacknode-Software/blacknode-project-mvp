package software.blacknode.backend.api.controller.invite.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.api.controller.invite.response.content.InviteResponseContent;
import software.blacknode.backend.api.controller.response.impl.BatchFetchResponse;

@Getter
@SuperBuilder
public class InvitesBatchFetchResponse extends BatchFetchResponse<InvitesBatchFetchResponse, InviteResponseContent> {

}
