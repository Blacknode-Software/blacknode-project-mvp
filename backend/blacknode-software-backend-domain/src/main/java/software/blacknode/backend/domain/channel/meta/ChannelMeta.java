package software.blacknode.backend.domain.channel.meta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.With;

@Getter
@With
@Builder
@ToString
public class ChannelMeta {

	@NotBlank
	@Size(min = 3, max = 30)
	private String name;
	
	@NotNull
	@Size(max = 255)
	private String description;
	
	@NotBlank
	@Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$")
	private String color;
}
