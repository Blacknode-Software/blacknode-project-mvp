package software.blacknode.backend.domain.view.meta;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.With;
import software.blacknode.backend.domain.view.View;

@Getter
@With
@Builder
@ToString
public class ViewMeta {

	@NotNull
	@Size(min = 3, max = 30)
	private String name;
	
	@NotNull
	private View.Type type;
	
	/* FOR FUTURE RELEASES - ADD QUERY PARAMETER FOR FILTERING */
}
