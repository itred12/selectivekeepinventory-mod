package com.itred.selectivekeepinv.mixin.curios;

import com.itred.selectivekeepinv.attachement.SKIDataAttachments;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.common.event.CuriosEventHandler;

@Mixin(CuriosEventHandler.class)
public class CuriosEventHandlerMixin {

    // Inject into the lambda at line 301
    @Inject(method = "lambda$playerDrops$12", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/GameRules;getBoolean(Lnet/minecraft/world/level/GameRules$Key;)Z"))
    private static void overrideKeepInventory(LivingDropsEvent evt, LivingEntity livingEntity, ICuriosItemHandler handler, CallbackInfo ci) {

        // If we've gotten this far, we already know the entity is a player.
        if (livingEntity.getData(SKIDataAttachments.KEEPINV_ATTACHMENT)) {
            boolean keepInventory = true;
        }

    }
}
