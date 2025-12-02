package software.blacknode.backend.api.controller.request;

import java.util.ArrayList;
import java.util.List;

public class PatchRequest extends BaseRequest {
	public List<PatchOpertation> operations = new ArrayList<>();
	
	public boolean hasOperationType(String type) {
		for (PatchOpertation operation : operations) {
			if (operation.isType(type)) {
				return true;
			}
		}
		return false;
	}
	
	public static interface PatchOpertation {
		public boolean isType(String type);
		public String getType();
	}
}
