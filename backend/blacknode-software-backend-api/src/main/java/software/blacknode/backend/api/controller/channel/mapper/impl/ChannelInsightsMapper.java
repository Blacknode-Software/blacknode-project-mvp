package software.blacknode.backend.api.controller.channel.mapper.impl;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.channel.response.ChannelInsightsResponse;
import software.blacknode.backend.application.channel.usecase.ChannelInsightsUseCase;

@Mapper(componentModel = "spring")
public interface ChannelInsightsMapper {

	ChannelInsightsResponse toResponse(ChannelInsightsUseCase.Result result);
	
}
