package software.blacknode.backend.api.controller.mapper;

import java.util.List;
import java.util.UUID;

import org.mapstruct.Named;

import me.hinsinger.hinz.common.huid.HUID;

public interface ControllerMapper {

	@Named("uuid2HUID")
	public default HUID uuidToHUID(UUID uuid) {
		if (uuid == null) {
			return null;
		}
		return HUID.fromUUID(uuid);
	}
	
	@Named("huid2UUID")
	public default UUID huidToUUID(HUID huid) {
		if (huid == null) {
			return null;
		}
		return huid.toUUID();
	}
	
	@Named("uuids2HUIDs")
	public default List<HUID> uuidsToHUIDs(List<UUID> uuids) {
		if (uuids == null) {
			return null;
		}
		
		return uuids.stream()
				.map(this::uuidToHUID)
				.toList();
	}
	
	@Named("huids2UUIDs")
	public default List<UUID> huidsToUUIDs(List<HUID> huids) {
		if (huids == null) {
			return null;
		}
		
		return huids.stream()
				.map(this::huidToUUID)
				.toList();
		}
	
}
