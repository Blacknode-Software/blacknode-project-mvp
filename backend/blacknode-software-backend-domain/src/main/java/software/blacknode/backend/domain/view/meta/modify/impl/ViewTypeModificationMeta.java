package software.blacknode.backend.domain.view.meta.modify.impl;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import software.blacknode.backend.domain.view.View;
import software.blacknode.backend.domain.view.meta.modify.ViewModificationMeta;

@Getter
@Builder
public class ViewTypeModificationMeta implements ViewModificationMeta {
	
	private final View.Type type;
	
	public Optional<View.Type> getType() {
		return Optional.ofNullable(type);
	}

}
