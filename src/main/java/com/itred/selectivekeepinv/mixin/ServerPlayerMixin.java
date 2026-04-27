package com.itred.selectivekeepinv.mixin;

import com.itred.selectivekeepinv.attachement.SKIDataAttachments;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {

    // Add onto the check at line 1398 to also fire if my check passes
    // This makes it so that the player's old inventory is restored if they have the keep inventory attachment OR keep inventory is enabled as a gamerule.
    @ModifyExpressionValue(method = "restoreFrom", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;isSpectator()Z"))
    private boolean altKeepInventoryCheck(boolean original, @Local(argsOnly = true) ServerPlayer that) {
        return original || that.getData(SKIDataAttachments.KEEPINV_ATTACHMENT);
    }


}
