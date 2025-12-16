package software.blacknode.backend.domain.modifier.modify.meta.list;

import java.util.ArrayList;
import java.util.List;

import software.blacknode.backend.domain.modifier.modify.meta.ModificationMeta;

public interface ModificationMetaList {
	
	public static List<ModificationMeta> empty() {
		return new ArrayList<>();
	}
	
}
