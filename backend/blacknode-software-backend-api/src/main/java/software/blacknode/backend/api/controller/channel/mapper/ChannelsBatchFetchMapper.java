package software.blacknode.backend.api.controller.channel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import software.blacknode.backend.api.controller.channel.request.ChannelsBatchFetchRequest;
import software.blacknode.backend.api.controller.channel.response.ChannelsBatchFetchResponse;
import software.blacknode.backend.api.controller.mapper.impl.RequestMapper;
import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.application.channel.command.ChannelsBatchFetchCommand;
import software.blacknode.backend.application.channel.usecase.ChannelsBatchFetchUseCase;

@Mapper(componentModel = "spring")
public interface ChannelsBatchFetchMapper extends RequestMapper<ChannelsBatchFetchRequest, ChannelsBatchFetchCommand>, ResponseMapper<ChannelsBatchFetchUseCase.Result, ChannelsBatchFetchResponse> {

	@Override
	@Mapping(target = "channelIds", source = "ids")
	ChannelsBatchFetchCommand toCommand(ChannelsBatchFetchRequest request);

	@Mapping(target = "items", source = "channels")
	ChannelsBatchFetchResponse toResponse(ChannelsBatchFetchUseCase.Result result);

	
}
