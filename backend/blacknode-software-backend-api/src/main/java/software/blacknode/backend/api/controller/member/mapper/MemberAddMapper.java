package software.blacknode.backend.api.controller.member.mapper;

import java.util.UUID;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.mapper.ControllerMapper;
import software.blacknode.backend.api.controller.member.request.MemberAddRequest;
import software.blacknode.backend.application.member.command.MemberAddToChannelCommand;
import software.blacknode.backend.application.member.command.MemberAddToProjectCommand;

@Mapper(componentModel = "spring")
public interface MemberAddMapper extends ControllerMapper {

	MemberAddToChannelCommand toChannelCommand(MemberAddRequest request, UUID channelId);
	
	MemberAddToProjectCommand toProjectCommand(MemberAddRequest request, UUID projectId);
	
}
