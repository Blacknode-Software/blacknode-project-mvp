package software.blacknode.backend.api.controller.channel;

import java.util.List;
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
import software.blacknode.backend.api.controller.BaseController;
import software.blacknode.backend.api.controller.channel.response.ChannelDeleteResponse;
import software.blacknode.backend.api.controller.channel.response.ChannelResponse;

@RestController
@Tag(name = "Channels", description = "Channel management APIs")
public class ChannelController extends BaseController {
	
	@Operation(summary = "Get a channel by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Found the channel"),
			@ApiResponse(responseCode = "404", description = "Channel not found") })
	@GetMapping("/channels/{channelId}")
	public ResponseEntity<ChannelResponse> getChannel(@PathVariable UUID id) {
		return ResponseEntity.ok(null);
	}
	
	@Operation(summary = "Get all channels for a project")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Found channels") })
	@GetMapping("/projects/{projectId}/channels")
	public ResponseEntity<List<ChannelResponse>> getChannels(@PathVariable UUID projectId) {
		return ResponseEntity.ok(null);
	}
	
	@Operation(summary = "Create a new channel")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Channel created"),
			@ApiResponse(responseCode = "400", description = "Invalid input") })
	@PostMapping("/projects/{projectId}/channels")
	public ResponseEntity<ChannelResponse> createChannel(@PathVariable UUID projectId, @RequestBody ChannelResponse request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(null);
	}
	
	@Operation(summary = "Update an existing channel")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Channel updated"),
			@ApiResponse(responseCode = "404", description = "Channel not found") })
	@PatchMapping("/channels/{channelId}")
	public ResponseEntity<ChannelResponse> patchChannel(@PathVariable UUID id, @RequestBody ChannelResponse request) {
		return ResponseEntity.ok(null);
	}
	
	@Operation(summary = "Delete a channel")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Channel deleted"),
			@ApiResponse(responseCode = "404", description = "Channel not found") })
	@DeleteMapping("/channels/{channelId}")
	public ResponseEntity<ChannelDeleteResponse> deleteChannel(@PathVariable UUID id) {
		return ResponseEntity.ok(null);
	}
	
	
}