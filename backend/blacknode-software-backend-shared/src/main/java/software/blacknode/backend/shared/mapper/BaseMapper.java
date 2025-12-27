package software.blacknode.backend.shared.mapper;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.mapstruct.Mapper;

import me.hinsinger.hinz.common.huid.HUID;
import me.hinsinger.hinz.common.time.timestamp.Timestamp;

@Mapper(componentModel = "spring")
public interface BaseMapper {
	
	default <T> T unwrap(Optional<T> value) {
        return value == null ? null : value.orElse(null);
    }

    default <T> Optional<T> wrap(T value) {
        return Optional.ofNullable(value);
    }
	
	default UUID map(HUID huid) {
		if (huid == null) {
			return null;
		}
		return huid.toUUID();
	}
	
	default HUID map(UUID uuid) {
		if (uuid == null) {
			return null;
		}
		return HUID.fromUUID(uuid);
	}
	
	default Timestamp map(Instant instant) {
		if (instant == null) {
			return null;
		}
		return Timestamp.fromInstant(instant);
	}
	
	default Instant map(Timestamp timestamp) {
		if (timestamp == null) {
			return null;
		}
		return timestamp.toInstant();
	}
}
