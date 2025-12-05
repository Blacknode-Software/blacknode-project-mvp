package software.blacknode.backend.domain.modifier.duplicate;

import java.util.Optional;

import software.blacknode.backend.domain.modifier.duplicate.meta.DuplicateMeta;

public interface Duplicatable {

    void duplicate(Optional<DuplicateMeta> meta);

    void duplicate(DuplicateMeta meta);

    void duplicate();

}