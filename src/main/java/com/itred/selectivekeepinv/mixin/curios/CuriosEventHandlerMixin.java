package com.itred.selectivekeepinv.mixin.curios;

import com.itred.selectivekeepinv.attachement.SKIDataAttachments;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.common.event.CuriosEventHandler;

@Pseudo
@Mixin(CuriosEventHandler.class)
public class CuriosEventHandlerMixin {

    // Inject into the lambda at line 301
    // Shifting like this was the only way I could get it to go after the gamerule call????
    @Inject(method = "lambda$playerDrops$12", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/GameRules;getBoolean(Lnet/minecraft/world/level/GameRules$Key;)Z", shift = At.Shift.BY, by = 2))
    private static void overrideKeepInventory(LivingDropsEvent evt, LivingEntity livingEntity, ICuriosItemHandler handler, CallbackInfo ci, @Local(name = "keepInventory") LocalBooleanRef keepInventory) {

        // If we've gotten this far, we already know the entity is a player.
        // keepInventory is currently the value of the keepInventory gamerule,
        // we only want to use the data attachment check if keepInventory is off, since it defaults to false, and would otherwise drop curios even if KeepInventory is enabled, lol...
        if (!keepInventory.get() && livingEntity.getData(SKIDataAttachments.KEEPINV_ATTACHMENT)) {
            keepInventory.set(true);
        }

    }
}
