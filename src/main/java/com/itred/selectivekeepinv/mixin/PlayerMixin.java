package com.itred.selectivekeepinv.mixin;

import com.itred.selectivekeepinv.attachement.IKIDataAttachments;
import com.itred.selectivekeepinv.util.InventoryDropsManager;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public class PlayerMixin {


    // Prevent the player from dropping their equipment if they have the keep inventory attachment.
    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;dropAll()V"), method = "dropEquipment")
    public void checkForKeepInventoryTag(Inventory instance, Operation<Void> original) {

        Player playerInstance = (Player) (Object) this;

        if (playerInstance.getData(IKIDataAttachments.KEEPINV_ATTACHMENT) == true) {
            InventoryDropsManager.inventoryDropHandler(instance, playerInstance);
        } else {
            original.call(instance);
        }

    }
}
