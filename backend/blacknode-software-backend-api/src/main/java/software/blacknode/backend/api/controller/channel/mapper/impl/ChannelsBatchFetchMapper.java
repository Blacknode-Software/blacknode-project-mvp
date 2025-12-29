package software.blacknode.backend.api.controller.channel.mapper.impl;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import software.blacknode.backend.api.controller.channel.mapper.ChannelMapper;
import software.blacknode.backend.api.controller.channel.request.ChannelsBatchFetchRequest;
import software.blacknode.backend.api.controller.channel.response.ChannelsBatchFetchResponse;
import software.blacknode.backend.api.controller.mapper.impl.RequestMapper;
import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.application.channel.command.ChannelsBatchFetchCommand;
import software.blacknode.backend.application.channel.usecase.ChannelsBatchFetchUseCase;

@Mapper(componentModel = "spring")
public interface ChannelsBatchFetchMapper extends ChannelMapper, RequestMapper<ChannelsBatchFetchRequest, ChannelsBatchFetchCommand>, ResponseMapper<ChannelsBatchFetchUseCase.Result, ChannelsBatchFetchResponse> {

	@Override
	@Mapping(target = "channelIds", source = "ids")
	ChannelsBatchFetchCommand toCommand(ChannelsBatchFetchRequest request);

	@Override
	@Mapping(target = "items", source = "channels")
	ChannelsBatchFetchResponse toResponse(ChannelsBatchFetchUseCase.Result result);

	
}
