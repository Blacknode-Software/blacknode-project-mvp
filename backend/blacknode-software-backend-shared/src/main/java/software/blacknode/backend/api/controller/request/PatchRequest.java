package software.blacknode.backend.api.controller.request;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public abstract class PatchRequest extends BaseRequest {
	public List<String> properties = new ArrayList<>();
	
//	public List<PatchProperty> properties = new ArrayList<>();
//	
//	public boolean hasPatchProperty(PatchProperty property) {
//		for (PatchProperty p : properties) {
//			if (p.is(property)) {
//				return true;
//			}
//		}
//		return false;
//	}
//	
//	public boolean hasPatchProperty(String property) {
//		for (PatchProperty p : properties) {
//			if (p.is(property)) {
//				return true;
//			}
//		}
//		return false;
//	}
//	
//	public static interface PatchProperty {
//		public default boolean is(PatchProperty property) {
//			return is(property.getName());
//		}
//		
//		public default boolean is(String type) {
//			return this.getName().equalsIgnoreCase(type);
//		}
//		
//		public String getName();
//	}
}
