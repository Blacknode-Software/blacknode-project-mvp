package software.blacknode.backend.domain.view.meta.create.impl;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.view.View;
import software.blacknode.backend.domain.view.meta.create.ViewCreationMeta;

@Builder
@Getter
public class ViewIntialCreationMeta implements ViewCreationMeta {

	private final String name;
	
	private final View.Type type;
	
	private final HUID channelId;
	
	public Optional<String> getName() {
		return Optional.ofNullable(name);
	}
	
	public Optional<View.Type> getType() {
		return Optional.ofNullable(type);
	}
	
}
