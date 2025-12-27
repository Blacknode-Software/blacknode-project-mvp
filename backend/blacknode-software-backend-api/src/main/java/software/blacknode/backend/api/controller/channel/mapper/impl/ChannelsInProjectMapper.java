package software.blacknode.backend.api.controller.channel.mapper.impl;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import software.blacknode.backend.api.controller.channel.response.ChannelsListResponse;
import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.application.channel.usecase.ChannelsInProjectUseCase;

@Mapper(componentModel = "spring")
public interface ChannelsInProjectMapper extends ResponseMapper<ChannelsInProjectUseCase.Result, ChannelsListResponse> {

	@Override
	@Mapping(target = "ids", source = "channelsIds")
	ChannelsListResponse toResponse(ChannelsInProjectUseCase.Result result);

}
