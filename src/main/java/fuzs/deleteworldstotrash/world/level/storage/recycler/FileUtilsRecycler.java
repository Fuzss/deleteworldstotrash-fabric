package fuzs.deleteworldstotrash.world.level.storage.recycler;

import com.sun.jna.platform.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * designed to work on windows and mac
 * https://www.rgagnon.com/javadetails/java-move-files-to-windows-trash-jna.html
 */
public class FileUtilsRecycler implements WorldRecycler {
    @Override
    public boolean isSupported() {
        return FileUtils.getInstance().hasTrash();
    }

    @Override
    public boolean moveToTrash(File file) throws IOException {
        FileUtils.getInstance().moveToTrash(file);
        return true;
    }
}
