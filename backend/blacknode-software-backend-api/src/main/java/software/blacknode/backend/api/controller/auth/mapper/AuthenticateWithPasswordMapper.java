package software.blacknode.backend.api.controller.auth.mapper;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.auth.request.AuthenticateWithPasswordRequest;
import software.blacknode.backend.api.controller.mapper.impl.RequestMapper;
import software.blacknode.backend.application.auth.command.AuthenticateWithPasswordCommand;

@Mapper(componentModel = "spring")
public interface AuthenticateWithPasswordMapper extends RequestMapper<AuthenticateWithPasswordRequest, AuthenticateWithPasswordCommand> {
	
	@Override
	AuthenticateWithPasswordCommand toCommand(AuthenticateWithPasswordRequest request);
	
}
