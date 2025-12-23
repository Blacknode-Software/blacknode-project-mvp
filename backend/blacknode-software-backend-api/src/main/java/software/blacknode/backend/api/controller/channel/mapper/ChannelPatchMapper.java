package software.blacknode.backend.api.controller.channel.mapper;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import software.blacknode.backend.api.controller.channel.request.ChannelPatchRequest;
import software.blacknode.backend.api.controller.channel.response.ChannelPatchResponse;
import software.blacknode.backend.api.controller.channel.response.content.annotation.ChannelResponseContentMapping;
import software.blacknode.backend.api.controller.mapper.annotation.PatchOperationsMappingRequest;
import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.application.channel.command.ChannelPatchCommand;
import software.blacknode.backend.application.channel.usecase.ChannelPatchUseCase;

@Mapper(componentModel = "spring")
public interface ChannelPatchMapper extends ResponseMapper<ChannelPatchUseCase.Result, ChannelPatchResponse> {

	@PatchOperationsMappingRequest
	ChannelPatchCommand toCommand(ChannelPatchRequest request, UUID id);
	
	@ChannelResponseContentMapping
	ChannelPatchResponse toResponse(ChannelPatchUseCase.Result result);

}
