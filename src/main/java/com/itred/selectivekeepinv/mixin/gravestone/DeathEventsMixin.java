package com.itred.selectivekeepinv.mixin.gravestone;

import com.itred.selectivekeepinv.IKIConfig;
import com.itred.selectivekeepinv.IndividualKeepInventory;
import com.itred.selectivekeepinv.attachement.IKIDataAttachments;
import com.llamalad7.mixinextras.sugar.Local;
import de.maxhenkel.gravestone.corelib.death.Death;
import de.maxhenkel.gravestone.corelib.death.PlayerDeathEvent;
import de.maxhenkel.gravestone.events.DeathEvents;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(DeathEvents.class)
public class DeathEventsMixin {


    // Graves nor obituaries will generate when the config is enabled, should all go well
    @Inject(method = "playerDeath", at = @At(value = "INVOKE", target = "Lde/maxhenkel/gravestone/GraveUtils;getGraveStoneLocation(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/core/BlockPos;"), cancellable = true)
    private static void individualkeepinventory$preventGravestoneWithSafety(PlayerDeathEvent event, CallbackInfo ci, @Local(name = "player") Player player, @Local(name = "death") Death death) {

        // Only prevent placing a gravestone if no items were dropped, to prevent accidentally voiding items
        if (death.getAllItems().isEmpty()) {
            if (player.getData(IKIDataAttachments.KEEPINV_ATTACHMENT) && IKIConfig.DISABLE_GRAVESTONES.get()) {
                IndividualKeepInventory.LOGGER.info("Presumably safely prevented death ID \"{}\" from placing a gravestone.", death.getId());
                ci.cancel();
            }
        }



    }
}
