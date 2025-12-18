package software.blacknode.backend.infrastructure.entity.version.reslover;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import software.blacknode.backend.infrastructure.entity.version.migration.MigrationEntity;

public final class VersionResolver {

    private static final Pattern VERSION_PATTERN =
            Pattern.compile(".*_V(\\d{3})$");

    private static final Map<Class<?>, VersionInfo> CACHE =
            new ConcurrentHashMap<>();

    public static VersionInfo resolve(Class<?> root) {
        return CACHE.computeIfAbsent(root, VersionResolver::scan);
    }

    private static VersionInfo scan(Class<?> root) {
        Map<Integer, Class<?>> byVersion = new HashMap<>();
        int max = 0;

        for (Class<?> nested : root.getDeclaredClasses()) {
            if (!MigrationEntity.class.isAssignableFrom(nested)) continue;

            int v = extract(nested);
            byVersion.put(v, nested);
            max = Math.max(max, v);
        }

        int latest = byVersion.isEmpty() ? 1 : max + 1;
        byVersion.put(latest, root);

        return new VersionInfo(latest, byVersion);
    }

    private static int extract(Class<?> type) {
        Matcher m = VERSION_PATTERN.matcher(type.getSimpleName());
        if (!m.matches())
            throw new IllegalStateException("Invalid migrator name: " + type);
        return Integer.parseInt(m.group(1));
    }

    public record VersionInfo(
            int latest,
            Map<Integer, Class<?>> versions
    ) {}
}
