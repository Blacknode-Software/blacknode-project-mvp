package software.blacknode.backend.api.controller.member.mapper;

import java.util.UUID;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.member.request.MemberRemoveRequest;
import software.blacknode.backend.application.member.command.MemberRemoveFromChannelCommand;
import software.blacknode.backend.application.member.command.MemberRemoveFromProjectCommand;

@Mapper(componentModel = "spring")
public interface MemberRemoveMapper {

	MemberRemoveFromChannelCommand toChannelCommand(MemberRemoveRequest request, UUID channelId);
	
	MemberRemoveFromProjectCommand toProjectCommand(MemberRemoveRequest request, UUID projectId);
	
}
