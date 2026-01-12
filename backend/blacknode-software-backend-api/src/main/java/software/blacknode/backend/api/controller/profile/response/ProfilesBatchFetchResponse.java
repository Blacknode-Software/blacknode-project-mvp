package software.blacknode.backend.api.controller.profile.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.api.controller.profile.response.content.ProfileResponseContent;
import software.blacknode.backend.api.controller.response.impl.BatchResponse;

@Getter
@SuperBuilder
public class ProfilesBatchFetchResponse extends BatchResponse<ProfilesBatchFetchResponse, ProfileResponseContent> {

}
