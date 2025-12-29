package software.blacknode.backend.api.controller.view;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.api.controller.organization.annotation.OrganizationHeader;
import software.blacknode.backend.api.controller.view.mapper.impl.ViewFetchMapper;
import software.blacknode.backend.api.controller.view.mapper.impl.ViewsBatchFetchMapper;
import software.blacknode.backend.api.controller.view.mapper.impl.ViewsInChannelMapper;
import software.blacknode.backend.api.controller.view.request.ViewsBatchFetchRequest;
import software.blacknode.backend.api.controller.view.response.ViewResponse;
import software.blacknode.backend.api.controller.view.response.ViewsBatchFetchResponse;
import software.blacknode.backend.api.controller.view.response.ViewsListResponse;
import software.blacknode.backend.application.view.command.ViewFetchCommand;
import software.blacknode.backend.application.view.command.ViewsInChannelCommand;
import software.blacknode.backend.application.view.usecase.ViewFetchUseCase;
import software.blacknode.backend.application.view.usecase.ViewsBatchFetchUseCase;
import software.blacknode.backend.application.view.usecase.ViewsInChannelUseCase;

@RestController
@RequiredArgsConstructor
@Tag(name = "Views", description = "View management APIs")
public class ViewController {

	private final ViewFetchMapper viewFetchMapper;
	private final ViewFetchUseCase viewFetchUseCase;
	
	private final ViewsBatchFetchMapper viewsBatchFetchMapper;
	private final ViewsBatchFetchUseCase viewsBatchFetchUseCase;
	
	private final ViewsInChannelMapper viewsInChannelMapper;
	private final ViewsInChannelUseCase viewsInChannelUseCase;
	
	@OrganizationHeader
	@Operation(summary = "Fetch a view by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "View fetched") })
	@GetMapping("/views/{id}	")
	public ResponseEntity<ViewResponse> getView(@PathVariable UUID id) {
		var command = ViewFetchCommand.builder()
				.viewId(HUID.fromUUID(id))
				.build();
		
		var result = viewFetchUseCase.execute(command);
		
		var response = viewFetchMapper.toResponse(result);
		
		return response.toOkResponse("View fetched successfully");
	}
	
	@OrganizationHeader
	@Operation(summary = "Fetch multiple views by IDs")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Found views") })
	@PostMapping("/views/batch-fetch")
	public ResponseEntity<ViewsBatchFetchResponse> getViewsBatch(@RequestBody ViewsBatchFetchRequest request) {
		var command = viewsBatchFetchMapper.toCommand(request);
		
		var result = viewsBatchFetchUseCase.execute(command);
		
		var response = viewsBatchFetchMapper.toResponse(result);
		
		return response.toOkResponse("Views fetched successfully");
	}
	
	@OrganizationHeader
	@Operation(summary = "Fetch views for a specific channel")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Views for channel fetched") })
	@GetMapping("/channels/{channelId}/views")
	public ResponseEntity<ViewsListResponse> getViewsForChannel(@PathVariable UUID channelId) {
		var command = ViewsInChannelCommand.builder()
				.channelId(HUID.fromUUID(channelId))
				.build();
		
		var result = viewsInChannelUseCase.execute(command);
		
		var response = viewsInChannelMapper.toResponse(result);
		
		return response.toOkResponse("Views for channel fetched successfully");
	}
}
