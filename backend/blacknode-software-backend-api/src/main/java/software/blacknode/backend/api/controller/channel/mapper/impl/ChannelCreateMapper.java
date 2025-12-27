package software.blacknode.backend.api.controller.channel.mapper.impl;

import java.util.UUID;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.channel.request.ChannelCreateRequest;
import software.blacknode.backend.api.controller.channel.response.ChannelCreateResponse;
import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.application.channel.command.ChannelCreateCommand;
import software.blacknode.backend.application.channel.usecase.ChannelCreateUseCase;

@Mapper(componentModel = "spring")
public interface ChannelCreateMapper extends ResponseMapper<ChannelCreateUseCase.Result, ChannelCreateResponse> {
	
	ChannelCreateCommand toCommand(ChannelCreateRequest request, UUID projectId);
	
	@Override
	ChannelCreateResponse toResponse(ChannelCreateUseCase.Result result);
	
}
