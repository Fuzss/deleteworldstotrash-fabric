package fuzs.deleteworldstotrash.world.level.storage.recycler;

import java.awt.*;
import java.io.File;

/**
 * support for trashing files was added to desktop class in java 9
 * https://stackoverflow.com/questions/222463/is-it-possible-with-java-to-delete-to-the-recycle-bin
 * Desktop API was developed to support Windows and Gnome only
 * https://stackoverflow.com/questions/102325/not-supported-platforms-for-java-awt-desktop-getdesktop
 * to make this work on ubuntu <code>apt-get install libgnome2-0</code> needs to be run
 * seems to work occasionally on macos as well
 */
public class DesktopRecycler implements WorldRecycler {
    @Override
    public boolean isSupported() {
        return Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.MOVE_TO_TRASH);
    }

    @Override
    public boolean moveToTrash(File file) {
        return Desktop.getDesktop().moveToTrash(file);
    }
}
