package software.blacknode.backend.domain.view.meta.modify.impl;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import software.blacknode.backend.domain.view.meta.modify.ViewModificationMeta;

@Getter
@Builder
public class ViewNameModificationMeta implements ViewModificationMeta {
	
	private final String name;
	
	public Optional<String> getName() {
		return Optional.ofNullable(name);
	}
	
}
