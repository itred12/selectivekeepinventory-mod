package com.itred.selectivekeepinv.mixin.curios;

import com.itred.selectivekeepinv.util.InventoryDropsManager;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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

    // Prevent Curios from being dropped using existing keepInventory behavior, hopefully better compat?
    @Inject(method = "lambda$playerDrops$12", at = @At(value = "INVOKE", target = "Lnet/neoforged/neoforge/common/ModConfigSpec$EnumValue;get()Ljava/lang/Object;", shift = At.Shift.BEFORE))
    private static void overrideKeepInventory(LivingDropsEvent evt, LivingEntity livingEntity, ICuriosItemHandler handler, CallbackInfo ci, @Local(name = "keepInventory") LocalBooleanRef keepInventory) {

        if (livingEntity instanceof Player player && !keepInventory.get() && InventoryDropsManager.shouldKeepExtraInventories(player)) {
            keepInventory.set(true);
        }

    }
}
