package fuzs.deleteworldstotrash.mixin.client;

import fuzs.deleteworldstotrash.world.level.storage.WorldTrashUtil;
import net.minecraft.util.DirectoryLock;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Path;

@Mixin(LevelStorageSource.LevelStorageAccess.class)
public abstract class LevelStorageAccessMixin {
    @Shadow
    @Final
    DirectoryLock lock;
    @Shadow
    @Final
    Path levelPath;

    @Shadow
    private void checkLock() {
        throw new IllegalStateException();
    }

    @Inject(method = "deleteLevel", at = @At("HEAD"), cancellable = true)
    public void deleteLevel(CallbackInfo callbackInfo) {
        if (WorldTrashUtil.tryMoveToTrash(this.lock, this.levelPath, this::checkLock)) {
            callbackInfo.cancel();
        }
    }
}
