package software.blacknode.backend.api.controller.channel;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
import software.blacknode.backend.api.controller.BaseController;
import software.blacknode.backend.api.controller.annotation.DisplayPatchOperations;
import software.blacknode.backend.api.controller.channel.mapper.impl.ChannelCreateMapper;
import software.blacknode.backend.api.controller.channel.mapper.impl.ChannelFetchMapper;
import software.blacknode.backend.api.controller.channel.mapper.impl.ChannelPatchMapper;
import software.blacknode.backend.api.controller.channel.mapper.impl.ChannelsBatchFetchMapper;
import software.blacknode.backend.api.controller.channel.mapper.impl.ChannelsInProjectMapper;
import software.blacknode.backend.api.controller.channel.request.ChannelCreateRequest;
import software.blacknode.backend.api.controller.channel.request.ChannelPatchRequest;
import software.blacknode.backend.api.controller.channel.request.ChannelsBatchFetchRequest;
import software.blacknode.backend.api.controller.channel.response.ChannelCreateResponse;
import software.blacknode.backend.api.controller.channel.response.ChannelPatchResponse;
import software.blacknode.backend.api.controller.channel.response.ChannelResponse;
import software.blacknode.backend.api.controller.channel.response.ChannelsBatchFetchResponse;
import software.blacknode.backend.api.controller.channel.response.ChannelsListResponse;
import software.blacknode.backend.api.controller.organization.annotation.OrganizationHeader;
import software.blacknode.backend.api.controller.response.impl.SuccessResponse;
import software.blacknode.backend.application.channel.command.ChannelDeleteCommand;
import software.blacknode.backend.application.channel.command.ChannelFetchCommand;
import software.blacknode.backend.application.channel.command.ChannelsInProjectCommand;
import software.blacknode.backend.application.channel.usecase.ChannelCreateUseCase;
import software.blacknode.backend.application.channel.usecase.ChannelDeleteUseCase;
import software.blacknode.backend.application.channel.usecase.ChannelFetchUseCase;
import software.blacknode.backend.application.channel.usecase.ChannelPatchUseCase;
import software.blacknode.backend.application.channel.usecase.ChannelPatchUseCase.ChannelPatchOperation;
import software.blacknode.backend.application.channel.usecase.ChannelsBatchFetchUseCase;
import software.blacknode.backend.application.channel.usecase.ChannelsInProjectUseCase;

@Tag(name = "Channels", description = "Channel management APIs")
@RestController
@RequiredArgsConstructor
public class ChannelController extends BaseController {
	
	private final ChannelFetchMapper channelFetchMapper;
	private final ChannelFetchUseCase channelFetchUseCase;
	
	private final ChannelsInProjectMapper channelsInProjectMapper;
	private final ChannelsInProjectUseCase channelsInProjectUseCase;
	
	private final ChannelsBatchFetchMapper channelsBatchMapper;
	private final ChannelsBatchFetchUseCase channelsBatchFetchUseCase;
	
	private final ChannelCreateMapper channelCreateMapper;
	private final ChannelCreateUseCase channelCreateUseCase;
	
	private final ChannelPatchMapper channelPatchMapper;
	private final ChannelPatchUseCase channelPatchUseCase;
	
	private final ChannelDeleteUseCase channelDeleteUseCase;
	
	@OrganizationHeader
	@Operation(summary = "Get a channel by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Found the channel") })
	@GetMapping("/channels/{id}")
	public ResponseEntity<ChannelResponse> getChannel(@PathVariable UUID id) {
		var command = ChannelFetchCommand.builder()
				.channelId(HUID.fromUUID(id))
				.build();
		
		var result = channelFetchUseCase.execute(command);
		
		var response = channelFetchMapper.toResponse(result);

		return response.toOkResponse("Channel fetched successfully");
	}
	
	@OrganizationHeader
	@Operation(summary = "Get all channels for a project")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Found channels") })
	@GetMapping("/projects/{projectId}/channels")
	public ResponseEntity<ChannelsListResponse> getChannelsInProject(@PathVariable UUID projectId) {
		var command = ChannelsInProjectCommand.builder()
				.projectId(HUID.fromUUID(projectId))
				.build();
		
		var result = channelsInProjectUseCase.execute(command);
		
		var response = channelsInProjectMapper.toResponse(result);
		
		return response.toOkResponse("Channels fetched successfully");
	}
	
	@OrganizationHeader
	@Operation(summary = "Get channels by a batch of IDs")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Found channels") })
	@PostMapping("/channels/batch-fetch")
	public ResponseEntity<ChannelsBatchFetchResponse> getChannelsBatch(@RequestBody ChannelsBatchFetchRequest request) {
		var command = channelsBatchMapper.toCommand(request);
		
		var result = channelsBatchFetchUseCase.execute(command);
		
		var response = channelsBatchMapper.toResponse(result);
		
		return response.toOkResponse("Channels fetched successfully");
	}
	
	@OrganizationHeader
	@Operation(summary = "Create a new channel")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Channel created") })
	@PostMapping("/projects/{projectId}/channels")
	public ResponseEntity<ChannelCreateResponse> createChannel(@PathVariable UUID projectId, @RequestBody ChannelCreateRequest request) {
		var command = channelCreateMapper.toCommand(request, projectId);
		
		var result = channelCreateUseCase.execute(command);
		
		var response = channelCreateMapper.toResponse(result);
		
		return response.toSuccessResponse("Channel created successfully", HttpStatus.CREATED);
	}
	
	@OrganizationHeader
	@Operation(summary = "Update an existing channel")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Channel updated") })
	@DisplayPatchOperations(ChannelPatchOperation.class)
	@PatchMapping("/channels/{id}")
	public ResponseEntity<ChannelPatchResponse> patchChannel(@PathVariable UUID id, @RequestBody ChannelPatchRequest request) {
		var command = channelPatchMapper.toCommand(request, id);
		
		var result = channelPatchUseCase.execute(command);
		
		var response = channelPatchMapper.toResponse(result);
		
		return response.toOkResponse("Channel updated successfully");
	}
	
	@OrganizationHeader
	@Operation(summary = "Delete a channel")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Channel deleted") })
	@DeleteMapping("/channels/{id}")
	public ResponseEntity<SuccessResponse> deleteChannel(@PathVariable UUID id) {
		var command = ChannelDeleteCommand.builder()
				.channelId(HUID.fromUUID(id))
				.build();
		
		channelDeleteUseCase.execute(command);
		
		return SuccessResponse.with("Channel deleted successfully");
	}
	
	
}