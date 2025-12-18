package software.blacknode.backend.infrastructure.entity.version.json;

import com.fasterxml.jackson.databind.JsonNode;

public record VersionedEntityJson(
        int version,
        JsonNode content
) {}

