package software.blacknode.backend.api.controller.view.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.api.controller.response.impl.BatchResponse;
import software.blacknode.backend.api.controller.view.response.content.ViewResponseContent;

@Getter
@SuperBuilder
public class ViewsBatchFetchResponse extends BatchResponse<ViewsBatchFetchResponse, ViewResponseContent> {

}
