package fuzs.deleteworldstotrash.client.handler;

import fuzs.deleteworldstotrash.mixin.client.accessor.ConfirmScreenAccessor;
import fuzs.deleteworldstotrash.world.level.storage.TrashComponentUtil;
import fuzs.deleteworldstotrash.world.level.storage.WorldTrashUtil;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TranslatableComponent;

public class TrashScreenHandler {
    public static Screen onScreenOpen(Screen screen) {
        if (screen instanceof ConfirmScreen && WorldTrashUtil.isTrashSupported()) {
            if (screen.getTitle() instanceof TranslatableComponent title1 && title1.getKey().equals("selectWorld.deleteQuestion") && ((ConfirmScreenAccessor) screen).getTitle2() instanceof TranslatableComponent title2) {
                return new ConfirmScreen(((ConfirmScreenAccessor) screen).getCallback(), title1, new TranslatableComponent("deleteworldstotrash.selectWorld.deleteWarning", title2.getArgs()[0], TrashComponentUtil.getTrashComponent()), new TranslatableComponent("deleteworldstotrash.selectWorld.deleteButton", TrashComponentUtil.getTrashButtonComponent()), CommonComponents.GUI_CANCEL);
            }
        }
        return null;
    }
}
