package software.blacknode.backend.api.controller.channel.mapper.impl;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.channel.response.ChannelResponse;
import software.blacknode.backend.api.controller.channel.response.content.annotation.ChannelResponseContentMapping;
import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.application.channel.usecase.ChannelFetchUseCase;

@Mapper(componentModel = "spring")
public interface ChannelFetchMapper extends ResponseMapper<ChannelFetchUseCase.Result, ChannelResponse> {

	@Override
	@ChannelResponseContentMapping
	ChannelResponse toResponse(ChannelFetchUseCase.Result result);
	
}
