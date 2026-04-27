package com.itred.selectivekeepinv.mixin;

import com.itred.selectivekeepinv.attachement.SKIDataAttachments;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {


    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;destroyVanishingCursedItems()V"), method = "dropEquipment", cancellable = true)
    public void checkForKeepInventoryTag(CallbackInfo ci) {

        // Have to cast this way since the compiler doesn't understand that the mixin *will* become the instance, or something
        Player playerInstance = (Player) (Object) this;
        // If the player has the data attachment, then prevent their equipment from dropping by returning before that method call. Nice and easy.
        if (playerInstance.getData(SKIDataAttachments.KEEPINV_ATTACHMENT) == true) {
            ci.cancel();
        }

    }
}
