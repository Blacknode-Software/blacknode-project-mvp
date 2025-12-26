package software.blacknode.backend.domain.entity.modifier.impl.modify.meta;

import java.util.ArrayList;
import java.util.List;

import software.blacknode.backend.domain.entity.modifier.meta.DomainEntityModifierMeta;

public interface ModificationMeta extends DomainEntityModifierMeta {

	public static List<ModificationMeta> of(ModificationMeta... metas) {
		return List.of(metas);
	}
	
	public static List<ModificationMeta> emptyList() {
		return new ArrayList<>();
	}
	
}