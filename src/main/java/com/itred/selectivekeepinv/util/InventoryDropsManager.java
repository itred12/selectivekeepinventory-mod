package com.itred.selectivekeepinv.util;

import com.itred.selectivekeepinv.attachement.IKIDataAttachments;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

public class InventoryDropsManager {

    public static void inventoryDropHandler(Inventory inventory, Player player) {
        return; // TODO: Some in-between from keeping and losing everything
    }

    public static void playerDeathHandler(Player oldBody, Player newBody) {
        newBody.experienceLevel = oldBody.experienceLevel;
        newBody.experienceProgress = oldBody.experienceProgress;
        newBody.setScore(oldBody.getScore());

        newBody.getInventory().replaceWith(oldBody.getInventory());
    }

    public static boolean shouldKeepExtraInventories(Player player) {
        return player.getData(IKIDataAttachments.KEEPINV_ATTACHMENT); // TODO: Adjust to account for aforementioned in-between
    }

}
