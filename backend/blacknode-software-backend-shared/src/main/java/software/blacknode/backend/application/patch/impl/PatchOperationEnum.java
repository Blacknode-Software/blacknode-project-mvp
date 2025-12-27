package software.blacknode.backend.application.patch.impl;

import java.util.Arrays;
import java.util.List;

import software.blacknode.backend.application.patch.PatchOperation;
import software.blacknode.backend.domain.exception.BlacknodeException;

public interface PatchOperationEnum extends PatchOperation {
	
	@Override
	public default String getName() {
		if(this instanceof Enum<?>) {
			return ((Enum<?>) this).name();
		}
		
		throw new BlacknodeException("PatchOperationEnum can only be implemented by Enums");
	}
	
	public static <E  extends Enum<? extends PatchOperationEnum>> List<String> getOperationsList(Class<E> enumClass) {
		var operations = Arrays.stream(enumClass.getEnumConstants())
				.map(e -> (PatchOperationEnum) e)
				.toArray(PatchOperationEnum[]::new);
		
		return getNames(operations);
	}
		
	public static List<String> getNames(PatchOperationEnum[] operations) {
		return Arrays.stream(operations)
				.map(PatchOperation::getName)
				.toList();
	}

}
