package fuzs.deleteworldstotrash.world.level.storage.recycler;

import java.io.File;
import java.io.IOException;

public interface WorldRecycler {
    boolean isSupported();

    boolean moveToTrash(File file) throws IOException;
}
