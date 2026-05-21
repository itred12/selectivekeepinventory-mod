package com.itred.selectivekeepinv.mixin.accessories;

import com.itred.selectivekeepinv.util.InventoryDropsManager;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import io.wispforest.accessories.impl.AccessoriesEventHandler;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

@Pseudo
@Mixin(AccessoriesEventHandler.class)
public class AccessoriesEventHandlerMixin {

    // Add a check for Individual Keep Inventory before dropping accessories
    @Inject(method = "onDeath", at = @At(value = "INVOKE", target = "Lio/wispforest/accessories/api/AccessoriesCapability;getHolder()Lio/wispforest/accessories/api/AccessoriesHolder;", shift = At.Shift.BEFORE))
    private static void individualkeepinventory$preventAccessoriesDropOrDestroy(LivingEntity entity, DamageSource source, CallbackInfoReturnable<Collection<ItemStack>> cir, @Local(name = "keepInv", ordinal = 0) LocalBooleanRef keepInv) {
        if (entity instanceof Player player) {
            if (!keepInv.get()) {
                keepInv.set(InventoryDropsManager.shouldKeepExtraInventories(player));
            }
        }
    }
}
