package software.blacknode.backend.application.health.usecase;

import java.util.Optional;

import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import software.blacknode.backend.application.health.commnad.HealthFetchCommand;
import software.blacknode.backend.application.organization.OrganizationService;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;

@Service
@RequiredArgsConstructor
public class HealthFetchUseCase implements ResultExecutionUseCase<HealthFetchCommand, HealthFetchUseCase.Result> {
	private final OrganizationService organizationService;
	
	private final Optional<BuildProperties> buildProperties;
	
	@Override
	public Result execute(HealthFetchCommand command) {
		var status = State.OK;
		
		if(!organizationService.isDefaultOrganizationPresent()) {
			status = State.AWATING_FOR_INITIAL_SETUP;
		}
		
		var version = buildProperties
				.map(BuildProperties::getVersion)
				.orElse("unknown");
		
		return Result.builder()
				.state(status)
				.version(version)
				.build();
	}

	@Getter
	@Builder
	@ToString
	public static class Result {
		
		@NonNull
		private final State state;
		
		@NonNull
		private final String version;
	
	}
	
	public static enum State {
		AWATING_FOR_INITIAL_SETUP,
		OK,
	}

}
