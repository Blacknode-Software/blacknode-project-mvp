package software.blacknode.backend.api.controller.channel.response;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.api.controller.response.impl.BaseResponse;

@Getter
@SuperBuilder
@ToString
public class ChannelInsightsResponse extends BaseResponse<ChannelInsightsResponse> {

	private int tasksTotal;
	private int tasksCompleted;
	private int tasksIncomplete;
	private int tasksOverdue;

}
