package fuzs.deleteworldstotrash.mixin.client;

import fuzs.deleteworldstotrash.client.handler.TrashScreenHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.ProgressScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.gui.screens.worldselection.WorldSelectionList;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.storage.LevelSummary;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldSelectionList.WorldListEntry.class)
public abstract class WorldListEntryMixin extends ObjectSelectionList.Entry<WorldSelectionList.WorldListEntry> {
    @Shadow
    @Final
    private Minecraft minecraft;
    @Shadow
    @Final
    private SelectWorldScreen screen;
    @Shadow
    @Final
    LevelSummary summary;

    @Inject(method = "deleteWorld", at = @At("HEAD"), cancellable = true)
    public void deleteWorld(CallbackInfo callbackInfo) {
        // lazy port from forge
        final ConfirmScreen deleteScreen = new ConfirmScreen(accepted -> {
            if (accepted) {
                this.minecraft.setScreen(new ProgressScreen(true));
                this.doDeleteWorld();
            }
            this.minecraft.setScreen(this.screen);
        }, new TranslatableComponent("selectWorld.deleteQuestion"), new TranslatableComponent("selectWorld.deleteWarning", this.summary.getLevelName()), new TranslatableComponent("selectWorld.deleteButton"), CommonComponents.GUI_CANCEL);
        final Screen trashScreen = TrashScreenHandler.onScreenOpen(deleteScreen);
        if (trashScreen != null) {
            this.minecraft.setScreen(trashScreen);
            callbackInfo.cancel();
        }
    }

    @Shadow
    public abstract void doDeleteWorld();
}
