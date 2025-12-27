package software.blacknode.backend.api.controller.channel.mapper;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.channel.response.content.ChannelResponseContent;
import software.blacknode.backend.api.controller.channel.response.content.annotation.ChannelResponseContentMapping;
import software.blacknode.backend.domain.channel.Channel;

@Mapper(componentModel = "spring")
public interface ChannelMapper {

	@ChannelResponseContentMapping
	ChannelResponseContent map(Channel channel);
	
}
