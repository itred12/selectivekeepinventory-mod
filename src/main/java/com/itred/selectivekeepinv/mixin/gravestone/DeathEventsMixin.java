package com.itred.selectivekeepinv.mixin.gravestone;

import com.itred.selectivekeepinv.SKIConfig;
import com.itred.selectivekeepinv.attachement.SKIDataAttachments;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import de.maxhenkel.gravestone.events.DeathEvents;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(DeathEvents.class)
public class DeathEventsMixin {

    // Modify the check for KeepInventory (from another function? ..is this important?) at line 38 to include a check for the keep inventory attachment
    // Graves nor obituaries will generate
    @ModifyExpressionValue(method = "playerDeath", at = @At(value = "INVOKE", target = "Lde/maxhenkel/gravestone/events/DeathEvents;keepInventory(Lnet/minecraft/world/entity/player/Player;)Z"))
    private static boolean addAltKeepInventoryCheck(boolean original, @Local(name = "player") Player player) {

        // Only try to disable gravestones if the config has been set
        if (SKIConfig.DISABLE_GRAVESTONES.get()) {
            // original is *before* inversion, so if either of these are true, then gravestones wont be placed.
            // I think.
            return (player.getData(SKIDataAttachments.KEEPINV_ATTACHMENT) || original);
        } else {
            return original;
        }

    }
}
