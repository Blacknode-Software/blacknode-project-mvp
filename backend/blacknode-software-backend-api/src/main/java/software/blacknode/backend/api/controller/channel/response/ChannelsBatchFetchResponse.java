package software.blacknode.backend.api.controller.channel.response;

import lombok.experimental.SuperBuilder;
import software.blacknode.backend.api.controller.channel.response.content.ChannelResponseContent;
import software.blacknode.backend.api.controller.response.impl.BatchFetchResponse;

@SuperBuilder
public class ChannelsBatchFetchResponse extends BatchFetchResponse<ChannelsBatchFetchResponse, ChannelResponseContent> {

}
