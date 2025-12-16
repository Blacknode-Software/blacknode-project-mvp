package software.blacknode.backend.application.patch;

import java.util.List;

public interface PatchOperation {
	
	public String getName();
	
	public default boolean is(PatchOperation property) {
		return is(property.getName());
	}
	
	public default boolean is(String type) {
		return this.getName().equalsIgnoreCase(type);
	}
	
	public default boolean isIn(List<String> types) {
		for (String type : types) {
			if (is(type)) {
				return true;
			}
		}
		return false;
	}
	
	public default boolean isIn(String... types) {
		for (String type : types) {
			if (is(type)) {
				return true;
			}
		}
		return false;
	}
	
}
