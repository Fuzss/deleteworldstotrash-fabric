package fuzs.deleteworldstotrash.world.level.storage;

import com.google.common.collect.ImmutableList;
import fuzs.deleteworldstotrash.DeleteWorldsToTrash;
import fuzs.deleteworldstotrash.world.level.storage.recycler.DesktopRecycler;
import fuzs.deleteworldstotrash.world.level.storage.recycler.FileUtilsRecycler;
import fuzs.deleteworldstotrash.world.level.storage.recycler.WorldRecycler;
import net.minecraft.util.DirectoryLock;

import java.nio.file.Path;
import java.util.List;

public class WorldTrashUtil {
    private static final List<WorldRecycler> SUPPORTED_RECYCLERS;

    static {
        SUPPORTED_RECYCLERS = new ImmutableList.Builder<WorldRecycler>()
                .add(new FileUtilsRecycler(), new DesktopRecycler())
                .build();
    }

    public static boolean tryMoveToTrash(DirectoryLock lock, Path levelPath, Runnable checkLock) {
        for (WorldRecycler recycler : SUPPORTED_RECYCLERS) {
            if (recycler.isSupported()) {
                checkLock.run();
                for (int i = 1; i <= 5; ++i) {
                    DeleteWorldsToTrash.LOGGER.info("Attempt {} moving world to trash...", i);
                    try {
                        lock.close();
                        if (recycler.moveToTrash(levelPath.toFile())) {
                            return true;
                        }
                    } catch (Exception e) {
                        DeleteWorldsToTrash.LOGGER.warn("Failed to move world to trash {}", levelPath, e);
                    }
                }
            }
        }
        DeleteWorldsToTrash.LOGGER.warn("Definitively failed to move world to trash {}", levelPath);
        return false;
    }

    public static boolean isTrashSupported() {
        return SUPPORTED_RECYCLERS.stream().anyMatch(WorldRecycler::isSupported);
    }
}
